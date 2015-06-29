package pool;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Group {

    public final int number;

    private ArrayList<DivideClass> classes;

    private LinkedHashSet<Period> periods;

    public Group(int number) {
        this.number = number;
        this.classes = new ArrayList<>();
        this.periods = new LinkedHashSet<>();
    }

}
