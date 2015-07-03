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

    // ���� �йݿ� ������ �л��� ��
    private int remainStudentNumber;

    // ���� �л��� �������� �й�
    private DivideClass assigningClass;

    // ������� ���� �й��� ��
    private int madeClass;

    // ������
    public Course(String name) {
        this.name = name;
    }

    // ���� �л��� ���� ���� �й��� return
    public DivideClass getAssigningClass() {
        if (assigningClass == null)
            assigningClass = new DivideClass(this, ++madeClass,
                    remainStudentNumber / (classNumber - madeClass + 1) + ((remainStudentNumber % (classNumber - madeClass + 1) != 0) ? 1 : 0),
                    timeComposition);
        return assigningClass;
    }

    public int getBalance() {
        return assigningClass.getBalance();
    }

    public void finishCurrentAssigning() {
        remainStudentNumber -= assigningClass.getStudentNumber();
        assigningClass = null;
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
        remainStudentNumber = studentNumber;
    }

    // ���� �ü� ���� getter
    public int getTimeComposition() {
        return timeComposition;
    }

    // ���� �ü� ���� setter
    public void setTimeComposition(int timeComposition) {
        this.timeComposition = timeComposition;
    }

    @Override
    public String toString() {
        return name;
    }

}