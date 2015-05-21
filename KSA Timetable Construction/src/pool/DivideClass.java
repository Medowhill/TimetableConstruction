/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.*;

public class DivideClass {
	// �й�

	// �й��� ����
	private Course course;

	// �й� ��ȣ
	public final int number;

	// ���� ��ġ���� ���� �й��� ���� �ü�
	private int[] lastingTimeComposition;

	// �й� ����
	private final int maxStudentNumber;

	// �йݿ� ������ �л���
	private ArrayList<Student> students;

	// �й��� ��ġ�� ���õ�
	private ArrayList<Period> periods;

	// ������
	public DivideClass(Course course, int number, int maxStudentNumber,
			int[] timeComposition) {
		this.course = course;
		this.number = number;
		this.maxStudentNumber = maxStudentNumber;

		lastingTimeComposition = new int[timeComposition.length];
		for (int i = 0; i < timeComposition.length; i++)
			lastingTimeComposition[i] = timeComposition[i];

		students = new ArrayList<>();
		periods = new ArrayList<>();
	}

	// �й��� ������ �� á������ return
	public boolean isFull() {
		return students.size() == maxStudentNumber;
	}

	// �йݿ� �л��� �߰�
	public void addStudent(Student student) {
		if (students.size() < maxStudentNumber)
			students.add(student);
	}

	// ���� ��ġ���� ���� ���� �� ���� �� ���� ���� �ü��� return
	public int getMaxContinuousTime() {
		for (int i = lastingTimeComposition.length - 1; i > 0; i--)
			if (lastingTimeComposition[i] != 0)
				return i + 1;
		return 0;
	}

	// �й��� �ش� ���ÿ� ��ġ�� �� �ִ��� return
	public boolean canUse(Period[] newPeriod) {
		int day = newPeriod[0].day;
		for (Period period : periods)
			if (period.day == day)
				return false;
		for (Student student : students)
			if (!student.canUse(newPeriod))
				return false;
		return true;
	}

	// �й��� �ش� ���ÿ� ��ġ
	public void addPeriods(Period[] newPeriod) {
		if (lastingTimeComposition.length >= newPeriod.length
				&& lastingTimeComposition[newPeriod.length - 1] > 0) {
			for (int i = 0; i < newPeriod.length; i++) {
				periods.add(newPeriod[i]);
				for (Student student : students)
					student.addPeriods(newPeriod);
			}
			lastingTimeComposition[newPeriod.length - 1]--;
		}
	}

	// ���� ������ �л� ���� return
	public int getStudentNumber() {
		return students.size();
	}

	// ���� getter
	public Course getCourse() {
		return course;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public String toString() {
		return course.toString() + "[" + number + "]: " + students;
	}

}