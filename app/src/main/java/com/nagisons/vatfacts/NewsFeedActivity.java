package com.nagisons.vatfacts;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nagisons.vatfacts.utils.RssItem;
import com.nagisons.vatfacts.utils.RssReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import customfonts.MyTextView;

public class NewsFeedActivity extends AppCompatActivity {

    private ExpandableHeightListView listview;
    private NewsFeedAdapter newsapadapter;
    private ProgressBar prgBar;


    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsicon};
    private int[] IMAGE2 = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private int[] IMAGE3 = {R.drawable.more, R.drawable.more, R.drawable.more};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_main);

        listview = (ExpandableHeightListView)findViewById(R.id.listview);
        prgBar = (ProgressBar)findViewById(R.id.ctrlActivityIndicator);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    RssReader rssReader = new RssReader("https://news.google.com/news/rss/search/section/q/uae%20vat/uae%20vat?hl=en&ned=us");
                    List<RssItem> RssItems = rssReader.getItems();
                    Collections.sort(RssItems, new Comparator<RssItem>() {
                        public int compare(RssItem o1, RssItem o2) {
                            return o2.getPubDate().compareTo(o1.getPubDate());
                        }
                    });
                    newsapadapter = new NewsFeedAdapter(NewsFeedActivity.this, RssItems) {
                    };
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listview.setAdapter(newsapadapter);
                            prgBar.setVisibility(View.INVISIBLE);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}
