package xyz.u999.creations.numer.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.blunderer.materialdesignlibrary.views.CardView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import butterknife.ButterKnife;
import xyz.u999.creations.numer.BannerAd;
import xyz.u999.creations.numer.R;
import xyz.u999.creations.numer.RippleView;

/**
 * Created by umang on 27/6/16.
 */
public class TriviaFactsFragment extends Fragment {

    CardView triviaFactCard;
    RippleView fetchTriviaFact;
    TextInputLayout getNumber;
    TextInputEditText editTextTrivia;
    AdView bannerAdTrivia;
    InputMethodManager inputMethodManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_trivia_facts, container, false);
        ButterKnife.bind(this, rootView);

        //new BannerAd();

        YoYo.with(Techniques.FadeInDown).duration(500).playOn(rootView);

        triviaFactCard = (CardView) rootView.findViewById(R.id.trivia_card);
        getNumber = (TextInputLayout) rootView.findViewById(R.id.trivia_input);
        editTextTrivia = (TextInputEditText) rootView.findViewById(R.id.edit_trivia);
        fetchTriviaFact = (RippleView) rootView.findViewById(R.id.trivia_get_fact);
        bannerAdTrivia = (AdView) rootView.findViewById(R.id.ad_banner_trivia);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final AdRequest bannerAdDateRequest = BannerAd.getBannerAd();

        bannerAdTrivia.postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAdTrivia.loadAd(bannerAdDateRequest);
            }
        }, 300);

        editTextTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triviaFactCard.setVisibility(View.INVISIBLE);
            }
        });

        fetchTriviaFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String query = String.valueOf(getNumber.getEditText().getText());

                if (query.isEmpty())
                    Snackbar.make(rootView, "Please provide a number.", Snackbar.LENGTH_SHORT).show();
                else
                    new TriviaFactsAsyncTask().execute(query);
            }
        });

        return rootView;
    }

    /**
     * Created by umang on 30/6/16.
     */
    class TriviaFactsAsyncTask extends AsyncTask<String, Void, String> {

        String triviaFactCardTitle = new String();

        @Override
        protected String doInBackground(String... params) {
            triviaFactCardTitle = params[0];
            String url = "http://numbersapi.com/" + params[0] + "/trivia";
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
            triviaFactCard.setTitle("Trivia fact for number " + triviaFactCardTitle);
            triviaFactCard.setDescription(s);
            triviaFactCard.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInRight).duration(999).playOn(triviaFactCard);

        }
    }
}
