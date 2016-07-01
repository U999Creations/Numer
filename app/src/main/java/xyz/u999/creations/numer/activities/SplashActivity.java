package xyz.u999.creations.numer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import butterknife.BindView;
import xyz.u999.creations.numer.R;

public class SplashActivity extends Activity {

//    @BindView(R.id.quote_show)
//    TextView quoteShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_connected);

        //final String temp = getRandomQuote();


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //              quoteShow.setText(temp);
                        if (isConnected()) {
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashActivity.this, SplashNotConnected.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, 333);
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

   /* private String getRandomQuote() {

        String[] quotes = {
                "Pure Mathematics is , in its way,the poetry of logical ideas. -Albert Einstein",
                "Math is Radical. -Bumper Sticker",
                "Decimals have a point.",
                "Natural numbers are better for your health.(mental health to be specific!)",
                "Without geometry life is pointless.",
                "Perfect numbers like perfect men are very rare. -Rene Descartes",
                "Where there is matter, there is geometry. -Johannes Kepler",
                "It is not certain that everything is uncertain. -Blaise Pascal",
                "0, 1, 1, 2, 3, 4, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987,.......Fibbonaci Series",
                "0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55,..... Triangular Number Series",
                "1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862,.....Catalan Number Series",
                "1, 11, 21, 1211, 111221, 312211, 13112221, 1113213211,......Look and Say sequence",
        };

        int rand = (int) Math.random() * 11;

        return quotes[rand];
    }*/

}
