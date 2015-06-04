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

		boolean log = false;
		int N = 1;
		String outputFile = "D:\\result.txt";

		int AVE = 0, MIN = Integer.MAX_VALUE, MAX = 0;
		long AVE_TIME = 0, MIN_TIME = Long.MAX_VALUE, MAX_TIME = 0;

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(outputFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}

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

			for (int i = 0; i < students.size(); i++) {
				int rand = (int) (Math.random() * (students.size() - i)) + i;
				Student tmp = students.get(rand);
				students.set(rand, students.get(i));
				students.set(i, tmp);
			}

			StudentManager studentManager = new StudentManager();
			classes = studentManager.assignStudents(students);

			if (log) {
				for (DivideClass divideClass : classes)
					System.out.println(divideClass.toString()
							+ divideClass.getStudents().toString());
			}

			ClassManager classManager = new ClassManager(log);
			int[] success = classManager.assignClasses(classes, periods);

			long end = System.currentTimeMillis();

			if (log) {
				for (DivideClass divideClass : classes)
					System.out.println(divideClass.toString()
							+ divideClass.getPeriods());
			}

			if (success != null) {

				int sum = 0;
				for (int i = 0; i < success.length; i++)
					sum += success[i] + 1;

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

		}

		pw.close();
		System.out.println(N);
		System.out.println(1. * AVE / N + "\t" + 1. * AVE_TIME / N);
		System.out.println(MIN + "\t" + MIN_TIME);
		System.out.println(MAX + "\t" + MAX_TIME);

	}
}
