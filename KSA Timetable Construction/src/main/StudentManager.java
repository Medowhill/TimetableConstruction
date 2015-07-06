package main;

import pool.Course;
import pool.DivideClass;
import pool.Group;
import pool.Student;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

class StudentManager {

    private static final int NEW_EDGE = 1, MULTIPLE_EDGE = 4, LIMIT = 4;

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

    private void sort() {

        if (LOG)
            pw.println("==========STUDENT SORTING==========");

        Collections.shuffle(mStudents);

        HashSet<Course> cSet = new HashSet<>();
        boolean b = true;

        int x = 0;

        for (int i = 0; i < mStudents.size(); i++) {

            Student student = mStudents.get(i);
            int max = 0;
            int index = i;

            int maxCourseNum = 0;

            for (int j = i; j < mStudents.size(); j++) {
                Student student_ = mStudents.get(j);

                if (student_.getCourses().size() > maxCourseNum)
                    maxCourseNum = student_.getCourses().size();

                int size = 0;
                if (b)
                    size = student_.getCourses().size();
                else
                    for (Course course : student_.getCourses())
                        if (cSet.contains(course))
                            size++;

                if (size > max) {
                    student = student_;
                    max = size;
                    index = j;
                }
            }

            if (max < Math.min(LIMIT, maxCourseNum)) {
                b = true;
                x = 0;
                cSet.clear();
                i--;
                continue;
            }

            x++;

            if (index != i) {
                mStudents.set(index, mStudents.get(i));
                mStudents.set(i, student);
            }

            if (b) {
                cSet.addAll(student.getCourses());
                b = false;
            } else {
                ArrayList<Course> cList = new ArrayList<>(cSet);
                for (Course course : cList)
                    if (!student.getCourses().contains(course))
                        cSet.remove(course);
            }

            if (LOG)
                pw.println(i + "(" + x + "," + cSet.size() + "): " + student + ": " + student.getCourses());
        }
    }

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        sort();

        for (Student student : mStudents) {

            minimumEdge(student);

            for (DivideClass divideClass : minSumClasses) {
                if (divideClass.getStudentNumber() == 0)
                    mClasses.add(divideClass);

                student.addClass(divideClass);
                divideClass.addStudent(student);
            }
        }

        return mClasses;
    }

    ArrayList<DivideClass> assignStudents_slow() {
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
                            int edge = dcAlready.multipleEdge(course) + dc.multipleEdge(dcAlready.getCourse());
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