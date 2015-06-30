/**
 * (C) 2015. È«Àç¹Î all rights reserved.
 */

package main;

import pool.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Constructor {

    public static void main(String[] args) {

        boolean log = false;
        int N = 1;

        String outputFile = "D:\\result.txt";
        String logFile = "D:\\log.csv";

        int AVE = 0, MIN = Integer.MAX_VALUE, MAX = 0;
        long AVE_TIME = 0, MIN_TIME = Long.MAX_VALUE, MAX_TIME = 0;

        PrintWriter pw = null, pw_log = null;
        try {
            pw = new PrintWriter(new File(outputFile));
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

            Parser parser = new Parser();

            try {
                courses = parser.parseCourse("D:\\2015_2_course.csv");
                students = parser.parseStudent("D:\\2015_2_student.csv");
                periods = parser.parsePeriod("D:\\2015_2_period.csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }

            long start = System.currentTimeMillis();

            StudentManager studentManager = new StudentManager(students, courses, log);
            studentManager.sortRandomly();
            classes = studentManager.assignStudents();

            if (log) {
                for (DivideClass divideClass : classes)
                    System.out.println(divideClass.toString()
                            + divideClass.getStudents().toString());
            }

            Group.prepare();
            ClassManager classManager = new ClassManager(log);
            int colors = classManager.assignClasses(classes, periods);

            long end = System.currentTimeMillis();

            if (log) {
                for (DivideClass divideClass : classes)
                    System.out.println(divideClass.toString()
                            + divideClass.getPeriods());
            }

            for (DivideClass divideClass : classes) {
                pw_log.print(divideClass + ",");
                for (DivideClass divideClass1 : classes) {
                    if (divideClass1 != divideClass) {
                        for (Student s : divideClass.getStudents()) {
                            if (divideClass1.getStudents().contains(s)) {
                                pw_log.print(divideClass1 + ",");
                                break;
                            }
                        }
                    }
                }
                pw_log.println();
            }

            System.out.println(x + 1);

            int sum = colors;

            long time = end - start;

            pw.println(sum + "\t" + time);
            AVE += sum;
            AVE_TIME += time;
            if (sum > MAX)
                MAX = sum;
            if (sum < MIN)
                MIN = sum;
            if (time > MAX_TIME)
                MAX_TIME = time;
            if (time < MIN_TIME)
                MIN_TIME = time;

        }

        pw.close();
        pw_log.close();
        System.out.println(1. * AVE / N + "\t" + 1. * AVE_TIME / N);
        System.out.println(MIN + "\t" + MIN_TIME);
        System.out.println(MAX + "\t" + MAX_TIME);

    }
}
