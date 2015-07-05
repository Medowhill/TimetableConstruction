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

    // 아직 분반의 수업 시수
    private int timeComposition;

    // 분반에 배정된 학생들
    private LinkedHashSet<Student> students;

    // 분반이 배치된 교시들
    private ArrayList<Period> periods;

    // 생성자
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

    // 분반에 학생을 추가
    public void addStudent(Student student) {
        if (students.size() < maxStudentNumber)
            students.add(student);
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