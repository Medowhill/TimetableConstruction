/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

import java.util.ArrayList;

public class Course {
    // 과목

    // 과목명
    public final String name;

    // 과목에 속한 분반의 수
    private int classNumber;

    // 과목을 수강 신청한 학생의 수
    private int studentNumber;

    // 과목의 수업 시수 구성
    private int timeComposition;

    private ArrayList<DivideClass> classes;

    // 현재 학생을 배정 중인 분반 객체이다.
    private int assigningClass;

    // 생성자
    public Course(String name) {
        this.name = name;
    }

    // 현재 학생을 배정 중인 분반을 return
    public DivideClass getAssigningClass() {
        if (classes.isEmpty())
            return null;
        return classes.get(assigningClass);
    }

    // 분반 수 setter
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    // 학생 수 setter
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
        classes = new ArrayList<>();
        for (int i = 0; i < classNumber; i++)
            classes.add(new DivideClass(this, i + 1, studentNumber / classNumber
                    + ((studentNumber % classNumber >= i + 1) ? 1 : 0), timeComposition));
    }

    // 수업 시수 구성 setter
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