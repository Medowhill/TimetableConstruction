package pool;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Group {

    public static final int PERIOD_NUM = 5;

    public static final int FIVE = 0, FOUR = 1, THREE = 2, THREE_CONT = 3, TWO = 4, TWO_CONT = 5, ONE = 6, THREE_FULL_CONT = 7;

    private static final int[][] timeComposition = {{0, 1, 2, 3, 4}, {0, 1, 2, 3}, {2, 3, 4}, {0, 1, 2}, {3, 4}, {0, 1}, {4}, {}};

    private static boolean[][] intersectPeriod;

    public final int number;

    private ArrayList<DivideClass> classes;

    private LinkedHashSet<Period> periods;

    public Group(int number) {
        this.number = number;
        this.classes = new ArrayList<>();
        this.periods = new LinkedHashSet<>();
    }

    public static void prepare() {
        intersectPeriod = new boolean[timeComposition.length][timeComposition.length];
        for (int type1 = 0; type1 < timeComposition.length; type1++) {
            for (int type2 = 0; type2 < timeComposition.length; type2++) {
                int[] comp1 = timeComposition[type1];
                int[] comp2 = timeComposition[type2];
                for (int i : comp1) {
                    for (int j : comp2) {
                        if (i == j) {
                            intersectPeriod[type1][type2] = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public static boolean intersectPeriod(int type1, int type2) {
        return intersectPeriod[type1][type2];
    }

    public void addClass(DivideClass divideClass) {
        classes.add(divideClass);
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public boolean canUsePeriod(Period period) {
        int day = period.day;
        for (Period period1 : periods)
            if (period1.day == day)
                return false;
        return true;
    }

    public boolean isComplete() {
        return periods.size() == PERIOD_NUM;
    }

}