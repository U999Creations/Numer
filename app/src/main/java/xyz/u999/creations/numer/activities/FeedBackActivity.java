package xyz.u999.creations.numer.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import xyz.u999.creations.numer.R;

public class FeedBackActivity extends AppCompatActivity {

    ImageView send;
    EditText textbox;
    LinearLayout rate;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        send = (ImageView) findViewById(R.id.feedback_send);
        textbox = (EditText) findViewById(R.id.feedback_et);
        rate = (LinearLayout) findViewById(R.id.feedback_rate);
        toolbar = (Toolbar) findViewById(R.id.feedback_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Feedback");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        send.setVisibility(View.GONE);

        setupTextWatcher();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMarket();
            }
        });

    }

    private void setupTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0)
                    send.setVisibility(View.VISIBLE);
                else
                    send.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        textbox.addTextChangedListener(textWatcher);
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        myAppLinkToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), " unable to rate :(", Toast.LENGTH_LONG).show();
        }
    }

    public void sendEmail() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse(getString(R.string.dev_email)));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.dev_email)});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Suggestions/Complaints.\n" + textbox.getText().toString());
        startActivity(sendIntent);
        textbox.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
