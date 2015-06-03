/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

import java.util.*;

public class Student implements Comparable<Student> {
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
		periods = new HashSet<>();
	}

	// 과목을 추가
	public void addCourse(Course course) {
		courses.add(course);
	}

	// 분반을 추가
	public void addClass(DivideClass divideClass) {
		classes.add(divideClass);
	}

	// 교시를 추가
	public void addPeriods(Period[] newPeriod) {
		for (int i = 0; i < newPeriod.length; i++)
			periods.add(newPeriod[i]);
	}

	// 해당 교시에 배정할 수 있는지 return
	public boolean canUse(Period[] newPeriod) {
		for (int i = 0; i < newPeriod.length; i++)
			if (periods.contains(newPeriod[i]))
				return false;
		return true;
	}

	// 과목 getter
	public ArrayList<Course> getCourses() {
		return courses;
	}

	@Override
	public String toString() {
		return id;
	}

	public ArrayList<DivideClass> getClasses() {
		return classes;
	}

	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		if (this.courses.size() > o.getCourses().size())
			return -1;
		else if (this.courses.size() == o.getCourses().size())
			return 0;
		else
			return 1;
	}
}