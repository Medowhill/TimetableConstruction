package main;

import java.util.ArrayList;

import pool.DivideClass;
import pool.Graph;
import pool.Period;

public class ClassManager {

	public boolean assignClasses(ArrayList<DivideClass> classes,
			Period[][] periods) {

		int prevMax = Integer.MAX_VALUE;
		Period usedPeriod = new Period(0, 0);

		while (true) {

			int max = 0;
			for (DivideClass divideClass : classes) {
				int maxContinuousTime = divideClass.getMaxContinuousTime();
				if (maxContinuousTime > max)
					max = maxContinuousTime;
			}

			if (max == 0)
				return true;

			Period[] assigningPeriods = getPeriods(periods, max, usedPeriod);

			if (assigningPeriods == null)
				return false;

			ArrayList<DivideClass> assigningClasses = new ArrayList<>();
			for (DivideClass divideClass : classes)
				if (divideClass.getMaxContinuousTime() == max
						&& divideClass.canUse(assigningPeriods))
					assigningClasses.add(divideClass);

			Graph graph = new Graph(assigningClasses.size());

		}

	}

	private Period[] getPeriods(Period[][] periods, int time, Period usedPeriod) {
		Period[] periodList = periods[usedPeriod.day];
		Period[] result = new Period[time];

		int add = 0;
		int prevHour = 0;

		for (int i = 0; i < periodList.length; i++) {
			int hour = periodList[i].hour;
			if (usedPeriod.hour >= hour)
				continue;
			if (prevHour + 1 == hour)
				result[add++] = periodList[i];
			else {
				result[0] = periodList[i];
				add = 1;
			}
			if (add == time)
				return result;
		}

		return null;
	}
}
