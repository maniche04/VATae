package com.nagisons.vatfacts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagisons.vatfacts.utils.RssItem;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by apple on 01/04/16.
 */
public class NewsFeedAdapter extends BaseAdapter {


    Context context;

    List<RssItem> news;
    Typeface fonts1,fonts2;


    public NewsFeedAdapter(Context context, List<RssItem> news) {
        fonts1 =  Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Light.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/Lato-Regular.ttf");
        this.context = context;
        this.news = news;
    }


    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
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
            convertView = layoutInflater.inflate(R.layout.newsfeed,null);

            viewHolder = new ViewHolder();

            viewHolder.newsimage1 = (ImageView)convertView.findViewById(R.id.newsimage1);
//            viewHolder.newsimage2 = (ImageView)convertView.findViewById(R.id.newsimage2);
            viewHolder.more = (ImageView)convertView.findViewById(R.id.more);
            viewHolder.newsname = (TextView)convertView.findViewById(R.id.newsname);
            viewHolder.time = (TextView)convertView.findViewById(R.id.time);
            viewHolder.news = (TextView)convertView.findViewById(R.id.news);

            viewHolder.newsname.setTypeface(fonts1);
            viewHolder.time.setTypeface(fonts1);
            viewHolder.news.setTypeface(fonts2);

            convertView.setTag(viewHolder);


        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }


        RssItem bean = (RssItem)getItem(position);

//        viewHolder.newsimage1.setImageResource(R.drawable.newsname1);
//        Picasso.with(context).load(bean.getImageUrl()).into(viewHolder.newsimage2);
//        System.out.println("Image is " + bean.getImageUrl());
//        viewHolder.newsimage2.setImageResource(R.drawable.img1);
        viewHolder.more.setImageResource(R.drawable.more);
        String timeago = parseDate(bean.getPubDate());
        System.out.println("TIME OFFSET: " + timeago);
        viewHolder.newsname.setText(getUrlDomainName(bean.getLink()) + timeago);
        viewHolder.time.setText(timeago);
        viewHolder.news.setText(bean.getTitle() );


        return convertView;
    }

    public String parseDate(Date date) {
        String timeOfDay = new SimpleDateFormat("HH:mm").format(date);
        Timestamp timeStampDate = new Timestamp(date.getTime());
        Timestamp timeStampNow = new Timestamp((new Date()).getTime());

        long secondDiff = timeStampNow.getTime() / 1000 - timeStampDate.getTime() / 1000;
        int minuteDiff = (int) (secondDiff / 60);
        int hourDiff = (int) (secondDiff / 3600);
        int dayDiff = daysBetween(date, new Date()) - 1;
        if (dayDiff > 0) {
            return dayDiff + " days ago";
//                @ " + timeOfDay;
        } else if (hourDiff > 0) {
            return hourDiff + " hour(s) ago";
//                " @ " + timeOfDay;
        } else if (minuteDiff > 0) {
            return minuteDiff + " minute(s) ago";
//                " @ " + timeOfDay;
        } else if (secondDiff > 0) {
            return secondDiff + " second(s) ago";
//                " @ " + timeOfDay;
        }
        return "";
    }


    private int daysBetween(Date startDate, Date endDate) {
        int daysBetween = 0;
        while (startDate.before(endDate)) {
            startDate.setTime(startDate.getTime() + 86400000);
            daysBetween++;
        }
        return daysBetween;
    }

    /**
     * Extracts the domain name from {@code url}
     * by means of String manipulation
     * rather than using the {@link } or {@link URL} class.
     *
     * @param url is non-null.
     * @return the domain name within {@code url}.
     */
    public String getUrlDomainName(String url) {
        String domainName = new String(url);

        int index = domainName.indexOf("://");

        if (index != -1) {
            // keep everything after the "://"
            domainName = domainName.substring(index + 3);
        }

        index = domainName.indexOf('/');

        if (index != -1) {
            // keep everything before the '/'
            domainName = domainName.substring(0, index);
        }

        // check for and remove a preceding 'www'
        // followed by any sequence of characters (non-greedy)
        // followed by a '.'
        // from the beginning of the string
        domainName = domainName.replaceFirst("^www.*?\\.", "");

        return domainName;
    }

    private class ViewHolder{

        ImageView newsimage1;
        ImageView  more;
        TextView newsname;
        TextView time;
        TextView news;

    }
}

