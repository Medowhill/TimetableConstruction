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

    // ��ü �л� ����Ʈ
    private ArrayList<Student> students;

    // �г� �� �л� ����Ʈ
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

            // Random�� ������ �迭
            Collections.shuffle(students);

            // ���� �����ǰ� �ִ� �л����� �������� ��û�� ������ set
            HashSet<Course> cSet = new HashSet<>();


            boolean b = true;
            int x = 0;

            for (int i = 0; i < students.size(); i++) {
                // i��° �л��� ������

                Student student = students.get(i);
                int max = 0;
                int index = i;

                for (int j = i; j < students.size(); j++) {
                    // i��° �л����� ������ �л��� ���� Ȯ��

                    Student student_ = students.get(j);

                    int size = 0;
                    if (b) // �л� ������ ���ο� ���� ����
                        size = student_.getCourses().size();
                    else // ������ ���ĵǴ� �л���� ��ġ�� ������ �� ����
                        for (Course course : student_.getCourses())
                            if (cSet.contains(course))
                                size++;

                    // ��ġ�� ������ �ִ��� �л� ����
                    if (size > max) {
                        student = student_;
                        max = size;
                        index = j;
                    }
                }

                // ��ġ�� ������ ���� LIMIT���� ������ �л� ������ ���Ӱ� ����
                if (max < LIMIT) {
                    b = true;
                    x = 0;
                    cSet.clear();
                    i--;
                    continue;
                }

                x++;

                // �л� index ����
                if (index != i) {
                    students.set(index, students.get(i));
                    students.set(i, student);
                }

                // ���� set ����
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

    // �л��� �йݿ� ����
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