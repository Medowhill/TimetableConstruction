/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.*;

public class Student implements Comparable<Student> {
	// �л�

	// �й�
	public final String id;

	// �г�
	public final int grade;

	// ���� ��û�� ����
	private ArrayList<Course> courses;

	// ������ �й�
	private ArrayList<DivideClass> classes;

	// ��ġ�� ����
	private HashSet<Period> periods;

	// ������
	public Student(String id, int grade) {
		this.id = id;
		this.grade = grade;
		courses = new ArrayList<>();
		classes = new ArrayList<>();
		periods = new HashSet<>();
	}

	// ������ �߰�
	public void addCourse(Course course) {
		courses.add(course);
	}

	// �й��� �߰�
	public void addClass(DivideClass divideClass) {
		classes.add(divideClass);
	}

	// ���ø� �߰�
	public void addPeriods(Period[] newPeriod) {
		for (int i = 0; i < newPeriod.length; i++)
			periods.add(newPeriod[i]);
	}

	// �ش� ���ÿ� ������ �� �ִ��� return
	public boolean canUse(Period[] newPeriod) {
		for (int i = 0; i < newPeriod.length; i++)
			if (periods.contains(newPeriod[i]))
				return false;
		return true;
	}

	// ���� getter
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