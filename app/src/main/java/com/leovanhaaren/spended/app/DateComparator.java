package com.leovanhaaren.spended.app;

import com.leovanhaaren.spended.app.model.Expense;

import java.util.Comparator;

/**
 * Created by Leo on 14/03/14.
 */
public class DateComparator implements Comparator<Expense> {
    public int compare(Expense o1, Expense o2) {
        return o2.getDate().compareTo(o1.getDate());
    }
}
