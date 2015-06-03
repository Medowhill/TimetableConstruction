/**
 * (C) 2015. È«Àç¹Î all rights reserved.
 */

package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import pool.Course;
import pool.DivideClass;
import pool.Period;
import pool.Student;

public class Constructor {

	public static void main(String[] args) {

		boolean log = false;
		int N = 100;
		int AVE = 0, MIN = Integer.MAX_VALUE, MAX = 0;
		long start = System.currentTimeMillis();

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

			for (int i = 0; i < students.size(); i++) {
				int rand = (int) (Math.random() * (students.size() - i));
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

			ClassManager classManager = new ClassManager();
			int[] success = classManager.assignClasses(classes, periods);

			if (log) {
				for (DivideClass divideClass : classes)
					System.out.println(divideClass.toString()
							+ divideClass.getPeriods());
			}

			if (success != null) {
				int sum = 0;
				for (int i = 0; i < success.length; i++)
					sum += success[i] + 1;
				System.out.println(sum);
				AVE += sum;
				if (sum > MAX)
					MAX = sum;
				if (sum < MIN)
					MIN = sum;
			}

		}

		long end = System.currentTimeMillis();

		System.out.println();
		System.out.println(1. * AVE / N);
		System.out.println(MIN);
		System.out.println(MAX);
		System.out.println(1. * (end - start) / N);

	}
}
