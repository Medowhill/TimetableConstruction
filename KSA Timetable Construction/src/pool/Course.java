/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

public class Course {
	// ����

	// �����
	public final String name;

	// ���� ���� �й��� ��
	private int classNumber;

	// ������ ���� ��û�� �л��� ��
	private int studentNumber;

	// ������ ����� �� �ִ� ������ ��
	// �� ���ÿ� ������ �й��� �ִ� �� �� ��ġ�� �� �ִ����� �����Ѵ�.
	private int teacherNumber;

	// ������ ���� �ü� ����
	// timeComposition[i-1]�� i���� ���� ������ ���� �ǹ��Ѵ�.
	// {2, 1}�̶�� 1���� ���� 2���� 2���� ���� 1���� ������ �����̴�.
	private int[] timeComposition;

	// ���� �л��� ���� ���� �й� ��ü�̴�.
	private DivideClass assigningClass;

	// ���ݱ��� ���� �й��� ���̴�.
	private int madeClass;

	// ������
	public Course(String name) {
		this.name = name;
	}

	// ���� �л��� ���� ���� �й��� return
	public DivideClass getAssigningClass() {
		if (assigningClass == null || assigningClass.isFull())
			assigningClass = new DivideClass(this, ++madeClass, (madeClass - 1)
					% teacherNumber, studentNumber / classNumber
					+ ((studentNumber % classNumber >= madeClass) ? 1 : 0),
					timeComposition);
		return assigningClass;
	}

	// �й� �� setter
	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	// �л� �� setter
	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	// ���� �� setter
	public void setTeacherNumber(int teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	// ���� �ü� ���� setter
	public void setTimeComposition(int[] timeComposition) {
		this.timeComposition = timeComposition;
	}

	@Override
	public String toString() {
		return name;
	}

}