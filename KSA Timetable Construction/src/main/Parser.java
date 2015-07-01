/**
 * (C) 2015. 홍재민 all rights reserved.
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
    // 파일을 읽어 정보를 parsing하는 parser

    private final boolean LOG;
    private final PrintWriter pw;

    private ArrayList<Course> courses;

    Parser(boolean log, PrintWriter pw) {
        this.LOG = log;
        this.pw = pw;
    }

    // 과목 정보를 parsing
    ArrayList<Course> parseCourse(String fileName) throws FileNotFoundException {
        courses = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileName));

        // 과목명 parsing
        String line = scanner.nextLine();
        String[] data = line.split(",");
        for (String name : data)
            courses.add(new Course(name));

        // 과목 분반 수 parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setClassNumber(Integer.parseInt(data[i]));

        // 과목 학생 수 parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setStudentNumber(Integer.parseInt(data[i]));

        // 과목 시수 구성 parsing
        line = scanner.nextLine();
        data = line.split(",");
        for (int i = 0; i < data.length; i++)
            courses.get(i).setTimeComposition(Integer.parseInt(data[i]));

        scanner.close();

        return courses;
    }

    // 학생 정보를 parsing
    ArrayList<Student> parseStudent(String fileName)
            throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();

        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            // 학생 이름, 학년 parsing
            Student student = new Student(data[0], Integer.parseInt(data[1]));

            // 학생 과목 parsing
            for (int i = 2; i < data.length; i++)
                if (data[i].equals("1"))
                    student.addCourse(courses.get(i - 2));

            students.add(student);
        }

        scanner.close();

        return students;
    }

    // 교시 정보를 parsing
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