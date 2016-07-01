package xyz.u999.creations.numer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blunderer.materialdesignlibrary.views.CardView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.wang.avi.AVLoadingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import butterknife.ButterKnife;
import xyz.u999.creations.numer.R;
import xyz.u999.creations.numer.RippleView;

/**
 * Created by umang on 27/6/16.
 */
public class RandomFactsFragment extends Fragment {

    AVLoadingIndicatorView pacmanIndicator;
    CardView randomFactCard;
    RippleView fetchRandomFact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_random_facts, container, false);
        ButterKnife.bind(this, rootView);

        YoYo.with(Techniques.RollIn).duration(250).playOn(rootView);

        randomFactCard = (CardView) rootView.findViewById(R.id.random_card);
        fetchRandomFact = (RippleView) rootView.findViewById(R.id.random_get_fact);
        pacmanIndicator= (AVLoadingIndicatorView) rootView.findViewById(R.id.pacman);

        new RandomFactsAsyncTask().execute();

        fetchRandomFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomFactCard.setVisibility(View.GONE);
                pacmanIndicator.setVisibility(View.VISIBLE);
                new RandomFactsAsyncTask().execute();
            }
        });

        return rootView;
    }

    /**
     * Created by umang on 30/6/16.
     */
    class RandomFactsAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String[] factType = {"trivia", "year", "date", "math"};
            int rand = (int) Math.random() * 4;

            String url = "http://numbersapi.com/random/" + factType[rand];
            String responseString = new String();
            try {
                Document doc = Jsoup.connect(url).get();
                Element body = doc.select("body").first();
                responseString = body.text();
                Log.wtf("wtf", responseString);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            randomFactCard.setTitle("Math.random() !");
            randomFactCard.setDescription(s);
            pacmanIndicator.setVisibility(View.INVISIBLE);
            randomFactCard.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.ZoomIn).duration(333).playOn(randomFactCard);

        }
    }
}
