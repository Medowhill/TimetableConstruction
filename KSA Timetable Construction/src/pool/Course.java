/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.ArrayList;

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

    private ArrayList<DivideClass> classes;

    // ���� �л��� ���� ���� �й� ��ü�̴�.
    private int assigningClass;

    // ������
    public Course(String name) {
        this.name = name;
    }

    // ���� �л��� ���� ���� �й��� return
    public DivideClass getAssigningClass() {
        if (classes.isEmpty())
            return null;
        return classes.get(assigningClass);
    }

    // �й� �� setter
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    // �л� �� setter
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
        classes = new ArrayList<>();
        for (int i = 0; i < classNumber; i++)
            classes.add(new DivideClass(this, i + 1, studentNumber / classNumber
                    + ((studentNumber % classNumber >= i + 1) ? 1 : 0), timeComposition));
    }

    // ���� �ü� ���� setter
    public void setTimeComposition(int timeComposition) {
        this.timeComposition = timeComposition;
    }

    public void nextClass() {
        if (classes.isEmpty())
            return;
        if (classes.get(assigningClass).isFull())
            classes.remove(assigningClass);
        else
            assigningClass++;
        if (assigningClass == classes.size())
            assigningClass = 0;
    }

    @Override
    public String toString() {
        return name;
    }

}