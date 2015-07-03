package main;

import pool.Course;
import pool.DivideClass;
import pool.Student;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

class StudentManager {

    private static final int LIMIT = 5;

    private final boolean LOG;
    private final PrintWriter pw;

    // 전체 학생 리스트
    private ArrayList<Student> mStudents;

    // 학년 별 학생 리스트
    private ArrayList<Student>[] mStudentsGrade;

    // 전체 분반 리스트
    private ArrayList<DivideClass> mClasses;

    private int MAX;
    private ArrayList<Student> maxStudents;

    StudentManager(ArrayList<Student> students, boolean log, PrintWriter pw) {
        this.LOG = log;
        this.pw = pw;

        mStudents = students;
        mStudentsGrade = new ArrayList[3];
        for (int i = 0; i < mStudentsGrade.length; i++)
            mStudentsGrade[i] = new ArrayList<>();
        for (Student student : students)
            mStudentsGrade[student.grade - 1].add(student);

        mClasses = new ArrayList<>();
    }

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        ArrayList<DivideClass> classes = new ArrayList<>();

        if (LOG)
            pw.println("==========STUDENT SORTING==========");

        for (int n = 0; n < mStudentsGrade.length; n++) {
            ArrayList<Student> students = this.mStudentsGrade[n];

            // Random한 순서로 배열
            Collections.shuffle(students);

            // 현재 배정되고 있는 학생들이 공통으로 신청한 과목의 set
            HashSet<Course> cSet = new HashSet<>();

            // 현재 배정되고 있는 학생들이 채우고 있는 필수 과목 set
            HashSet<Course> necessarySet = new HashSet<>();

            boolean first = true;

            for (int i = 0; i < students.size(); i++) {
                // i번째 학생을 결정함

                Student student = null;

                if (first) {
                    // 기존에 정렬되던 학생들이 없을 때
                    int max = 0;
                    int index = i;

                    for (int j = i; j < students.size(); j++) {
                        // i번째 학생부터 마지막 학생에 대해 확인

                        Student student_ = students.get(j);

                        int size = 0;
                        // 가지고 있는 해당 학년 필수 과목의 수 조사
                        for (Course course : student_.getCourses())
                            if (course.getNecessary() == n + 1)
                                size++;

                        // 필수 과목이 최대인 학생 저장
                        if (size > max) {
                            max = size;
                            index = j;
                        }
                    }

                    // 학생 index 수정
                    student = students.get(index);
                    if (index != i) {
                        students.set(index, students.get(i));
                        students.set(i, student);
                    }

                    // 과목 set 갱신
                    cSet.addAll(student.getCourses());
                    for (Course course : student.getCourses())
                        if (course.getNecessary() == n + 1)
                            necessarySet.add(course);
                    first = false;
                } else {
                    // 기존에 정렬되던 학생들이 있을 때
                    int max = 0;
                    int index = i;

                    for (int j = i; j < students.size(); j++) {
                        // i번째 학생부터 마지막 학생에 대해 확인

                        Student student_ = students.get(j);

                        int size = 0;
                        // 기존에 정렬되던 학생들과 겹치는 과목의 수 조사
                        for (Course course : student_.getCourses())
                            if (cSet.contains(course))
                                size++;

                        // 겹치는 과목이 최대인 학생 저장
                        if (size > max) {
                            max = size;
                            index = j;
                        }
                    }

                    // 겹치는 과목의 수가 LIMIT보다 작으면 학생 정렬을 새롭게 시작
                    if (max < LIMIT) {
                        first = true;
                        cSet.clear();
                        i--;
                        continue;
                    }

                    // 학생 index 수정
                    student = students.get(index);
                    if (index != i) {
                        students.set(index, students.get(i));
                        students.set(i, student);
                    }

                    // 과목 set 갱신
                    ArrayList<Course> cList = new ArrayList<>(cSet);
                    for (Course course : cList)
                        if (!student.getCourses().contains(course))
                            cSet.remove(course);
                }

                // 분반에 학생 배정
                for (Course course : student.getCourses()) {
                    DivideClass divideClass = course.getAssigningClass();
                    divideClass.addStudent(student);
                    student.addClass(divideClass);

                    if (divideClass.getStudentNumber() == 1)
                        classes.add(divideClass);
                }
            }
        }

        return classes;
    }

    private ArrayList<Student> findMaxSameCourse(int N) {
        MAX = 0;
        maxStudents = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            findMaxSameCourse(new ArrayList<Student>(N), N, i);
        return maxStudents;
    }

    private void findMaxSameCourse(ArrayList<Student> students, int remainStudents, int grade) {
        for (Student student : mStudentsGrade[grade]) {
            if (!students.contains(student)) {
                students.add(student);
                int same = sameCourse(students).size();
                if (same > MAX) {
                    if (remainStudents > 1) {
                        findMaxSameCourse(students, remainStudents - 1, grade);
                        students.remove(student);
                    } else {
                        MAX = same;
                        maxStudents.clear();
                        maxStudents.addAll(students);
                    }
                }
            }
        }
    }

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

    private ArrayList<Course> unionCourse(ArrayList<Student> students) {
        ArrayList<Course> courses = new ArrayList<>();

        for (Student student : students)
            for (Course course : student.getCourses())
                if (!courses.contains(course))
                    courses.add(course);

        return courses;
    }

}