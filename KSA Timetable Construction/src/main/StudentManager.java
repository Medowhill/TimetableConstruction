package main;

import java.util.ArrayList;

import pool.Course;
import pool.DivideClass;
import pool.Student;

public class StudentManager {

	// 학생을 분반에 배정
	public ArrayList<DivideClass> assignStudent(ArrayList<Student> students) {
		ArrayList<DivideClass> classes = new ArrayList<>();

		for (Student student : students) {
			for (Course course : student.getCourses()) {
				DivideClass divideClass = course.getAssigningClass();

				divideClass.addStudent(student);
				student.addClass(divideClass);

				if (divideClass.getStudentNumber() == 1)
					classes.add(divideClass);
			}
		}

		return classes;
	}

}
