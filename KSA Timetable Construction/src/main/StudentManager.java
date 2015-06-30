package main;

import pool.Course;
import pool.DivideClass;
import pool.Student;

import java.util.ArrayList;
import java.util.HashSet;

class StudentManager {

    private static final int LIMIT = 3;

    private boolean log;

    private ArrayList<Student> students;

    StudentManager(ArrayList<Student> students, boolean log) {
        this.students = students;
        this.log = log;
    }

    // 학생을 무작위 순서로 배열
    void sortRandomly() {
        for (int i = 0; i < students.size(); i++) {
            int rand = (int) (Math.random() * (students.size() - i)) + i;
            Student tmp = students.get(rand);
            students.set(rand, students.get(i));
            students.set(i, tmp);
        }
    }

    void sort() {
        HashSet<Course> cSet = new HashSet<>();
        boolean b = true;

        int x = 0;

        for (int i = 0; i < students.size(); i++) {

            Student student = students.get(i);
            int max = 0;
            int index = i;

            for (int j = i; j < students.size(); j++) {
                Student student_ = students.get(j);

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

            if (max < LIMIT) {
                b = true;
                x = 0;
                cSet.clear();
                i--;
                continue;
            }

            x++;

            if (index != i) {
                students.set(index, students.get(i));
                students.set(i, student);
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

            if (log)
                System.out.println(i + "(" + x + "," + cSet.size() + "): "
                        + student + ": " + student.getCourses());
        }
    }

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        ArrayList<DivideClass> classes = new ArrayList<>();

        for (Student student : students) {
            for (Course course : student.getCourses()) {

                DivideClass divideClass = course.getAssigningClass();
                divideClass.addStudent(student);
                student.addClass(divideClass);

                if (divideClass.getStudentNumber() == 1)
                    classes.add(divideClass);
            }
        }

        return classes;
    }

}
