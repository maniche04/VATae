package com.nagisons.vatfacts;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.List;

import customfonts.MyTextView;

public class MOAFeedActivity extends AppCompatActivity {

    private ExpandableHeightListView listview;
    private ArrayList<Bean> Bean;
    private MOAFeedAdapter baseAdapter;
    private ProgressBar prgBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("moafeeds");

    private TextWatcher mSearchTw;


    private int[] IMAGE1 = {R.drawable.newsname1, R.drawable.newsname1, R.drawable.newsname1};
    private int[] IMAGE2 = {R.drawable.img1, R.drawable.img1, R.drawable.img1};
    private int[] IMAGE3 = {R.drawable.more, R.drawable.more, R.drawable.more};
    private String[] NEWSNAME = {"कान्तिपुर", "Fox News .", "Fox News ."};
    private String[] TITLE = {"1 day ago", "1 day ago", "1 day ago"};
    private String[] NEWS = {"सूचना तथा सञ्चारमन्त्री डा मीनेन्द्र रिजालले सिमांकनसहितको संविधान जारी गर्नुपर्ने बताएका छन् ।",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous",
            "Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous. Trump’s Plan for AmericanMade iPhonew Wold Be Disastrous"};
    private String[] NEWSSUB = {"Why even a President Trump couldn’t make Apple manufacture iPhone in the state.","Why even a President Trump couldn’t make Apple manufacture iPhone in the state.",
            "Why even a President Trump couldn’t make Apple manufacture iPhone in the state."};
    private String[] INTREST = {"You've shown interest in iPhone","You've shown interest in iPhone","You've shown interest in iPhone"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ExpandableHeightListView)findViewById(R.id.listview);

        Bean = new ArrayList<Bean>();

        for (int i= 0; i< IMAGE1.length; i++){

            Bean bean = new Bean(IMAGE1[i], IMAGE2[i], IMAGE3[i], NEWSNAME[i], TITLE[i], NEWS[i], NEWSSUB[i], INTREST[i]);
            Bean.add(bean);

        }

//        baseAdapter = new MOAFeedAdapter(MOAFeedActivity.this, Bean) {
//        };
        prgBar = (ProgressBar)findViewById(R.id.ctrlActivityIndicator);


        // Capture Text in EditText
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                GenericTypeIndicator<List<MOAFeed>> genericTypeIndicator =new GenericTypeIndicator<List<MOAFeed>>(){};
//                List<MOAFeed> feedlist = dataSnapshot.getValue(genericTypeIndicator);

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        List<MOAFeed> list = new ArrayList<MOAFeed>();
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            list.add(child.getValue(MOAFeed.class));
                        }
                        //                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        //                    // TODO: handle the post
                        //                    MOAFeed value = postSnapshot.getValue(MOAFeed.class);
                        //                    System.out.println("-------------");
                        //                    System.out.println(value.title);
                        //                    System.out.println("-------------");
                        //                }
                        baseAdapter = new MOAFeedAdapter(MOAFeedActivity.this, list) {
                        };

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listview.setAdapter(baseAdapter);
                                prgBar.setVisibility(View.INVISIBLE);
                                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        MOAFeed feed = (MOAFeed) baseAdapter.getItem(position);
                                        final Dialog dialog = new Dialog(MOAFeedActivity.this);

                                        dialog.setContentView(R.layout.moafaqsingle);
                                        dialog.setTitle("Custom Alert Dialog");

                                        final MyTextView title = (MyTextView) dialog.findViewById(R.id.faq_title);
                                        final MyTextView desc = (MyTextView) dialog.findViewById(R.id.faq_detail);
                                        title.setText(feed.getTitle());
                                        desc.setText(feed.getData());
                                        dialog.show();
                                    }
                                });

                            }
                        });
                    }
                };

                thread.start();



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }
}
