package com.u999.creations.numers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.u999.creations.numers.R;
import com.u999.creations.numers.fragments.FactsFragment;
import com.u999.creations.numers.fragments.MathsFactsFragment;
import com.u999.creations.numers.fragments.RandomFactsFragment;
import com.u999.creations.numers.fragments.TriviaFactsFragment;
import com.u999.creations.numers.fragments.YearFactsFragment;
import com.u999.creations.numers.utils.Constants;

import butterknife.ButterKnife;

public class Numers extends AppCompatActivity {

    private Toolbar toolbar;
    private PrimaryDrawerItem fact, math, trivia, year, random;
    private SecondaryDrawerItem about;
    private Drawer drawer;
    private AccountHeader header;
    private IDrawerItem selectedDrawerItem;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numers);
        ButterKnife.bind(this);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setToolbar();
        setDrawer();

        selectedDrawerItem = fact;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedDrawerItem != null) {
            setToolbarTitle(selectedDrawerItem);
        }
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_red_900));
        setSupportActionBar(toolbar);
    }

    private void setDrawer() {

        fact = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.fact);
        math = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.math);
        trivia = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.trivia);
        year = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.year);
        random = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.random);

        about = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(6).withName(R.string.about);

        header = new AccountHeaderBuilder()
                .withActivity(this)
                //.withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName("Numers"))
                .build();


        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withCloseOnClick(true)
                .addDrawerItems(fact, math, trivia, year, random, new DividerDrawerItem(), about)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Fragment fragment = null;
                        String fragmentTag = "";
                        drawer.closeDrawer();
                        if (drawerItem == null)
                            drawerItem = fact;

                        setToolbarTitle(drawerItem);

                        if (drawerItem.equals(fact)) {
                            selectedDrawerItem = drawerItem;
                            fragment = new FactsFragment();
                            Snackbar.make(frameLayout, getString(R.string.fact), Snackbar.LENGTH_SHORT).show();
                            fragmentTag = Constants.FRAGMENT_TAG_FACTS;
                        } else if (drawerItem.equals(math)) {
                            selectedDrawerItem = drawerItem;
                            fragment = new MathsFactsFragment();
                            Snackbar.make(frameLayout, getString(R.string.math), Snackbar.LENGTH_SHORT).show();
                            fragmentTag = Constants.FRAGMENT_TAG_MATHS;
                        } else if (drawerItem.equals(trivia)) {
                            selectedDrawerItem = drawerItem;
                            fragment = new TriviaFactsFragment();
                            Snackbar.make(frameLayout, getString(R.string.trivia), Snackbar.LENGTH_SHORT).show();
                            fragmentTag = Constants.FRAGMENT_TAG_TRIVIA;
                        } else if (drawerItem.equals(year)) {
                            selectedDrawerItem = drawerItem;
                            fragment = new YearFactsFragment();
                            Snackbar.make(frameLayout, getString(R.string.year), Snackbar.LENGTH_SHORT).show();
                            fragmentTag = Constants.FRAGMENT_TAG_YEAR;
                        } else if (drawerItem.equals(random)) {
                            selectedDrawerItem = drawerItem;
                            fragment = new RandomFactsFragment();
                            Snackbar.make(frameLayout, getString(R.string.random), Snackbar.LENGTH_SHORT).show();
                            fragmentTag = Constants.FRAGMENT_TAG_RANDOM;
                        } else if (drawerItem.equals(about)) {
                            selectedDrawerItem = drawerItem;
                            Intent intent = new Intent(Numers.this, AboutDev.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            return true;
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.main_frame, fragment, fragmentTag).commit();
                            fragmentManager.popBackStack();
                        }

                        return false;
                    }
                })
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void setToolbarTitle(IDrawerItem drawerItem) {
        String toolbarTitle = "";

        int identifier = (int) drawerItem.getIdentifier();

        switch (identifier) {

            case 1:
                toolbarTitle = getString(R.string.fact);
                break;
            case 2:
                toolbarTitle = getString(R.string.math);
                break;
            case 3:
                toolbarTitle = getString(R.string.trivia);
                break;
            case 4:
                toolbarTitle = getString(R.string.year);
                break;
            case 5:
                toolbarTitle = getString(R.string.random);
                break;

        }

        getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}