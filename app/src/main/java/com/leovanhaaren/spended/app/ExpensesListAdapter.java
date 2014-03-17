package com.leovanhaaren.spended.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leovanhaaren.spended.app.model.Expense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Leo on 14/03/14.
 */
public class ExpensesListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Expense> expenses;
    private LayoutInflater inflater;

    public ExpensesListAdapter(Context c, ArrayList<Expense> e) {
        inflater = LayoutInflater.from(c);
        context  = c;
        expenses = e;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            v = inflater.inflate(R.layout.expense_list_item, parent, false);
            v.setTag(R.id.expense_list_item_amount,   v.findViewById(R.id.expense_list_item_amount));
            v.setTag(R.id.expense_list_item_date,     v.findViewById(R.id.expense_list_item_date));
            v.setTag(R.id.expense_list_item_category, v.findViewById(R.id.expense_list_item_category));
            v.setTag(R.id.expense_list_item_note,     v.findViewById(R.id.expense_list_item_note));
        }

        TextView  amount = (TextView) v.getTag(R.id.expense_list_item_amount);
        TextView  date   = (TextView) v.getTag(R.id.expense_list_item_date);
        ImageView image  = (ImageView)v.getTag(R.id.expense_list_item_category);
        TextView  note   = (TextView) v.getTag(R.id.expense_list_item_note);

        Expense item = (Expense) getItem(position);

        SimpleDateFormat dateFormat    = new SimpleDateFormat("d.MM.yyyy", new Locale("nl", "NL"));
        DecimalFormat    decimalFormat = new DecimalFormat("0.00");

        amount.setText("€ " + decimalFormat.format(item.getAmount()));
        date.setText(dateFormat.format(item.getDate()));
        note.setText(item.getNote());

        // Set image
        int resourceId = context.getResources().getIdentifier(item.getCategory().getIcon(), "drawable", context.getPackageName());
        image.setImageResource(resourceId);

        return v;
    }

}