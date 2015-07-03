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
    private ArrayList<Student> mStudents;

    // �г� �� �л� ����Ʈ
    private ArrayList<Student>[] mStudentsGrade;

    // ��ü �й� ����Ʈ
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

    // �л��� �йݿ� ����
    ArrayList<DivideClass> assignStudents() {

        ArrayList<DivideClass> classes = new ArrayList<>();

        if (LOG)
            pw.println("==========STUDENT SORTING==========");

        for (int n = 0; n < mStudentsGrade.length; n++) {
            ArrayList<Student> students = this.mStudentsGrade[n];

            // Random�� ������ �迭
            Collections.shuffle(students);

            // ���� �����ǰ� �ִ� �л����� �������� ��û�� ������ set
            HashSet<Course> cSet = new HashSet<>();

            // ���� �����ǰ� �ִ� �л����� ä��� �ִ� �ʼ� ���� set
            HashSet<Course> necessarySet = new HashSet<>();

            boolean first = true;

            for (int i = 0; i < students.size(); i++) {
                // i��° �л��� ������

                Student student = null;

                if (first) {
                    // ������ ���ĵǴ� �л����� ���� ��
                    int max = 0;
                    int index = i;

                    for (int j = i; j < students.size(); j++) {
                        // i��° �л����� ������ �л��� ���� Ȯ��

                        Student student_ = students.get(j);

                        int size = 0;
                        // ������ �ִ� �ش� �г� �ʼ� ������ �� ����
                        for (Course course : student_.getCourses())
                            if (course.getNecessary() == n + 1)
                                size++;

                        // �ʼ� ������ �ִ��� �л� ����
                        if (size > max) {
                            max = size;
                            index = j;
                        }
                    }

                    // �л� index ����
                    student = students.get(index);
                    if (index != i) {
                        students.set(index, students.get(i));
                        students.set(i, student);
                    }

                    // ���� set ����
                    cSet.addAll(student.getCourses());
                    for (Course course : student.getCourses())
                        if (course.getNecessary() == n + 1)
                            necessarySet.add(course);
                    first = false;
                } else {
                    // ������ ���ĵǴ� �л����� ���� ��
                    int max = 0;
                    int index = i;

                    for (int j = i; j < students.size(); j++) {
                        // i��° �л����� ������ �л��� ���� Ȯ��

                        Student student_ = students.get(j);

                        int size = 0;
                        // ������ ���ĵǴ� �л���� ��ġ�� ������ �� ����
                        for (Course course : student_.getCourses())
                            if (cSet.contains(course))
                                size++;

                        // ��ġ�� ������ �ִ��� �л� ����
                        if (size > max) {
                            max = size;
                            index = j;
                        }
                    }

                    // ��ġ�� ������ ���� LIMIT���� ������ �л� ������ ���Ӱ� ����
                    if (max < LIMIT) {
                        first = true;
                        cSet.clear();
                        i--;
                        continue;
                    }

                    // �л� index ����
                    student = students.get(index);
                    if (index != i) {
                        students.set(index, students.get(i));
                        students.set(i, student);
                    }

                    // ���� set ����
                    ArrayList<Course> cList = new ArrayList<>(cSet);
                    for (Course course : cList)
                        if (!student.getCourses().contains(course))
                            cSet.remove(course);
                }

                // �йݿ� �л� ����
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