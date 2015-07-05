/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

public class Course {
    // ����

    // �����
    public final String name;

    // ������ �й� ����Ʈ
    public final DivideClass[] classes;

    // ���� ���� �й��� ��
    private final int classNumber;

    // ������ ���� ��û�� �л��� ��
    private final int studentNumber;

    // ������ ���� �ü� ����
    private final int timeComposition;

    // ������
    public Course(String name, int classNumber, int studentNumber, int timeComposition) {
        this.name = name;
        this.classNumber = classNumber;
        this.studentNumber = studentNumber;
        this.timeComposition = timeComposition;

        classes = new DivideClass[classNumber];
        for (int i = 0; i < classNumber; i++)
            classes[i] = new DivideClass(this, i + 1, studentNumber / classNumber, timeComposition);
    }

    @Override
    public String toString() {
        return name;
    }

}