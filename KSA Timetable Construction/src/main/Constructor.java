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

	private static ArrayList<Course> courses = null;
	private static ArrayList<Student> students = null;
	private static ArrayList<DivideClass> classes = null;
	private static ArrayList<Period> periods = null;

	public static void main(String[] args) {

		Parser parser = new Parser();

		try {
			courses = parser.parseCourse("2015_1_course.csv");
			students = parser.parseStudent("2015_1_student.csv");
			periods = parser.parsePeriod("2015_1_period.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		StudentManager studentManager = new StudentManager();
		classes = studentManager.assignStudents(students);

		// for (DivideClass divideClass : classes)
		// System.out.println(divideClass.toString()
		// + divideClass.getStudents().toString());

		ClassManager classManager = new ClassManager();
		boolean success = classManager.assignClasses(classes, periods);

		for (DivideClass divideClass : classes)
			System.out.println(divideClass.toString()
					+ divideClass.getPeriods());

		System.out.println(success);
	}

}
