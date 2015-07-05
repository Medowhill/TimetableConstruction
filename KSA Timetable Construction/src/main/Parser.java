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

    private static final String courseFileName = "course.csv", studentFileName = "student.csv", periodFileName = "period.csv";

    private final boolean LOG;
    private final PrintWriter pw;

    private final String inputDir;
    private ArrayList<Course> courses;

    Parser(String inputDir, boolean log, PrintWriter pw) {
        this.inputDir = inputDir;
        this.LOG = log;
        this.pw = pw;
    }

    // ���� ������ parsing
    ArrayList<Course> parseCourse() throws FileNotFoundException {
        courses = new ArrayList<>();

        Scanner scanner = new Scanner(new File(inputDir + courseFileName));

        // ����� parsing
        String line = scanner.nextLine();
        String[] data1 = line.split(",");

        // ���� �й� �� parsing
        line = scanner.nextLine();
        String[] data2 = line.split(",");

        // ���� �л� �� parsing
        line = scanner.nextLine();
        String[] data3 = line.split(",");

        // ���� �ü� ���� parsing
        line = scanner.nextLine();
        String[] data4 = line.split(",");

        scanner.close();

        for (int i = 0; i < data1.length; i++)
            courses.add(new Course(data1[i], Integer.parseInt(data2[i]), Integer.parseInt(data3[i]), Integer.parseInt(data4[i])));

        return courses;
    }

    // �л� ������ parsing
    ArrayList<Student> parseStudent()
            throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();

        Scanner scanner = new Scanner(new File(inputDir + studentFileName));

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
    ArrayList<Period> parsePeriod() throws FileNotFoundException {
        ArrayList<Period> periods = new ArrayList<>();

        Scanner scanner = new Scanner(new File(inputDir + periodFileName));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");
            periods.add(new Period(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        }

        return periods;
    }
}