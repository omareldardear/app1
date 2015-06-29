package com.omar.dardear.dardearycafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Omar on 6/26/2015.
 */
public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<CustomObject> objects;

    private class ViewHolder {
        TextView textView1;
        TextView textView2;
    }

    public CustomAdapter(Context context, ArrayList<CustomObject> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public CustomObject getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.data_view, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.data_Name);
            holder.textView2 = (TextView) convertView.findViewById(R.id.data_Price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView1.setText(objects.get(position).getProp1());
        holder.textView2.setText(objects.get(position).getProp2());
        return convertView;
    }
}

