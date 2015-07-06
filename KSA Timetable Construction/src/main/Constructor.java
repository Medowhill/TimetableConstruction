/**
 * (C) 2015. ????©ö? all rights reserved.
 */

package main;

import pool.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Constructor {

    final static String outputFile = "D:\\_data\\result.csv";
    final static String logFile = "D:\\_data\\log.txt";
    final static String inputDir = "D:\\_data\\2015_2\\";

    public static void main(String[] args) {

        final boolean LOG = true;
        final int N = 1;

        int AVE = 0, MIN = Integer.MAX_VALUE, MAX = 0;
        long AVE_TIME = 0, MIN_TIME = Long.MAX_VALUE, MAX_TIME = 0;

        PrintWriter pw_result = null, pw_log = null;
        try {
            pw_result = new PrintWriter(new File(outputFile));
            if (LOG)
                pw_log = new PrintWriter(new File(logFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return;
        }

        for (int x = 0; x < N; x++) {

            ArrayList<Course> courses = null;
            ArrayList<Student> students = null;
            ArrayList<DivideClass> classes = null;
            ArrayList<Period> periods = null;

            // Parsing
            Parser parser = new Parser(inputDir, LOG, pw_log);
            try {
                courses = parser.parseCourse();
                students = parser.parseStudent();
                periods = parser.parsePeriod();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            // //////////

            long start = System.currentTimeMillis();

            // Student assigning
            Group.prepare();
            StudentManager studentManager = new StudentManager(students, LOG, pw_log);
            classes = studentManager.assignStudents();
            // //////////

            // Class assigning
            ClassManager classManager = new ClassManager(classes, periods, LOG, pw_log);
            int colors = classManager.assignClasses();
            // //////////

            long end = System.currentTimeMillis();

            // Result
            long time = end - start;
            pw_result.println(colors + "," + time);
            AVE += colors;
            AVE_TIME += time;
            if (colors > MAX)
                MAX = colors;
            if (colors < MIN)
                MIN = colors;
            if (time > MAX_TIME)
                MAX_TIME = time;
            if (time < MIN_TIME)
                MIN_TIME = time;
            // //////////

            // Log
            if (LOG) {
                pw_log.println("==========COURSE DATA==========");
                for (Course course : courses)
                    pw_log.println(course.name + "\t" + course.getClassNumber() + "c\t" + course.getStudentNumber()
                            + "s\t" + course.getTimeComposition());
                pw_log.println();
                pw_log.println("==========STUDENT DATA==========");
                for (Student student : students)
                    pw_log.println(student.id + ": " + student.getCourses());
                pw_log.println();
                pw_log.println("==========CLASS DATA==========");
                for (DivideClass divideClass : classes)
                    pw_log.println(divideClass.toString()
                            + divideClass.getStudents().toString());
                pw_log.println();
                pw_log.println("==========STUDENT DATA==========");
                for (Student student : students)
                    pw_log.println(student.id + ": " + student.getClasses());
                pw_log.println();
                pw_log.println("==========PERIOD DATA==========");
                for (DivideClass divideClass : classes)
                    pw_log.println(divideClass.toString()
                            + divideClass.getPeriods());
            } else
                System.out.println(x + 1);
            // //////////
        }

        pw_result.close();
        if (LOG)
            pw_log.close();

        System.out.println("NUM\tTIME");
        System.out.println(1. * AVE / N + "\t" + 1. * AVE_TIME / N);
        System.out.println(MIN + "\t" + MIN_TIME);
        System.out.println(MAX + "\t" + MAX_TIME);

    }
}