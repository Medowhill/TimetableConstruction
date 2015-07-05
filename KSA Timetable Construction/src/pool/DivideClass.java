/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class DivideClass {
    // �й�

    // �й� ��ȣ
    public final int number;

    // �й� ����
    private final int maxStudentNumber;

    // �й��� ����
    private Course course;

    // ���� �й��� ���� �ü�
    private int timeComposition;

    // �йݿ� ������ �л���
    private LinkedHashSet<Student> students;

    // �й��� ��ġ�� ���õ�
    private ArrayList<Period> periods;

    // ������
    public DivideClass(Course course, int number, int maxStudentNumber, int timeComposition) {
        this.course = course;
        this.number = number;
        this.maxStudentNumber = maxStudentNumber;
        this.timeComposition = timeComposition;

        students = new LinkedHashSet<>();
        periods = new ArrayList<>();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    // �йݿ� �л��� �߰�
    public void addStudent(Student student) {
        if (students.size() < maxStudentNumber)
            students.add(student);
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

    // ���� ������ �л� ���� return
    public int getStudentNumber() {
        return students.size();
    }

    // ���� getter
    public Course getCourse() {
        return course;
    }

    // �л� getter
    public HashSet<Student> getStudents() {
        return students;
    }

    // ���� getter
    public ArrayList<Period> getPeriods() {
        return periods;
    }

    // Max student getter
    public int getMaxStudentNumber() {
        return maxStudentNumber;
    }

    // Time Composition getter
    public int getTimeComposition() {
        return timeComposition;
    }

    public boolean hasEdgeAlready(DivideClass dc) {
        for (Student student : students)
            if (student.getClasses().contains(dc))
                return true;
        return false;
    }

    public int multipleEdge(Course course) {
        boolean[] edge = new boolean[course.getClassNumber()];
        for (Student student : students)
            for (DivideClass dc : student.getClasses())
                if (dc.course == course)
                    edge[dc.number - 1] = true;

        int sum = 0;
        for (int i = 0; i < edge.length; i++)
            if (edge[i])
                sum++;
        return sum;
    }

    @Override
    public String toString() {
        return course.toString() + "[" + number + "]";
    }

}