package pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class Graph {

    private int size;

    private Vertex[] vertices;

    private boolean[][] edgeSet;

    private int minColor;
    private int[] color;

    public Graph(int size) {
        this.size = size;
        this.minColor = 13;
        this.color = new int[size];

        vertices = new Vertex[size];
        for (int i = 0; i < size; i++)
            vertices[i] = new Vertex(i);

        edgeSet = new boolean[size][size];
    }

    public boolean addEdge(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= vertices.length || v2 >= vertices.length)
            return false;
        if (edgeSet[v1][v2] || edgeSet[v2][v1])
            return false;

        edgeSet[v1][v2] = true;
        edgeSet[v2][v1] = true;
        vertices[v1].addEdge(vertices[v2]);
        vertices[v2].addEdge(vertices[v1]);
        return true;
    }

    public int[] coloring() {
        ArrayList<Vertex> vertexList = new ArrayList<>(size);
        for (Vertex vertex : vertices)
            vertexList.add(vertex);
        Collections.sort(vertexList);

        vertexList.get(0).color = 1;
        vertexList.remove(0);

        for (int i = 1; i < size; i++) {
            Vertex current = vertexList.get(0);
            for (Vertex vertex : vertexList)
                if (current.getDegreeOfSaturation() < vertex.getDegreeOfSaturation())
                    current = vertex;
            vertexList.remove(current);

            for (int j = 1; j <= i + 1; j++) {
                boolean b = true;
                for (Vertex vertex : current.edges) {
                    if (vertex.color == j) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    current.color = j;
                    break;
                }
            }
        }

        int[] color = new int[size];
        for (int i = 0; i < vertices.length; i++)
            color[i] = vertices[i].color;
        return color;
    }

    public int[] coloring_exact_slow() {
        ArrayList<Vertex> vertexList = new ArrayList<>(size);
        for (Vertex vertex : vertices)
            vertexList.add(vertex);
        Collections.sort(vertexList);

        vertexList.get(0).color = 1;
        vertexList.remove(0);

        coloring_exact_slow(vertexList, 1);

        return color;
    }

    private void coloring_exact_slow(ArrayList<Vertex> vertexList, int maxColor) {

        if (maxColor > minColor)
            return;

        if (vertexList.isEmpty()) {
            if (maxColor < minColor) {
                minColor = maxColor;
                System.out.println(minColor);
                for (int i = 0; i < vertices.length; i++)
                    color[i] = vertices[i].color;
            }
            return;
        }

        Vertex current = vertexList.get(0);
        for (Vertex vertex : vertexList)
            if (current.getDegreeOfSaturation() < vertex.getDegreeOfSaturation())
                current = vertex;
        vertexList.remove(current);

        for (int i = 1; i <= maxColor + 1; i++) {
            boolean b = true;
            for (Vertex vertex : current.edges) {
                if (vertex.color == i) {
                    b = false;
                    break;
                }
            }
            if (b) {
                current.color = i;
                coloring_exact_slow(vertexList, Math.max(i, maxColor));
            }
        }

        vertexList.add(current);
    }

    public int[] coloring_exact() {

        int[] optimal = null;

        ArrayList<Vertex> vertexList = new ArrayList<>(size);
        for (Vertex vertex : vertices)
            vertexList.add(vertex);
        Collections.sort(vertexList);

        int start = 0; // starting index
        int optColorNum = size + 1; // optimal number of colors
        Vertex current = vertexList.get(0); // current vertex to be colored
        int[] colors = new int[size + 1]; // colors[j] = number of colors at A[0] ... A[j-1]
        colors[0] = 0;
        ArrayList<Integer> freeColors = new ArrayList<>(); // set of free colors
        freeColors.add(1);
        current.setFreeColors(freeColors);

        while (start >= 0) {
            boolean backtracking = false;

            for (int i = start; i < size; i++) {
                if (i > start) {
                    current = null;
                    for (Vertex vertex : vertexList)
                        if (vertex.color == 0)
                            if (current == null || current.getDegreeOfSaturation() < vertex.getDegreeOfSaturation())
                                current = vertex;
                    freeColors.clear();
                    freeColors.addAll(current.freeColors);
                    Collections.sort(freeColors);
                }
                if (freeColors.size() > 0) {
                    int free = freeColors.get(0);
                    current.color = free;
                    freeColors.remove(0);
                    current.setFreeColors(freeColors);
                    int l = colors[i - 1 + 1];
                    colors[i + 1] = Math.max(free, l);
                } else {
                    start = i - 1;
                    backtracking = true;
                    break;
                }
            }

            if (backtracking) {
                if (start >= 0) {
                    current = vertexList.get(start);
                    current.color = 0;
                    freeColors.clear();
                    freeColors.addAll(current.freeColors);
                }
            } else {
                optimal = new int[size];
                for (Vertex vertex : vertexList)
                    optimal[vertex.number] = vertex.color;
                optColorNum = colors[size - 1 + 1];
                for (int i = 0; i < vertexList.size(); i++) {
                    if (vertexList.get(i).color == optColorNum) {
                        start = i - 1;
                        break;
                    }
                }
                if (start < 0)
                    break;
                for (int i = start; i < vertexList.size(); i++)
                    vertexList.get(i).color = 0;
                for (int i = 0; i <= start; i++) {
                    current = vertexList.get(i);
                    freeColors.clear();
                    freeColors.addAll(current.freeColors);
                    for (int j = 0; j < freeColors.size(); j++) {
                        if (freeColors.get(j) >= optColorNum) {
                            freeColors.remove(j);
                            j--;
                        }
                    }
                }
            }
        }

        return optimal;
    }

    private class Vertex implements Comparable {

        final int number;

        final LinkedHashSet<Vertex> edges;
        final ArrayList<Integer> freeColors;

        int color = 0;

        Vertex(int number) {
            this.number = number;
            this.edges = new LinkedHashSet<>();
            this.freeColors = new ArrayList<>();
        }

        void addEdge(Vertex vertex) {
            this.edges.add(vertex);
        }

        void setFreeColors(ArrayList<Integer> freeColors) {
            this.freeColors.clear();
            this.freeColors.addAll(freeColors);
        }

        int getDegree() {
            return this.edges.size();
        }

        int getDegreeOfSaturation() {
            int degree = 0;
            boolean[] colors = new boolean[size];
            for (Vertex vertex : edges) {
                if (vertex.color != 0) {
                    if (!colors[vertex.color - 1]) {
                        colors[vertex.color - 1] = true;
                        degree++;
                    }
                }
            }
            return degree;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Vertex) {
                Vertex vertex = (Vertex) o;
                if (vertex.getDegree() > this.getDegree())
                    return 1;
                else if (vertex.getDegree() == this.getDegree())
                    return 0;
                else
                    return -1;
            } else
                return 0;
        }
    }

}
