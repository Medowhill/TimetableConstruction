/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

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
    private LinkedHashSet<Period> periods;

    // ������
    public Student(String id, int grade) {
        this.id = id;
        this.grade = grade;
        courses = new ArrayList<>();
        classes = new ArrayList<>();
        periods = new LinkedHashSet<>();
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

    // �й� getter
    public ArrayList<DivideClass> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return id;
    }

}