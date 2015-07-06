package main;

import pool.*;

import java.io.PrintWriter;
import java.util.ArrayList;

class ClassManager {

    private final boolean LOG;
    private final PrintWriter pw;

    private ArrayList<DivideClass> mClasses;
    private ArrayList<Course> mCourses;

    ClassManager(ArrayList<DivideClass> classes, ArrayList<Course> courses, boolean log, PrintWriter pw) {
        this.mClasses = classes;
        this.mCourses = courses;
        this.LOG = log;
        this.pw = pw;
    }

    int[] assignClasses() {
        Group.prepare();

        Graph graph = new Graph(mClasses.size());

        // Edge 추가
        for (int i = 0; i < mClasses.size(); i++) {
            DivideClass class1 = mClasses.get(i);
            for (int j = i + 1; j < mClasses.size(); j++) {
                DivideClass class2 = mClasses.get(j);
                if (makeEdge(class1, class2))
                    graph.addEdge(i, j);
            }
        }

        // 같은 과목의 분반 사이에 edge 추가
        for (Course course : mCourses) {
            if (course.getClassNumber() == 1)
                continue;

            ArrayList<DivideClass>[] classArray = new ArrayList[course.getMaxClassSamePeriod()];
            for (int i = 0; i < classArray.length; i++)
                classArray[i] = new ArrayList<>();

            DivideClass[] classes = course.getClasses();
            for (int i = 0; i < classes.length; i++)
                classArray[i % classArray.length].add(classes[i]);

            for (ArrayList<DivideClass> distinctClasses : classArray)
                for (int i = 0; i < distinctClasses.size(); i++)
                    for (int j = i + 1; j < distinctClasses.size(); j++)
                        graph.addEdge(mClasses.indexOf(distinctClasses.get(i)), mClasses.indexOf(distinctClasses.get(j)));
        }

        int[] colors = graph.coloring();

        return colors;
    }

    private boolean makeEdge(DivideClass dc1, DivideClass dc2) {
        if (dc1.getCourse() == dc2.getCourse())
            return false;
        if (!Group.intersectPeriod(dc1.getTimeComposition(), dc2.getTimeComposition()))
            return false;
        for (Student student : dc2.getStudents())
            if (dc1.getStudents().contains(student))
                return true;
        return false;
    }

}