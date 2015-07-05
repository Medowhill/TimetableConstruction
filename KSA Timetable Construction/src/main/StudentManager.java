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

        while (!mStudents.isEmpty()) {
            System.out.println(mStudents.size());

            Collections.shuffle(mStudents);

            // N���� �������� ���� ������ �ִ�� ����� N���� ã�� ��û�� ������ �������� ���Ѵ�.
            ArrayList<Student> students = findMaxSameCourse(N);
            ArrayList<Course> courses = unionCourse(students);

            for (Course course : courses) {
                DivideClass dc = course.getAssigningClass();
                if (!mClasses.contains(dc))
                    mClasses.add(dc);
            }

            ArrayList<Student> addingStudents = new ArrayList<>();
            ArrayList<Course> finCourse = new ArrayList<>();

            // ��û�� ������� ������ ���� ���� ���ԵǴ� �л����� ã�´�.
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
                // ��� �л��� �йݿ� �����Ѵ�.
                for (Student student : addingStudents) {
                    for (Course course : student.getCourses()) {
                        DivideClass dc = course.getAssigningClass();
                        dc.addStudent(student);
                        student.addClass(dc);

                        mStudents.remove(student);
                    }
                }

                // balance�� 0�� �� ������ ����Ʈ���� �����Ѵ�.
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
                    // weight�� ���� �ִ��� �л��� ã�´�.

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

                    // ã�� �л��� �йݿ� �����Ѵ�.
                    for (Course course : s.getCourses()) {
                        DivideClass dc = course.getAssigningClass();
                        dc.addStudent(s);
                        s.addClass(dc);

                        mStudents.remove(s);
                        addingStudents.remove(s);
                    }

                    // balance�� 0�� �� ������ ����Ʈ���� �����ϰ� �ش� ������ ��û�� �л��� �����Ѵ�.
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

            // ������ ������� ���� ���� ���ؼ� �л����� �����Ѵ�.
            boolean first = true;
            while (!courses.isEmpty()) {
                sort(mStudents);

                for (int i = 0; i < mStudents.size(); i++) {
                    Student student = mStudents.get(i);
                    boolean able1 = true, able2 = false;

                    // ������ ����� ������ ������ ���� �л����� ã�´�.
                    if (first) {
                        for (Course course : student.getCourses()) {
                            if (finCourse.contains(course)) {
                                able1 = false;
                                break;
                            }
                        }
                    }

                    // ������ ������� ���� ������ �ϳ��� ���� �л����� ã�´�.
                    if (able1) {
                        for (Course course : student.getCourses()) {
                            if (courses.contains(course)) {
                                able2 = true;
                                break;
                            }
                        }
                    }

                    // ã�� �л��� �����Ѵ�.
                    if (able1 && able2) {
                        for (Course course : student.getCourses()) {
                            DivideClass dc = course.getAssigningClass();
                            dc.addStudent(student);
                            student.addClass(dc);
                        }
                        mStudents.remove(student);
                        i--;
                    }

                    // balance�� 0�� �� ������ ����Ʈ���� �����Ѵ�.
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

    // N���� �л����� ��� ��û�� ������ ���� �ִ�� ����� N���� ã�� return
    private ArrayList<Student> findMaxSameCourse(int N) {
        MAX = 0;
        maxStudents = new ArrayList<>();
        for (int i = 1; i <= 3; i++)
            findMaxSameCourse(new ArrayList<Student>(N), N, i);
        if (maxStudents.size() == 0)
            return findMaxSameCourse(N - 1);
        return maxStudents;
    }

    // students�� �л��鿡 grade�� �´� remainStudents��ŭ�� �л��� �߰��ؼ� ��� ��û�� ������ ���� ����
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

    // students�� �л����� ��� ��û�� ������� ������ return
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

    // students�� �л����� ��û�� ������� �������� return
    private ArrayList<Course> unionCourse(ArrayList<Student> students) {
        ArrayList<Course> courses = new ArrayList<>();

        for (Student student : students)
            for (Course course : student.getCourses())
                if (!courses.contains(course))
                    courses.add(course);

        return courses;
    }

    // c2�� ������ c1�� ���ԵǴ��� return
    private boolean include(ArrayList<Course> c1, ArrayList<Course> c2) {
        for (Course course : c2)
            if (!c1.contains(course))
                return false;
        return true;
    }

    // �л����� ������ ���� ��ġ�� ������ �л����� �����Ѵ�.
    private void sort(ArrayList<Student> students) {

        // �������� �л����� ������ �ִ� ����
        HashSet<Course> cSet = new HashSet<>();

        // ���ο� �������� �л� ������ �����ϴ°�
        boolean first = true;

        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            int max = 0;
            int index = i;
            int maxCourse = 0;

            // index i�� �� �л��� ã�´�.
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