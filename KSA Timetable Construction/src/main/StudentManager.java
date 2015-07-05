package main;

import pool.Course;
import pool.DivideClass;
import pool.Student;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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

        while (!mStudents.isEmpty()) {
            System.out.println(mStudents.size());

            Collections.shuffle(mStudents);

            // N명이 공통으로 가진 과목을 최대로 만드는 N명을 찾고 신청한 과목의 합집합을 구한다.
            ArrayList<Student> students = findMaxSameCourse(N);
            ArrayList<Course> courses = unionCourse(students);

            for (Course course : courses) {
                DivideClass dc = course.getAssigningClass();
                if (!mClasses.contains(dc))
                    mClasses.add(dc);
            }

            ArrayList<Student> addingStudents = new ArrayList<>();
            ArrayList<Course> finCourse = new ArrayList<>();

            // 신청한 과목들이 위에서 구한 과목에 포함되는 학생들을 찾는다.
            for (Student student : mStudents)
                if (include(courses, student.getCourses()))
                    addingStudents.add(student);

            int minBalance = courses.get(0).getBalance();
            for (int i = 1; i < courses.size(); i++) {
                int balance = courses.get(i).getBalance();
                if (minBalance > balance)
                    minBalance = balance;
            }

            if (minBalance >= addingStudents.size()) {
                // 모든 학생을 분반에 배정한다.
                for (Student student : addingStudents) {
                    for (Course course : student.getCourses()) {
                        DivideClass dc = course.getAssigningClass();
                        dc.addStudent(student);
                        student.addClass(dc);

                        mStudents.remove(student);
                    }
                }

                // balance가 0이 된 과목을 리스트에서 제거한다.
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    if (course.getBalance() == 0) {
                        courses.remove(i);
                        finCourse.add(course);
                        course.finishCurrentAssigning();
                        i--;
                    }
                }
            } else {
                while (!addingStudents.isEmpty()) {
                    // weight의 합이 최대인 학생을 찾는다.

                    Student s = null;
                    float maxWeight = -Float.MAX_VALUE;

                    float ave = 0;
                    for (Course course : courses)
                        ave += course.getBalance();
                    ave /= courses.size();

                    for (Student student : addingStudents) {
                        float weight = 0;
                        for (Course course : student.getCourses())
                            weight += course.getBalance() - ave;
                        if (maxWeight < weight) {
                            maxWeight = weight;
                            s = student;
                        }
                    }

                    // 찾은 학생을 분반에 배정한다.
                    for (Course course : s.getCourses()) {
                        DivideClass dc = course.getAssigningClass();
                        dc.addStudent(s);
                        s.addClass(dc);

                        mStudents.remove(s);
                        addingStudents.remove(s);
                    }

                    // balance가 0이 된 과목을 리스트에서 제거하고 해당 과목을 신청한 학생도 제거한다.
                    for (int i = 0; i < courses.size(); i++) {
                        Course course = courses.get(i);
                        if (course.getBalance() == 0) {
                            courses.remove(i);
                            finCourse.add(course);
                            course.finishCurrentAssigning();
                            i--;

                            for (int j = 0; j < addingStudents.size(); j++) {
                                if (addingStudents.get(j).getCourses().contains(course)) {
                                    addingStudents.remove(j);
                                    j--;
                                }
                            }
                        }
                    }
                }
            }

            // 배정이 종료되지 않은 과목에 대해서 학생들을 배정한다.
            boolean first = true;
            while (!courses.isEmpty()) {
                sort(mStudents);

                for (int i = 0; i < mStudents.size(); i++) {
                    Student student = mStudents.get(i);
                    boolean able1 = true, able2 = false;

                    // 배정이 종료된 과목을 가지지 않은 학생들을 찾는다.
                    if (first) {
                        for (Course course : student.getCourses()) {
                            if (finCourse.contains(course)) {
                                able1 = false;
                                break;
                            }
                        }
                    }

                    // 배정이 종료되지 않은 과목을 하나라도 가진 학생들을 찾는다.
                    if (able1) {
                        for (Course course : student.getCourses()) {
                            if (courses.contains(course)) {
                                able2 = true;
                                break;
                            }
                        }
                    }

                    // 찾은 학생을 배정한다.
                    if (able1 && able2) {
                        for (Course course : student.getCourses()) {
                            DivideClass dc = course.getAssigningClass();
                            dc.addStudent(student);
                            student.addClass(dc);
                        }
                        mStudents.remove(student);
                        i--;
                    }

                    // balance가 0이 된 과목을 리스트에서 제거한다.
                    for (int j = 0; j < courses.size(); j++) {
                        Course course = courses.get(j);
                        if (course.getBalance() == 0) {
                            courses.remove(j);
                            finCourse.add(course);
                            course.finishCurrentAssigning();
                            j--;
                        }
                    }
                }

                first = false;
            }
        }

        return mClasses;
    }

    // N명의 학생들이 모두 신청한 과목의 수를 최대로 만드는 N명을 찾아 return
    private ArrayList<Student> findMaxSameCourse(int N) {
        MAX = 0;
        maxStudents = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
            findMaxSameCourse(new ArrayList<Student>(N), N, i);
        if (maxStudents.size() == 0)
            return findMaxSameCourse(N - 1);
        return maxStudents;
    }

    // students의 학생들에 grade에 맞는 remainStudents만큼의 학생을 추가해서 모두 신청한 과목의 수를 조사
    private void findMaxSameCourse(ArrayList<Student> students, int remainStudents, int grade) {
        for (Student student : mStudents) {
            if (student.grade == grade && !students.contains(student)) {
                students.add(student);
                int same = sameCourse(students).size();
                if (same > MAX) {
                    if (remainStudents > 1) {
                        findMaxSameCourse(students, remainStudents - 1, grade);
                    } else {
                        MAX = same;
                        maxStudents.clear();
                        maxStudents.addAll(students);
                    }
                }
                students.remove(student);
            }
        }
    }

    // students의 학생들이 모두 신청한 과목들의 집합을 return
    private ArrayList<Course> sameCourse(ArrayList<Student> students) {
        ArrayList<Course> courses = new ArrayList<>();

        Student student = students.get(0);
        for (Course course : student.getCourses()) {
            boolean b = true;
            for (int i = 1; i < students.size(); i++) {
                Student s = students.get(i);
                if (!s.getCourses().contains(course)) {
                    b = false;
                    break;
                }
            }
            if (b)
                courses.add(course);
        }

        return courses;
    }

    // students의 학생들이 신청한 과목들의 합집합을 return
    private ArrayList<Course> unionCourse(ArrayList<Student> students) {
        ArrayList<Course> courses = new ArrayList<>();

        for (Student student : students)
            for (Course course : student.getCourses())
                if (!courses.contains(course))
                    courses.add(course);

        return courses;
    }

    // c2의 과목이 c1에 포함되는지 return
    private boolean include(ArrayList<Course> c1, ArrayList<Course> c2) {
        for (Course course : c2)
            if (!c1.contains(course))
                return false;
        return true;
    }

    // 학생들의 과목이 많이 겹치는 순서로 학생들을 정렬한다.
    private void sort(ArrayList<Student> students) {

        // 공통으로 학생들이 가지고 있는 과목
        HashSet<Course> cSet = new HashSet<>();

        // 새로운 기준으로 학생 정렬을 시작하는가
        boolean first = true;

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            int max = 0;
            int index = i;
            int maxCourse = 0;

            // index i에 올 학생을 찾는다.
            for (int j = i; j < students.size(); j++) {
                Student student_ = students.get(j);
                if (student_.getCourses().size() > maxCourse)
                    maxCourse = student_.getCourses().size();

                int size = 0;
                if (first)
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

            if (max < Math.min(LIMIT, maxCourse)) {
                first = true;
                cSet.clear();
                i--;
                continue;
            }

            if (index != i) {
                students.set(index, students.get(i));
                students.set(i, student);
            }

            if (first) {
                cSet.addAll(student.getCourses());
                first = false;
            } else {
                ArrayList<Course> cList = new ArrayList<>(cSet);
                for (Course course : cList)
                    if (!student.getCourses().contains(course))
                        cSet.remove(course);
            }
        }
    }

}