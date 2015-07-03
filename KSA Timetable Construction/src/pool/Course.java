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

    // 남은 분반에 배정할 학생의 수
    private int remainStudentNumber;

    // 현재 학생을 배정중인 분반
    private DivideClass assigningClass;

    // 현재까지 만든 분반의 수
    private int madeClass;

    // 생성자
    public Course(String name) {
        this.name = name;
    }

    // 현재 학생을 배정 중인 분반을 return
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

    // 분반 수 getter
    public int getClassNumber() {
        return classNumber;
    }

    // 분반 수 setter
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    // 학생 수 getter
    public int getStudentNumber() {
        return studentNumber;
    }

    // 학생 수 setter
    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
        remainStudentNumber = studentNumber;
    }

    // 수업 시수 구성 getter
    public int getTimeComposition() {
        return timeComposition;
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