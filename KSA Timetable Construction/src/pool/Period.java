/**
 * (C) 2015. 홍재민 all rights reserved.
 */

package pool;

import java.util.*;

public class Period {
	// 교시

	// 요일
	public final int day;

	// 교시
	public final int hour;

	// 교시에 배치되어 있는 분반
	private ArrayList<DivideClass> classes;

	// 생성자
	public Period(int day, int hour) {
		this.day = day;
		this.hour = hour;
		classes = new ArrayList<>();
	}

	@Override
	public String toString() {
		return (day + 1) + "-" + hour;
	}

}