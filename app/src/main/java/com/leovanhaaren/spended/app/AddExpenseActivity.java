package com.leovanhaaren.spended.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.segment.android.TrackedActivity;


public class AddExpenseActivity extends TrackedActivity {

    private int selectedCategory = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.animation_sub_enter, R.anim.animation_main_leave);
        setContentView(R.layout.activity_add_expense);

        //get UI elements
        TextView date = (TextView) findViewById(R.id.addExpenseDate);

        // Display current date
        SimpleDateFormat formatter = new SimpleDateFormat("d.MM.yyyy", new Locale("nl", "NL"));
        Date currentDate = new Date();
        date.setText(formatter.format(currentDate));
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
                    onNext();
                    break;
            }
        }
    };

    public void onCancel() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void onNext() {
        EditText amount   = (EditText) findViewById(R.id.addExpenseAmount);
        EditText note     = (EditText) findViewById(R.id.addExpenseNote);

        if(amount.length() <= 0) {
            Toast.makeText(getApplicationContext(), "Please fill in a amount", Toast.LENGTH_SHORT).show();
            amount.requestFocus();
            return;
        }

        Intent intent = new Intent(getBaseContext(), AddExpenseCategoryActivity.class);

        intent.putExtra("EXPENSE_AMOUNT", amount.getText().toString());
        intent.putExtra("EXPENSE_NOTE", note.getText().toString());

        startActivity(intent);
    }

}
