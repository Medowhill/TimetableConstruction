/**
 * (C) 2015. ȫ��� all rights reserved.
 */

package pool;

import java.util.*;

public class Period {
	// ����

	// ����
	public final int day;

	// ����
	public final int hour;

	// ���ÿ� ��ġ�Ǿ� �ִ� �й�
	private ArrayList<DivideClass> classes;

	// ������
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