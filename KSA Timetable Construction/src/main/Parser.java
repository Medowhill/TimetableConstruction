/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package main;

import pool.Course;
import pool.Period;
import pool.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Parser {
    // ������ �о� ������ parsing�ϴ� parser

    private final boolean LOG;
    private final PrintWriter pw;

    private ArrayList<Course> courses;

    Parser(boolean log, PrintWriter pw) {
        this.LOG = log;
        this.pw = pw;
    }

    // ���� ������ parsing
    ArrayList<Course> parseCourse(String fileName) throws FileNotFoundException {
        courses = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileName));

        // ����� parsing
        String line = scanner.nextLine();
        String[] data = line.split(",");
        for (String name : data)
            courses.add(new Course(name));

        // ���� �й� �� parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setClassNumber(Integer.parseInt(data[i]));

        // ���� �л� �� parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setStudentNumber(Integer.parseInt(data[i]));

        // ���� �ü� ���� parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setTimeComposition(Integer.parseInt(data[i]));

        scanner.close();

        return courses;
    }

    // �л� ������ parsing
    ArrayList<Student> parseStudent(String fileName)
            throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            // �л� �̸�, �г� parsing
            Student student = new Student(data[0], Integer.parseInt(data[1]));

            // �л� ���� parsing
            for (int i = 2; i < data.length; i++)
                if (data[i].equals("1"))
                    student.addCourse(courses.get(i - 2));

            students.add(student);
        }

        scanner.close();

        return students;
    }

    // ���� ������ parsing
    ArrayList<Period> parsePeriod(String fileName) throws FileNotFoundException {
        ArrayList<Period> periods = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");
            periods.add(new Period(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        }

        return periods;
    }
}