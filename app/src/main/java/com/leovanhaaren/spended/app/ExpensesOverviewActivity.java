package com.leovanhaaren.spended.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leovanhaaren.spended.app.data.ExpensesRepository;
import com.leovanhaaren.spended.app.listener.SwipeDismissListViewTouchListener;
import com.leovanhaaren.spended.app.model.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import io.segment.android.TrackedActivity;


public class ExpensesOverviewActivity extends TrackedActivity {

    private ExpensesRepository  repository;
    private ExpensesListAdapter adapter;
    private ArrayList<Expense>  expenses;

    private DrawerLayout drawerlayout;
    private ListView     drawerList;

    private ListView listview;
    private TextView date;
    private TextView totalExpenses;

    private Date currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.animation_main_enter, R.anim.animation_sub_leave);
        setContentView(R.layout.activity_expenses_overview);

        // Init navigation drawer
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList   = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        //drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        //drawerList.setOnItemClickListener(new DrawerItemClickListener());

        //get UI elements
        listview      = (ListView) this.findViewById(R.id.expensesListView);

        // Set current date
        currentDate = new Date();

        // Add fancy header to list
        View header = View.inflate(this, R.layout.expense_list_header, null);
        listview.addHeaderView(header);

        // Get header UI elements
        date          = (TextView) this.findViewById(R.id.totalExpensesDate);
        totalExpenses = (TextView) this.findViewById(R.id.totalExpensesAmount);

        // Ready the repo
        repository = new ExpensesRepository(this);
        expenses = (ArrayList<Expense>) repository.getAll();

        // Sort our data
        Collections.sort(expenses, new DateComparator());

        // Set our custom list adapter
        adapter = new ExpensesListAdapter(this, expenses);
        listview.setAdapter(adapter);

        // Update header
        updateHeader();

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listview,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    Expense expense = (Expense) adapter.getItem(position - 1);
                                    expenses.remove(expense);
                                    repository.delete(expense);
                                }
                                adapter.notifyDataSetChanged();
                                updateHeader();

                                Toast.makeText(getApplicationContext(), "Removed expense", Toast.LENGTH_SHORT).show();
                            }
                        });
        listview.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listview.setOnScrollListener(touchListener.makeScrollListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expenses_overview, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            startActivity(new Intent(getApplicationContext(), AddExpenseActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateHeader() {
        // Update date
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", new Locale("nl", "NL"));
        date.setText(formatter.format(currentDate));

        // Update month total
        double total = 0.00;
        for(Expense expense : expenses) {
            total += expense.getAmount();
        }
        totalExpenses.setText("€ " + roundExpense(total));
    }

    public static double roundExpense(double value) {
        return (double) Math.round(value * 100) / 100;
    }

}
