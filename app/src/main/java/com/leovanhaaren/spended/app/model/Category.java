package com.leovanhaaren.spended.app.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Leo on 14/03/14.
 */
@DatabaseTable(tableName = "categories")
public class Category {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String icon;

    @ForeignCollectionField
    private ForeignCollection<Expense> expenses;

    public Category() {
        // ORMLite needs a no-arg constructor
    }

    public Category(String name, String icon){
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ForeignCollection<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ForeignCollection<Expense> expenses) {
        this.expenses = expenses;
    }

}