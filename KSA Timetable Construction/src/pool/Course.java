/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

public class Course {
    // 과목

    // 과목명
    public final String name;

    // 과목의 분반 리스트
    private final DivideClass[] classes;

    // 과목에 속한 분반의 수
    private final int classNumber;

    // 과목을 수강 신청한 학생의 수
    private final int studentNumber;

    // 과목의 수업 시수 구성
    private final int timeComposition;

    // 한 교시에 동시에 수업할 수 있는 분반 수
    private final int maxClassSamePeriod;

    // 생성자
    public Course(String name, int classNumber, int studentNumber, int timeComposition, int maxClassSamePeriod) {
        this.name = name;
        this.classNumber = classNumber;
        this.studentNumber = studentNumber;
        this.timeComposition = timeComposition;
        this.maxClassSamePeriod = maxClassSamePeriod;

        classes = new DivideClass[classNumber];
        for (int i = 0; i < classNumber; i++)
            classes[i] = new DivideClass(this, i + 1, studentNumber / classNumber + 1, timeComposition);
    }

    public int getClassNumber() {
        return classNumber;
    }

    public DivideClass[] getClasses() {
        return classes;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public int getTimeComposition() {
        return timeComposition;
    }

    public int getMaxClassSamePeriod() {
        return maxClassSamePeriod;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}