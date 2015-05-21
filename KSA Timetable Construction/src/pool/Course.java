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

	// Getter
	public DivideClass getAssigningClass() {
		if (assigningClass == null || assigningClass.isFull())
			assigningClass = new DivideClass(this, ++madeClass, studentNumber
					/ classNumber
					+ ((studentNumber % classNumber >= madeClass) ? 1 : 0),
					timeComposition);
		return assigningClass;
	}

	// Setter
	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public void setStudentNumber(int studentNumber) {
		this.studentNumber = studentNumber;
	}

	public void setTimeComposition(int[] timeComposition) {
		this.timeComposition = timeComposition;
	}

}