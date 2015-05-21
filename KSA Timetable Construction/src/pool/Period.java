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

	// �й��� ���ÿ� ��ġ
	public void addClasses(DivideClass[] newClass) {
		for (int i = 0; i < newClass.length; i++)
			classes.add(newClass[i]);
	}

}