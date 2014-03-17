package com.leovanhaaren.spended.app.data;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.leovanhaaren.spended.app.model.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Leo on 14/03/14.
 */
public class CategoriesRepository {

    private DatabaseHelper db;
    Dao<Category, Integer> categoriesDao;

    public CategoriesRepository(Context ctx) {
        try {
            DatabaseManager dbManager = new DatabaseManager();
            db = dbManager.getHelper(ctx);
            categoriesDao = db.getCategoriesDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int create(Category category) {
        try {
            return categoriesDao.create(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Category category) {
        try {
            return categoriesDao.update(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Category category)
    {
        try {
            return categoriesDao.delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Category> getAll()
    {
        try {
            return categoriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}