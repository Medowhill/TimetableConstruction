/**
 * (C) 2015. 홍재민 all rights reserved.
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
	// 파일을 읽어 정보를 parsing하는 parser

	private ArrayList<Course> courses;

	// 과목 정보를 parsing
	public ArrayList<Course> parseCourse(String fileName)
			throws FileNotFoundException {
		courses = new ArrayList<>();

		Scanner scanner = new Scanner(new File(fileName));

		// 과목명 parsing
		String line = scanner.nextLine();
		String[] data = line.split(",");
		for (int i = 0; i < data.length; i++)
			courses.add(new Course(data[i]));

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
		for (int i = 0; i < data.length; i++) {
			String[] timeCompositionString = data[i].split("R");
			int[] timeComposition = new int[timeCompositionString.length];
			for (int j = 0; j < timeCompositionString.length; j++)
				timeComposition[j] = Integer.parseInt(timeCompositionString[j]);
			courses.get(i).setTimeComposition(timeComposition);
		}

		// 과목 교사 수 parsing
		line = scanner.nextLine();
		data = line.split(",");
		for (int i = 0; i < data.length; i++)
			courses.get(i).setTeacherNumber(Integer.parseInt(data[i]));

		scanner.close();

		return courses;
	}

	// 학생 정보를 parsing
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

	// 교시 정보를 parsing
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
