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

    // 전체 학생 리스트
    private ArrayList<Student> mStudents;

    // 전체 분반 리스트
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

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        if (LOG)
            pw.println("==========STUDENT ASSIGNING==========");

        Collections.shuffle(mStudents);
        while (!mStudents.isEmpty()) {

        }

        return mClasses;
    }

}