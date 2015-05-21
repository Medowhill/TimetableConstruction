/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

import java.util.*;

public class Student {
	// 학생

	// 학번
	public final String id;

	// 학년
	public final int grade;

	// 수강 신청한 과목
	private ArrayList<Course> courses;

	// 배정된 분반
	private ArrayList<DivideClass> classes;

	// 배치된 교시
	private HashSet<Period> periods;

	// 생성자
	public Student(String id, int grade) {
		this.id = id;
		this.grade = grade;
		courses = new ArrayList<>();
		classes = new ArrayList<>();
	}

	// 과목을 추가
	public void addCourse(Course course) {
		courses.add(course);
	}

	// 분반을 추가
	public void addClass(DivideClass divideClass) {
		classes.add(divideClass);
	}

	// 과목 getter
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public String toString() {
		return id;
	}

}