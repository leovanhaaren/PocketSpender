package com.leovanhaaren.spended.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leovanhaaren.spended.app.model.Category;

import java.util.ArrayList;

/**
 * Created by Leo on 14/03/14.
 */
public class CategoryGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categories;
    private LayoutInflater inflater;

    public CategoryGridAdapter(Context c, ArrayList<Category> cat) {
        inflater = LayoutInflater.from(c);
        context    = c;
        categories = cat;
    }

    public int getCount() {
        return categories.size();
    }

    public Object getItem(int position) {
        return categories.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(v == null) {
            v = inflater.inflate(R.layout.category_grid_item, parent, false);
            v.setTag(R.id.category_grid_item_image, v.findViewById(R.id.category_grid_item_image));
            v.setTag(R.id.category_grid_item_name,  v.findViewById(R.id.category_grid_item_name));
        }

        ImageView image = (ImageView) v.getTag(R.id.category_grid_item_image);
        TextView  name  = (TextView)  v.getTag(R.id.category_grid_item_name);

        Category category = (Category) getItem(position);

        name.setText(category.getName());

        // Set image
        int resourceId = context.getResources().getIdentifier(category.getIcon(), "drawable", context.getPackageName());
        image.setImageResource(resourceId);

        return v;
    }

}