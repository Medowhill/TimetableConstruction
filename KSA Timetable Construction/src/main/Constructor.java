/**
 * (C) 2015. È«Àç¹Î all rights reserved.
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import pool.Course;
import pool.DivideClass;
import pool.Period;
import pool.Student;

public class Constructor {

	public static void main(String[] args) {

		boolean log = true;
		int N = 1;

		String outputFile = "D:\\result.txt";
		String logFile = "D:\\log.txt";

		int AVE = 0, MIN = Integer.MAX_VALUE, MAX = 0;
		long AVE_TIME = 0, MIN_TIME = Long.MAX_VALUE, MAX_TIME = 0;

		PrintWriter pw = null;
		PrintWriter pw_log = null;

		try {
			pw = new PrintWriter(new File(outputFile));
			if (log)
				pw_log = new PrintWriter(new File(logFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}

		StudentManager.log = log;
		ClassManager.log = log;
		StudentManager.pw = pw_log;
		ClassManager.pw = pw_log;

		for (int x = 0; x < N; x++) {

			ArrayList<Course> courses = null;
			ArrayList<Student> students = null;
			ArrayList<DivideClass> classes = null;
			Period[][] periods = null;

			Parser parser = new Parser();
			try {
				courses = parser.parseCourse("2015_1_course.csv");
				students = parser.parseStudent("2015_1_student.csv");
				periods = parser.parsePeriod("2015_1_period.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(0);
			}

			long start = System.currentTimeMillis();

			StudentManager studentManager = new StudentManager(students);
			studentManager.sortRandomly();
			studentManager.sort();
			classes = studentManager.assignStudents();

			ClassManager classManager = new ClassManager(classes, periods);
			int period = classManager.assignClasses();

			long end = System.currentTimeMillis();

			// LOG =============================================================
			if (log) {
				for (DivideClass divideClass : classes) {
					pw_log.println(divideClass.toString()
							+ divideClass.getStudents().toString());
					pw_log.println(divideClass.toString()
							+ divideClass.getPeriods());
				}
			}
			// LOG =============================================================

			// RESULT ==========================================================
			if (period != -1) {
				long time = end - start;

				AVE += period;
				AVE_TIME += time;

				if (period > MAX)
					MAX = period;
				if (period < MIN)
					MIN = period;
				if (time > MAX_TIME)
					MAX_TIME = time;
				if (time < MIN_TIME)
					MIN_TIME = time;

				System.out.println(x + 1);
				pw.println(period + "\t" + time);
			} else {
				x--;
			}
			// RESULT ==========================================================

		}

		pw.close();
		pw_log.close();

		System.out.println(1. * AVE / N + "\t" + 1. * AVE_TIME / N);
		System.out.println(MIN + "\t" + MIN_TIME);
		System.out.println(MAX + "\t" + MAX_TIME);

	}
}
