package main;

import pool.*;

import java.io.PrintWriter;
import java.util.ArrayList;

class ClassManager {

    private final boolean LOG;
    private final PrintWriter pw;

    private ArrayList<DivideClass> classes;
    private ArrayList<Period> periods;

    ClassManager(ArrayList<DivideClass> classes, ArrayList<Period> periods, boolean log, PrintWriter pw) {
        this.classes = classes;
        this.periods = periods;
        this.LOG = log;
        this.pw = pw;
    }

    int assignClasses() {
        Group.prepare();

        Graph graph = new Graph(classes.size());

        for (int i = 0; i < classes.size(); i++) {
            DivideClass class1 = classes.get(i);
            for (int j = i + 1; j < classes.size(); j++) {
                DivideClass class2 = classes.get(j);
                if (makeEdge(class1, class2))
                    graph.addEdge(i, j);
            }
        }

        int[] colors = graph.coloring();

        int max = 0;
        for (int color : colors)
            if (color > max)
                max = color;
        return max;
    }

    private boolean makeEdge(DivideClass dc1, DivideClass dc2) {
        if (dc1.getCourse() == dc2.getCourse())
            return true;
        if (!Group.intersectPeriod(dc1.getTimeComposition(), dc2.getTimeComposition()))
            return false;
        for (Student student : dc2.getStudents())
            if (dc1.getStudents().contains(student))
                return true;
        return false;
    }

}