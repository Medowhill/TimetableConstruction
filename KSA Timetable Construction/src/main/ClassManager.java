package main;

import java.util.ArrayList;

import pool.DivideClass;
import pool.Graph;
import pool.Period;
import pool.Student;

class ClassManager {

	private boolean log;

	private int[] prevHour;

	ClassManager(boolean log) {
		this.log = log;
	}

	int[] assignClasses(ArrayList<DivideClass> classes, Period[][] periods) {

		int prevMax = Integer.MAX_VALUE;

		while (true) {

			int max = 0;
			for (DivideClass divideClass : classes) {
				int maxContinuousTime = divideClass.getMaxContinuousTime();
				if (maxContinuousTime > max)
					max = maxContinuousTime;
			}

			if (max == 0)
				return prevHour;

			if (max < prevMax)
				prevHour = new int[] { -1, -1, -1, -1, -1 };

			Period[] assigningPeriods = getPeriods(periods, max);

			if (assigningPeriods == null)
				return null;

			if (log)
				for (int i = 0; i < assigningPeriods.length; i++)
					System.out.println(assigningPeriods[i]);

			ArrayList<DivideClass> assigningClasses = new ArrayList<>();
			for (DivideClass divideClass : classes)
				if (divideClass.getMaxContinuousTime() == max
						&& divideClass.canUse(assigningPeriods))
					assigningClasses.add(divideClass);

			if (!assigningClasses.isEmpty()) {

				if (log)
					System.out.println(assigningClasses);

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

				if (log)
					System.out.println(newClasses);

				for (int i = 0; i < assigningPeriods.length; i++)
					assigningPeriods[i].addClasses(newClasses);

				for (DivideClass divideClass : newClasses)
					divideClass.addPeriods(assigningPeriods);
			}

			prevMax = max;

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

	private Period[] getPeriods(Period[][] periods, int time) {
		loop: while (true) {
			int min = prevHour[0];
			int day = 0;
			for (int i = 1; i < prevHour.length; i++) {
				if (min > prevHour[i]) {
					min = prevHour[i];
					day = i;
				}
			}

			if (min == Integer.MAX_VALUE)
				return null;

			if (min + time >= periods[day].length) {
				prevHour[day] = Integer.MAX_VALUE;
				continue;
			}

			Period[] result = new Period[time];
			for (int i = 0; i < time; i++) {
				result[i] = periods[day][min + 1 + i];
				if (i > 0) {
					if (result[i - 1].hour == 1
							|| result[i].hour != result[i - 1].hour + 1) {
						prevHour[day]++;
						continue loop;
					}
				}
			}

			prevHour[day] += time;
			return result;

		}
	}
}
