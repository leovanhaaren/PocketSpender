package com.leovanhaaren.spended.app.data;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.leovanhaaren.spended.app.model.Expense;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Leo on 14/03/14.
 */
public class ExpensesRepository {

    private DatabaseHelper db;
    Dao<Expense, Integer> expensesDao;

    public ExpensesRepository(Context ctx) {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(ctx);
            expensesDao = db.getExpensesDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int create(Expense expense) {
        try {
            return expensesDao.create(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Expense expense) {
        try {
            return expensesDao.update(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Expense expense)
    {
        try {
            return expensesDao.delete(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Expense> getAll()
    {
        try {
            return expensesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}