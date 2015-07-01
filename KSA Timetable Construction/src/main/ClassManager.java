package main;

import pool.DivideClass;
import pool.Graph;
import pool.Group;
import pool.Student;

import java.util.ArrayList;

class ClassManager {

    private boolean log;

    ClassManager(boolean log) {
        this.log = log;
    }

    int assignClasses(ArrayList<DivideClass> classes, ArrayList periods) {

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
        if (log)
            for (int color : colors)
                System.out.println(color);
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