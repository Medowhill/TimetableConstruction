package main;

import java.io.PrintWriter;
import java.util.ArrayList;

import pool.DivideClass;
import pool.Graph;
import pool.Period;
import pool.Student;

class ClassManager {

	public static boolean log;
	public static PrintWriter pw;

	private ArrayList<DivideClass> classes;
	private Period[][] periods;

	private ArrayList<Period>[] periodsList;
	private int minPeriod;

	private int[] prevHour;

	ClassManager(ArrayList<DivideClass> classes, Period[][] periods) {
		this.classes = classes;
		this.periods = periods;

		periodsList = new ArrayList[classes.size()];
		for (int i = 0; i < periodsList.length; i++)
			periodsList[i] = new ArrayList<>();
	}

	int assignClasses() {

		minPeriod = Integer.MAX_VALUE;

		assign(Integer.MAX_VALUE, 0);

		if (minPeriod == Integer.MAX_VALUE)
			return -1;

		for (int i = 0; i < classes.size(); i++) {
			ArrayList<Period> pList = classes.get(i).getPeriods();
			pList.clear();
			pList.addAll(periodsList[i]);
		}

		return minPeriod;
	}

	private void assign(int prevMax, int depth) {

		int max = 0;
		for (DivideClass divideClass : classes) {
			int maxContinuousTime = divideClass.getMaxContinuousTime();
			if (maxContinuousTime > max)
				max = maxContinuousTime;
		}

		if (max == 0) {
			int period = 0;
			for (int i = 0; i < prevHour.length; i++)
				period += prevHour[i] + 1;
			if (period < minPeriod) {
				minPeriod = period;
				for (int i = 0; i < classes.size(); i++) {
					periodsList[i].clear();
					periodsList[i].addAll(classes.get(i).getPeriods());
				}
			}
			return;
		}

		if (max < prevMax)
			prevHour = new int[] { -1, -1, -1, -1, -1 };

		Period[] assigningPeriods = getPeriods(periods, max);

		if (assigningPeriods == null)
			return;

		ArrayList<DivideClass> assigningClasses = new ArrayList<>();
		for (DivideClass divideClass : classes)
			if (divideClass.getMaxContinuousTime() == max
					&& divideClass.canUse(assigningPeriods))
				assigningClasses.add(divideClass);

		if (log) {
			for (int i = 0; i < depth; i++)
				pw.print(" ");
			for (int i = 0; i < assigningPeriods.length; i++)
				pw.print(assigningPeriods[i] + " ");
			pw.println();
			// pw.println(assigningClasses);
		}

		if (!assigningClasses.isEmpty()) {

			Graph graph = new Graph(assigningClasses.size());

			for (int i = 0; i < assigningClasses.size(); i++) {
				for (int j = i + 1; j < assigningClasses.size(); j++) {
					if (makeEdge(assigningClasses.get(i),
							assigningClasses.get(j)))
						graph.addEdge(i, j);
				}
			}

			ArrayList<int[]> maxCliques = graph.maxClique(false);

			for (int[] maxClique : maxCliques) {
				ArrayList<DivideClass> newClasses = new ArrayList<>();
				for (int i = 0; i < maxClique.length; i++)
					newClasses.add(assigningClasses.get(maxClique[i]));

				if (false) {
					for (int i = 0; i < depth + 1; i++)
						pw.print(" ");
					pw.println(newClasses);
				}

				for (DivideClass divideClass : newClasses)
					divideClass.addPeriods(assigningPeriods);

				assign(max, depth + 1);

				for (DivideClass divideClass : newClasses)
					divideClass.removePeriods(assigningPeriods);
			}
		} else {
			assign(max, depth + 1);
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
