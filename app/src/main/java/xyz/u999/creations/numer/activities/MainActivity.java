package xyz.u999.creations.numer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blunderer.materialdesignlibrary.activities.NavigationDrawerActivity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarDefaultHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsMenuHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerBottomHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerStyleHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerTopHandler;
import com.blunderer.materialdesignlibrary.models.Account;
import com.google.android.gms.ads.MobileAds;

import me.drakeet.materialdialog.MaterialDialog;
import xyz.u999.creations.numer.R;
import xyz.u999.creations.numer.fragments.DateFactsFragment;
import xyz.u999.creations.numer.fragments.MathsFactsFragment;
import xyz.u999.creations.numer.fragments.RandomFactsFragment;
import xyz.u999.creations.numer.fragments.TriviaFactsFragment;
import xyz.u999.creations.numer.fragments.YearFactsFragment;

public class MainActivity extends NavigationDrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4812033264637585/6092847952");
    }


    @Override
    protected boolean enableActionBarShadow() {
        return true;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarDefaultHandler(this);
    }

    @Override
    public NavigationDrawerStyleHandler getNavigationDrawerStyleHandler() {
        return null;
    }

    @Override
    public NavigationDrawerAccountsHandler getNavigationDrawerAccountsHandler() {
        return null;
    }

    @Override
    public NavigationDrawerAccountsMenuHandler getNavigationDrawerAccountsMenuHandler() {
        return null;
    }

    @Override
    public void onNavigationDrawerAccountChange(Account account) {

    }

    @Override
    public NavigationDrawerTopHandler getNavigationDrawerTopHandler() {
        return new NavigationDrawerTopHandler(this)
                .addSection(R.string.fact_type)
                .addItem(R.string.math, R.drawable.math, new MathsFactsFragment())
                .addItem(R.string.trivia, R.drawable.trivia, new TriviaFactsFragment())
                .addItem(R.string.date, R.drawable.date, new DateFactsFragment())
                .addItem(R.string.year, R.drawable.year, new YearFactsFragment())
                .addItem(R.string.random, R.drawable.surprise, new RandomFactsFragment());
    }

    @Override
    public NavigationDrawerBottomHandler getNavigationDrawerBottomHandler() {
        return new NavigationDrawerBottomHandler(this)
                .addItem("Share App", R.drawable.share, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String sharingBody = "Get this amazing app named \"Numer\" to know fascinating facts about numbers and be geek!";
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "App to know number facts!");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, sharingBody);
                        startActivity(Intent.createChooser(shareIntent, "Share Via"));
                    }
                })
                .addItem("About", R.drawable.info, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(i);
                    }
                })
                .addHelpAndFeedback(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, FeedBackActivity.class);
                        startActivity(i);
                    }
                });
    }

    @Override
    public boolean overlayActionBar() {
        return false;
    }

    @Override
    public boolean replaceActionBarTitleByNavigationDrawerItemTitle() {
        return true;
    }

    @Override
    public int defaultNavigationDrawerItemSelectedPosition() {
        return 5;
    }

    @Override
    public void onBackPressed() {
        if (isNavigationDrawerOpen())
            closeNavigationDrawer();
        else {
            final MaterialDialog exitConfirm = new MaterialDialog(this);
            exitConfirm
                    .setTitle("Quit App")
                    .setMessage("Do you really want to quit the App?")
                    .setPositiveButton("Yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            exitConfirm.dismiss();
                        }
                    }).show();
        }
    }
}

