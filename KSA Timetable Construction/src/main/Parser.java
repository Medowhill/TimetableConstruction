/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import pool.Course;
import pool.Period;
import pool.Student;

public class Parser {
	// ������ �о� ������ parsing�ϴ� parser

	private ArrayList<Course> courses;

	// ���� ������ parsing
	public ArrayList<Course> parseCourse(String fileName)
			throws FileNotFoundException {
		courses = new ArrayList<>();

		Scanner scanner = new Scanner(new File(fileName));

		// ����� parsing
		String line = scanner.nextLine();
		String[] data = line.split(",");
		for (int i = 0; i < data.length; i++)
			courses.add(new Course(data[i]));

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
		for (int i = 0; i < data.length; i++) {
			String[] timeCompositionString = data[i].split("R");
			int[] timeComposition = new int[timeCompositionString.length];
			for (int j = 0; j < timeCompositionString.length; j++)
				timeComposition[j] = Integer.parseInt(timeCompositionString[j]);
			courses.get(i).setTimeComposition(timeComposition);
		}

		// ���� ���� �� parsing
		line = scanner.nextLine();
		data = line.split(",");
		for (int i = 0; i < data.length; i++)
			courses.get(i).setTeacherNumber(Integer.parseInt(data[i]));

		scanner.close();

		return courses;
	}

	// �л� ������ parsing
	public ArrayList<Student> parseStudent(String fileName)
			throws FileNotFoundException {
		ArrayList<Student> students = new ArrayList<>();

		Scanner scanner = new Scanner(new File(fileName));

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] data = line.split(",");

			Student student = new Student(data[0], Integer.parseInt(data[1]));
			for (int i = 2; i < data.length; i++)
				if (data[i].equals("1"))
					student.addCourse(courses.get(i - 2));

			students.add(student);
		}

		scanner.close();

		return students;
	}

	// ���� ������ parsing
	public Period[][] parsePeriod(String fileName) throws FileNotFoundException {
		Period[][] periods = new Period[5][];

		Scanner scanner = new Scanner(new File(fileName));

		for (int i = 0; i < 5; i++) {
			String line = scanner.nextLine();
			String[] data = line.split(",");

			periods[i] = new Period[data.length];
			for (int j = 0; j < data.length; j++)
				periods[i][j] = new Period(i, Integer.parseInt(data[j]));
		}

		return periods;
	}
}
