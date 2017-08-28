package com.nagisons.vatfacts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 01/04/16.
 */
public class MOAFeedAdapter extends BaseAdapter {


    Context context;
    private List<MOAFeed> arraylist;
    List<MOAFeed>bean;
    Typeface fonts1,fonts2;


    public MOAFeedAdapter(Context context, List<MOAFeed> bean) {
        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Light.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Regular.ttf");

        this.context = context;
        this.bean = bean;
        this.arraylist = new ArrayList<MOAFeed>();
        this.arraylist.addAll(bean);
    }


    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.moafeedlist,null);

            viewHolder = new ViewHolder();

            viewHolder.newsname = (TextView)convertView.findViewById(R.id.newsname);
            viewHolder.news = (TextView)convertView.findViewById(R.id.news);
            viewHolder.newflag = (TextView)convertView.findViewById(R.id.newflag);



            viewHolder.newsname.setTypeface(fonts1);
            viewHolder.news.setTypeface(fonts2);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        MOAFeed bean = (MOAFeed)getItem(position);

        viewHolder.newsname.setText("MOF");
        viewHolder.news.setText(bean.getTitle());

        if (bean.isnew == 1) {
            viewHolder.newflag.setText("*NEW*");
        } else {
            viewHolder.newflag.setText("");
        }

        return convertView;
    }


    private class ViewHolder{
        TextView newsname;
        TextView news;
        TextView newflag;
    }
}

