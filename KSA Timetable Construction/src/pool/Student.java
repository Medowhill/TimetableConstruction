/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.*;

public class Student {
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
	}

	// ������ �߰�
	public void addCourse(Course course) {
		courses.add(course);
	}

	// �й��� �߰�
	public void addClass(DivideClass divideClass) {
		classes.add(divideClass);
	}

	// ���� getter
	public ArrayList<Course> getCourses() {
		return courses;
	}

	public String toString() {
		return id;
	}

}