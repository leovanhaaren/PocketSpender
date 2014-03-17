package com.leovanhaaren.spended.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.leovanhaaren.spended.app.model.Category;
import com.leovanhaaren.spended.app.model.Expense;

import java.sql.SQLException;

/**
 * Created by Leo on 14/03/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Expense, Integer> expensesDao = null;
    private RuntimeExceptionDao<Expense, Integer> expensesRuntimeDao = null;

    private Dao<Category, Integer> categoriesDao = null;
    private RuntimeExceptionDao<Category, Integer> categoriesRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Expense.class);
            TableUtils.createTable(connectionSource, Category.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        RuntimeExceptionDao<Expense, Integer>  expensesDao   = getExpensesDataDao();
        RuntimeExceptionDao<Category, Integer> categoriesDao = getCategoriesDataDao();

        Category category1 = new Category("Drink / Food", "drink");
        Category category2 = new Category("Clothing",     "cloth");
        Category category3 = new Category("Bills",        "bills");
        Category category4 = new Category("Friend",       "friend");
        Category category5 = new Category("Luxury",       "luxury");
        Category category6 = new Category("music",        "music");
        Category category7 = new Category("Present",      "present");
        Category category8 = new Category("Software",     "software");
        Category category9 = new Category("Misc",         "misc");
        categoriesDao.create(category1);
        categoriesDao.create(category2);
        categoriesDao.create(category3);
        categoriesDao.create(category4);
        categoriesDao.create(category5);
        categoriesDao.create(category6);
        categoriesDao.create(category7);
        categoriesDao.create(category8);
        categoriesDao.create(category9);

        Expense expense1 = new Expense(5.59,  category1, "6 Pack Hertog Jan");
        Expense expense2 = new Expense(99.95, category2, "Nieuwe glimjack");
        Expense expense3 = new Expense(55.95, category7, "Awakenings ticket");
        expensesDao.create(expense1);
        expensesDao.create(expense2);
        expensesDao.create(expense3);

        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Expense.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Expense, Integer> getExpensesDao() throws SQLException {
        if (expensesDao == null) {
            expensesDao = getDao(Expense.class);
        }
        return expensesDao;
    }

    public RuntimeExceptionDao<Expense, Integer> getExpensesDataDao() {
        if (expensesRuntimeDao == null) {
            expensesRuntimeDao = getRuntimeExceptionDao(Expense.class);
        }
        return expensesRuntimeDao;
    }

    public Dao<Category, Integer> getCategoriesDao() throws SQLException {
        if (categoriesDao == null) {
            categoriesDao = getDao(Category.class);
        }
        return categoriesDao;
    }

    public RuntimeExceptionDao<Category, Integer> getCategoriesDataDao() {
        if (categoriesRuntimeDao == null) {
            categoriesRuntimeDao = getRuntimeExceptionDao(Category.class);
        }
        return categoriesRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        expensesDao = null;
    }

}