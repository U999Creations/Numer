package com.u999.creations.numers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class Numers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numers);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);

        PrimaryDrawerItem fact = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.math);
        PrimaryDrawerItem math = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.math);
        PrimaryDrawerItem trivia = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.trivia);
        PrimaryDrawerItem year = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.year);
        PrimaryDrawerItem random = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.random);

        SecondaryDrawerItem about = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(6).withName(R.string.about);

        Drawer drawer=new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(fact,math,trivia,year,random,new DividerDrawerItem(),about)
                .build();


    }
}