package main;

import java.util.ArrayList;

import pool.DivideClass;
import pool.Graph;
import pool.Period;
import pool.Student;

public class ClassManager {

	public boolean assignClasses(ArrayList<DivideClass> classes,
			ArrayList<Period> periods) {

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

			if (max < prevMax)
				usedPeriod = new Period(0, 0);

			// log
			System.out.println(max);

			Period[] assigningPeriods = getPeriods(periods, max, usedPeriod);

			if (assigningPeriods == null)
				return false;

			// log
			for (int i = 0; i < assigningPeriods.length; i++)
				System.out.println(assigningPeriods[i]);

			ArrayList<DivideClass> assigningClasses = new ArrayList<>();
			for (DivideClass divideClass : classes)
				if (divideClass.getMaxContinuousTime() == max
						&& divideClass.canUse(assigningPeriods))
					assigningClasses.add(divideClass);

			if (!assigningClasses.isEmpty()) {
				Graph graph = new Graph(assigningClasses.size());

				for (int i = 0; i < assigningClasses.size(); i++) {
					for (int j = i + 1; j < assigningClasses.size(); j++) {
						if (makeEdge(assigningClasses.get(i),
								assigningClasses.get(j)))
							graph.addEdge(i, j);
					}
				}

				int[] maxClique = graph.maxClique(false);

				ArrayList<DivideClass> newClasses = new ArrayList<>();
				for (int i = 0; i < maxClique.length; i++)
					newClasses.add(assigningClasses.get(maxClique[i]));

				// log
				System.out.println(newClasses);

				for (int i = 0; i < assigningPeriods.length; i++)
					assigningPeriods[i].addClasses(newClasses);

				for (DivideClass divideClass : newClasses)
					divideClass.addPeriods(assigningPeriods);
			}

			prevMax = max;
			usedPeriod = assigningPeriods[assigningPeriods.length - 1];

		}

	}

	private boolean makeEdge(DivideClass dc1, DivideClass dc2) {
		if (dc1.getCourse() == dc2.getCourse())
			return false;
		for (Student student : dc2.getStudents())
			if (dc1.getStudents().contains(student))
				return false;
		return true;
	}

	private Period[] getPeriods(ArrayList<Period> periods, int time,
			Period usedPeriod) {
		Period[] result = new Period[time];

		int add = 0;
		int prevHour = 0;

		for (Period period : periods) {
			if (period.day < usedPeriod.day)
				continue;
			int hour = period.hour;
			if (period.day == usedPeriod.day && hour <= usedPeriod.hour)
				continue;

			if (prevHour + 1 == hour)
				result[add++] = period;
			else {
				result[0] = period;
				add = 1;
			}

			prevHour = hour;

			if (add == time)
				return result;
		}

		return null;
	}
}
