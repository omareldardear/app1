package com.omar.dardear.dardearycafe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Omar on 6/24/2015.
 */
public class SecondMenuListAdapter extends CursorAdapter
{


    public SecondMenuListAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }



    public static class ViewHolder {
        public final TextView Name;
        public final TextView Price;


        public ViewHolder(View view) {

            Name = (TextView) view.findViewById(R.id.data_Name);
            Price = (TextView) view.findViewById(R.id.data_Price);

        }
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {



        View view = LayoutInflater.from(context).inflate(R.layout.data_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }





    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String Name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
        float Price = cursor.getFloat(cursor.getColumnIndexOrThrow("Price"));


        viewHolder.Name.setText(Name);
        viewHolder.Price.setText(String.valueOf(Price));


    }




}
