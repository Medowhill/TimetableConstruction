package main;

import pool.Course;
import pool.DivideClass;
import pool.Student;

import java.util.ArrayList;

class StudentManager {

    private static final int LIMIT = 3;

    private boolean log;

    private ArrayList<Student> students;

    private ArrayList<Course> courses;

    StudentManager(ArrayList<Student> students, ArrayList<Course> courses, boolean log) {
        this.students = students;
        this.courses = courses;
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

    // 학생을 분반에 배정
    ArrayList<DivideClass> assignStudents() {

        ArrayList<DivideClass> classes = new ArrayList<>();

        for (int i = 0; i < students.size(); i++) {

            float minDev = Float.MAX_VALUE;
            Student s = null;
            int index = -1;

            for (int j = 0; j < students.size() - i; j++) {
                boolean b = true;
                Student student = students.get(j);
                for (Course course : student.getCourses()) {
                    if (course.getAssigningClass().isFull()) {
                        b = false;
                        break;
                    }
                }
                if (b) {
                    float[] ratio = new float[courses.size()];
                    for (int k = 0; k < courses.size(); k++) {
                        DivideClass dc = courses.get(k).getAssigningClass();
                        if (dc == null)
                            ratio[k] = 0;
                        else {
                            int max = dc.getMaxStudentNumber();
                            int num = dc.getStudentNumber();
                            if (student.getCourses().contains(courses.get(k)))
                                num++;
                            ratio[k] = 1.f * num / max;
                        }
                    }
                    float stdev = stdev(ratio);
                    if (stdev < minDev) {
                        minDev = stdev;
                        s = student;
                        index = j;
                    }
                }
            }

            if (s == null) {
                System.out.println(i);
                for (Course course : courses)
                    course.nextClass();
                i--;
            } else {
                for (Course course : s.getCourses()) {

                    DivideClass divideClass = course.getAssigningClass();
                    divideClass.addStudent(s);
                    s.addClass(divideClass);

                    if (divideClass.getStudentNumber() == 1)
                        classes.add(divideClass);
                }
                students.set(index, students.get(students.size() - i - 1));
                students.set(students.size() - i - 1, s);
            }
        }

        return classes;
    }

    private float stdev(float[] arr) {
        float ave = 0;
        for (int i = 0; i < arr.length; i++)
            ave += arr[i];
        ave /= arr.length;
        float sum = 0;
        for (int i = 0; i < arr.length; i++)
            sum += (ave - arr[i]) * (ave - arr[i]);
        return (float) Math.sqrt(sum);
    }

}
