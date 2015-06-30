/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

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

    // 현재 학생을 배정 중인 분반 객체이다.
    private DivideClass assigningClass;

    // 지금까지 만든 분반의 수이다.
    private int madeClass;

    // 생성자
    public Course(String name) {
        this.name = name;
    }

    // 현재 학생을 배정 중인 분반을 return
    public DivideClass getAssigningClass() {
        if (assigningClass == null || assigningClass.isFull())
            assigningClass = new DivideClass(this, ++madeClass, studentNumber
                    / classNumber
                    + ((studentNumber % classNumber >= madeClass) ? 1 : 0),
                    timeComposition);
        return assigningClass;
    }

    // 분반 수 setter
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    // 학생 수 setter
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    // 수업 시수 구성 setter
    public void setTimeComposition(int timeComposition) {
        this.timeComposition = timeComposition;
    }

    @Override
    public String toString() {
        return name;
    }

}