package com.leovanhaaren.spended.app;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leovanhaaren.spended.app.data.CategoriesRepository;
import com.leovanhaaren.spended.app.data.ExpensesRepository;
import com.leovanhaaren.spended.app.model.Category;
import com.leovanhaaren.spended.app.model.Expense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.segment.android.Analytics;
import io.segment.android.TrackedActivity;
import io.segment.android.models.EventProperties;


public class AddExpenseCategoryActivity extends TrackedActivity {

    private ExpensesRepository   expensesRepository;
    private CategoriesRepository categoriesRepository;
    private CategoryGridAdapter  adapter;
    private ArrayList<Category>  categories;

    private GridView  category;

    private TextView  previewAmount;
    private TextView  previewDate;
    private TextView  previewNote;
    private ImageView previewCategory;

    private Bundle expense_data;
    private Expense expense;

    private int selectedCategory = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.animation_sub_enter, R.anim.animation_main_leave);
        setContentView(R.layout.activity_add_expense_category);

        //get UI elements
        previewAmount   = (TextView)  findViewById(R.id.expense_preview_amount);
        previewDate     = (TextView)  findViewById(R.id.expense_preview_date);
        previewNote     = (TextView)  findViewById(R.id.expense_preview_note);
        previewCategory = (ImageView) findViewById(R.id.expense_preview_category);

        category = (GridView) findViewById(R.id.addExpenseCategory);

        // Display current date
        SimpleDateFormat dateFormat    = new SimpleDateFormat("d.MM.yyyy", new Locale("nl", "NL"));
        DecimalFormat    decimalFormat = new DecimalFormat("0.00");

        Date currentDate = new Date();
        previewDate.setText(dateFormat.format(currentDate));

        // Make preview
        expense_data = getIntent().getExtras();
        if (expense_data != null) {
            Double amount = Double.parseDouble(expense_data.getString("EXPENSE_AMOUNT"));

            previewAmount.setText("€ " + decimalFormat.format(amount));
            previewNote.setText(expense_data.getString("EXPENSE_NOTE"));
            previewCategory.setImageAlpha(50);
        }

        // Ready the repo
        categoriesRepository = new CategoriesRepository(this);
        categories = (ArrayList<Category>) categoriesRepository.getAll();

        // Prepare categories
        adapter = new CategoryGridAdapter(this, categories);
        category.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //gridview.setDrawSelectorOnTop(false);
        category.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedCategory = position;

                // Update preview
                Category category = (Category) adapter.getItem(selectedCategory);

                int resourceId = getResources().getIdentifier(category.getIcon(), "drawable", getPackageName());
                previewCategory.setImageResource(resourceId);
                previewCategory.setImageAlpha(255);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        findViewById(R.id.BtnAddExpenseCancel).setOnClickListener(listener);
        findViewById(R.id.BtnAddExpenseSave).setOnClickListener(listener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Global On click listener for all views
    final OnClickListener listener = new OnClickListener() {
        public void onClick(final View v) {
            switch(v.getId()) {
                case R.id.BtnAddExpenseCancel:
                    onCancel();
                    break;
                case R.id.BtnAddExpenseSave:
                    onSave();
                    break;
            }
        }
    };

    public void onCancel() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void onSave() {
        if(selectedCategory < 0) {
            Toast.makeText(getApplicationContext(), "Please select a category", Toast.LENGTH_SHORT).show();
            category.requestFocus();
            return;
        }

        // Check if we have all bundle data
        if (expense_data == null)  return;

        expense = new Expense(
                Double.parseDouble(expense_data.getString("EXPENSE_AMOUNT")),
                (Category) adapter.getItem(selectedCategory),
                expense_data.getString("EXPENSE_NOTE")
        );

        // Create and add expense to repo
        expensesRepository = new ExpensesRepository(this);
        expensesRepository.create(expense);

        Analytics.track("Added expense", new EventProperties(
                "Amount",   expense.getAmount(),
                "Category", expense.getCategory().getName(),
                "Note",     expense.getNote()));

        Analytics.screen("Added expense screen");

        Toast.makeText(getApplicationContext(), "Added an expense", Toast.LENGTH_LONG).show();

        NavUtils.navigateUpFromSameTask(this);
    }

}
