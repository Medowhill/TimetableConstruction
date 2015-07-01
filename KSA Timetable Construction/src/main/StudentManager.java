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
    private ArrayList<Student> students;

    // 학년 별 학생 리스트
    private ArrayList<Student>[] students_grade;

    StudentManager(ArrayList<Student> students, boolean log, PrintWriter pw) {
        this.LOG = log;
        this.pw = pw;

        this.students = students;
        students_grade = new ArrayList[3];
        for (int i = 0; i < students_grade.length; i++)
            students_grade[i] = new ArrayList<>();
        for (Student student : students)
            students_grade[student.grade - 1].add(student);
    }

    void sort() {

        if (LOG)
            pw.println("==========STUDENT SORTING==========");

        for (int n = 0; n < students_grade.length; n++) {
            ArrayList<Student> students = this.students_grade[n];

            // Random한 순서로 배열
            Collections.shuffle(students);

            // 현재 배정되고 있는 학생들이 공통으로 신청한 과목의 set
            HashSet<Course> cSet = new HashSet<>();


            boolean b = true;
            int x = 0;

            for (int i = 0; i < students.size(); i++) {
                // i번째 학생을 결정함

                Student student = students.get(i);
                int max = 0;
                int index = i;

                for (int j = i; j < students.size(); j++) {
                    // i번째 학생부터 마지막 학생에 대해 확인

                    Student student_ = students.get(j);

                    int size = 0;
                    if (b) // 학생 정렬의 새로운 시작 지점
                        size = student_.getCourses().size();
                    else // 기존에 정렬되던 학생들과 겹치는 과목의 수 조사
                        for (Course course : student_.getCourses())
                            if (cSet.contains(course))
                                size++;

                    // 겹치는 과목이 최대인 학생 저장
                    if (size > max) {
                        student = student_;
                        max = size;
                        index = j;
                    }
                }

                // 겹치는 과목의 수가 LIMIT보다 작으면 학생 정렬을 새롭게 시작
                if (max < LIMIT) {
                    b = true;
                    x = 0;
                    cSet.clear();
                    i--;
                    continue;
                }

                x++;

                // 학생 index 수정
                if (index != i) {
                    students.set(index, students.get(i));
                    students.set(i, student);
                }

                // 과목 set 갱신
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
    }

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        ArrayList<DivideClass> classes = new ArrayList<>();

        for (int n = 0; n < students_grade.length; n++) {
            ArrayList<Student> students = this.students_grade[n];

            for (Student student : students) {
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

}