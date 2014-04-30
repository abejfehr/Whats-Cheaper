package com.abefehr.whatscheaper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by abe on 1/2/2014.
 */
public class CrazyAdapter extends ArrayAdapter<Item> {
    Context context;
    List<Item> data = null;

    public CrazyAdapter(Context context, List<Item> data) {
        super(context, R.layout.list_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemHolder holder;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);

            holder = new ItemHolder();
            holder.title = (TextView)row.findViewById(R.id.list_title);
            holder.subtitle = (TextView)row.findViewById(R.id.list_subtitle);

            row.setTag(holder);
        }
        else
            holder = (ItemHolder)row.getTag();

        Item item = data.get(position);
        holder.title.setText(formatAsNumber(item.getNumUnits()) + " " + context.getResources().getString(R.string.for_string) + " " + formatAsMoney(item.getPrice()));
        holder.subtitle.setText( formatAsMoney(item.getPrice() / item.getNumUnits()) + " each");

        if(data.size() > 1 && position == 0)
        {
            holder.title.setTextColor(Color.parseColor("#00FF00"));
            holder.subtitle.setTextColor(Color.parseColor("#00FF00"));
        }

        return row;
    }

    static class ItemHolder {
        TextView title, subtitle;
    }

    @Override
    public void add(Item item) {
        super.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        Collections.sort(data);
        super.notifyDataSetChanged();
    }

    private String formatAsMoney(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(amount);

        if (moneyString.endsWith(".00")) {
            int centsIndex = moneyString.lastIndexOf(".00");
            if (centsIndex != -1)
                moneyString = moneyString.substring(0, centsIndex);
        }
        return moneyString;
    }

    private String formatAsNumber(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }
}
