package main;

import pool.DivideClass;
import pool.Student;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

class StudentManager {

    private static final int N = 5, LIMIT = 5;

    private final boolean LOG;
    private final PrintWriter pw;

    // ��ü �л� ����Ʈ
    private ArrayList<Student> mStudents;

    // ��ü �й� ����Ʈ
    private ArrayList<DivideClass> mClasses;

    private int MAX;
    private ArrayList<Student> maxStudents;

    StudentManager(ArrayList<Student> students, boolean log, PrintWriter pw) {
        this.LOG = log;
        this.pw = pw;

        mStudents = new ArrayList<>();
        mStudents.addAll(students);

        mClasses = new ArrayList<>();
    }

    // �л��� �йݿ� ����
    ArrayList<DivideClass> assignStudents() {

        if (LOG)
            pw.println("==========STUDENT ASSIGNING==========");

        Collections.shuffle(mStudents);
        while (!mStudents.isEmpty()) {

        }

        return mClasses;
    }

}