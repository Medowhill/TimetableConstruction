package main;

import pool.Course;
import pool.DivideClass;
import pool.Group;
import pool.Student;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

class StudentManager {

    private static final int NEW_EDGE = 1, MULTIPLE_EDGE = 2;

    private final boolean LOG;
    private final PrintWriter pw;

    // 전체 학생 리스트
    private ArrayList<Student> mStudents;

    // 전체 분반 리스트
    private ArrayList<DivideClass> mClasses;

    private int minSum;
    private ArrayList<DivideClass> minSumClasses;

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
            if (LOG)
                System.out.println(mStudents.size());

            int min = Integer.MAX_VALUE;
            ArrayList<DivideClass> classes = new ArrayList<>();
            Student student = null;

            for (Student student_ : mStudents) {
                minimumEdge(student_);
                if (minSum < min) {
                    min = minSum;
                    classes.clear();
                    classes.addAll(minSumClasses);
                    student = student_;
                }
            }

            mStudents.remove(student);
            for (DivideClass divideClass : classes) {
                if (divideClass.getStudentNumber() == 0)
                    mClasses.add(divideClass);

                student.addClass(divideClass);
                divideClass.addStudent(student);
            }
        }

        return mClasses;
    }

    private void minimumEdge(Student student) {
        minSum = Integer.MAX_VALUE;
        minSumClasses = new ArrayList<>();

        ArrayList<Course> courses = new ArrayList<>();
        courses.addAll(student.getCourses());
        minimumEdge(courses, new ArrayList<DivideClass>(), 0);
    }

    private void minimumEdge(ArrayList<Course> courses, ArrayList<DivideClass> classes, int sum) {
        if (courses.isEmpty()) {
            if (sum < minSum) {
                minSum = sum;
                minSumClasses.clear();
                minSumClasses.addAll(classes);
            }
        } else {
            Course course = courses.remove(courses.size() - 1);

            DivideClass[] newClasses = course.getClasses();
            for (DivideClass dc : newClasses) {
                if (dc.getStudentNumber() >= dc.getMaxStudentNumber())
                    continue;

                int sum_ = 0;
                for (DivideClass dcAlready : classes) {
                    if (Group.intersectPeriod(dc.getTimeComposition(), dcAlready.getTimeComposition())) {
                        if (!dcAlready.hasEdgeAlready(dc)) {
                            int edge = dcAlready.multipleEdge(course);
                            if (edge == 0)
                                sum_ += NEW_EDGE;
                            else
                                sum_ += MULTIPLE_EDGE * edge;
                        }
                    }
                }

                if (sum + sum_ >= minSum)
                    continue;

                classes.add(dc);
                minimumEdge(courses, classes, sum + sum_);
                classes.remove(dc);

                if (dc.isEmpty())
                    break;
            }

            courses.add(course);
        }
    }

}