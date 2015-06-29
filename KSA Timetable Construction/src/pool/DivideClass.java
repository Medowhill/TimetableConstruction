/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class DivideClass {
    // 분반

    // 분반 번호
    public final int number;
    // 분반 정원
    private final int maxStudentNumber;
    // 분반의 과목
    private Course course;
    // 아직 배치되지 않은 분반의 수업 시수
    private int[] lastingTimeComposition;
    // 분반에 배정된 학생들
    private LinkedHashSet<Student> students;

    // 분반이 배치된 교시들
    private ArrayList<Period> periods;

    // 생성자
    public DivideClass(Course course, int number, int maxStudentNumber,
                       int[] timeComposition) {
        this.course = course;
        this.number = number;
        this.maxStudentNumber = maxStudentNumber;

        lastingTimeComposition = new int[timeComposition.length];
        for (int i = 0; i < timeComposition.length; i++)
            lastingTimeComposition[i] = timeComposition[i];

        students = new LinkedHashSet<>();
        periods = new ArrayList<>();
    }

    // 분반의 정원이 다 찼는지를 return
    public boolean isFull() {
        return students.size() == maxStudentNumber;
    }

    // 분반에 학생을 추가
    public void addStudent(Student student) {
        if (students.size() < maxStudentNumber)
            students.add(student);
    }

    // 아직 배치되지 않은 수업 중 가장 긴 연속 수업 시수를 return
    public int getMaxContinuousTime() {
        for (int i = lastingTimeComposition.length - 1; i >= 0; i--)
            if (lastingTimeComposition[i] != 0)
                return i + 1;
        return 0;
    }

    // 분반을 해당 교시에 배치할 수 있는지 return
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

    // 분반을 해당 교시에 배치
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

    // 현재 배정된 학생 수를 return
    public int getStudentNumber() {
        return students.size();
    }

    // 과목 getter
    public Course getCourse() {
        return course;
    }

    // 학생 getter
    public HashSet<Student> getStudents() {
        return students;
    }

    // 교시 getter
    public ArrayList<Period> getPeriods() {
        return periods;
    }

    @Override
    public String toString() {
        return course.toString() + "[" + number + "]";
    }

}