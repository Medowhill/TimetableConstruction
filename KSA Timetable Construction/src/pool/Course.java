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
    private int timeComposition;

    // ���� �л��� ���� ���� �й� ��ü�̴�.
    private DivideClass assigningClass;

    // ���ݱ��� ���� �й��� ���̴�.
    private int madeClass;

    // �ش� �г��� �ʼ� ���� 0�̸� �ʼ��� �ƴϴ�.
    private int necessary;

    // ������
    public Course(String name) {
        this.name = name;
    }

    // ���� �л��� ���� ���� �й��� return
    public DivideClass getAssigningClass() {
        if (assigningClass == null || assigningClass.isFull())
            assigningClass = new DivideClass(this, ++madeClass, studentNumber
                    / classNumber
                    + ((studentNumber % classNumber >= madeClass) ? 1 : 0),
                    timeComposition);
        return assigningClass;
    }

    // �й� �� getter
    public int getClassNumber() {
        return classNumber;
    }

    // �й� �� setter
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    // �л� �� getter
    public int getStudentNumber() {
        return studentNumber;
    }

    // �л� �� setter
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    // ���� �ü� ���� getter
    public int getTimeComposition() {
        return timeComposition;
    }

    // ���� �ü� ���� setter
    public void setTimeComposition(int timeComposition) {
        this.timeComposition = timeComposition;
    }

    // �ʼ� �г� getter
    public int getNecessary() {
        return necessary;
    }

    // �ʼ� �г� setter
    public void setNecessary(int necessary) {
        this.necessary = necessary;
    }

    @Override
    public String toString() {
        return name;
    }

}