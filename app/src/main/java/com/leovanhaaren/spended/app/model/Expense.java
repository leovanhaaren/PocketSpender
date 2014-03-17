package com.leovanhaaren.spended.app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Leo on 14/03/14.
 */
@DatabaseTable(tableName = "expenses")
public class Expense {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private double amount;

    @DatabaseField
    private Date date;

    @DatabaseField
    private String note;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Category category;

    public Expense() {
        // ORMLite needs a no-arg constructor
    }

    public Expense(double amount, Category category, String note){
        this.amount   = amount;
        this.date     = new Date();
        this.category = category;
        this.note     = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}