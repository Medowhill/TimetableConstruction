package main;

import pool.DivideClass;
import pool.Group;
import pool.Period;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Jaemin on 2015-07-06.
 */
public class PeriodManager {

    private static final int GROUP_NUM = 8;

    private final boolean LOG;
    private final PrintWriter pw;

    private ArrayList<Period> mPeriods;
    private ArrayList<DivideClass> mClasses;
    private Group[] mGroups;

    private int[] mColors;


    PeriodManager(ArrayList<DivideClass> classes, ArrayList<Period> periods, int[] colors, boolean log, PrintWriter pw) {
        this.mClasses = new ArrayList<>();
        mClasses.addAll(classes);

        this.mPeriods = periods;
        this.mColors = colors;

        this.mGroups = new Group[GROUP_NUM];
        for (int i = 0; i < GROUP_NUM; i++)
            mGroups[i] = new Group(i);

        this.LOG = log;
        this.pw = pw;
    }

    void assignPeriod() {
        for (int i = 0; i < mColors.length; i++) {
            if (mColors[i] <= GROUP_NUM) {
                DivideClass dc = mClasses.get(i);
                Group group = mGroups[mColors[i] - 1];
                dc.setGroup(group);
                group.addClass(dc);
                mClasses.remove(dc);
                i--;
            }
        }

        for (int i = 0; i < mGroups.length; i++) {
            Group group = mGroups[i];
            group.addPeriod(mPeriods.get(i * 2));
            group.addPeriod(mPeriods.get(i * 2 + 1));

            while (!group.isComplete()) {
                for (int j = GROUP_NUM * 2; j < mPeriods.size(); j++) {
                    Period period = mPeriods.get(j);
                    if (group.canUsePeriod(period))
                        group.addPeriod(period);
                }
            }
        }
    }

}
