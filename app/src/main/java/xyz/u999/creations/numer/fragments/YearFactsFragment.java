package xyz.u999.creations.numer.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import xyz.u999.creations.numer.AdBanner;
import xyz.u999.creations.numer.R;
import xyz.u999.creations.numer.RippleView;

/**
 * Created by umang on 27/6/16.
 */
public class YearFactsFragment extends Fragment {

    CardView yearFactCard;
    RippleView fetchYearFact;
    TextInputLayout getYear;
    TextInputEditText editTextYear;
    AdView bannerAdYear;
    InputMethodManager inputMethodManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_year_facts, container, false);
        ButterKnife.bind(this, rootView);

        YoYo.with(Techniques.FadeInDown).duration(500).playOn(rootView);

        yearFactCard = (CardView) rootView.findViewById(R.id.year_card);
        getYear = (TextInputLayout) rootView.findViewById(R.id.year_input);
        editTextYear = (TextInputEditText) rootView.findViewById(R.id.edit_year);
        fetchYearFact = (RippleView) rootView.findViewById(R.id.year_get_fact);
        bannerAdYear = (AdView) rootView.findViewById(R.id.ad_banner_year);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final AdRequest bannerAdDateRequest = AdBanner.getBannerAd();

        bannerAdYear.postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAdYear.loadAd(bannerAdDateRequest);
            }
        }, 300);

        editTextYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearFactCard.setVisibility(View.INVISIBLE);
            }
        });

        fetchYearFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String query = String.valueOf(getYear.getEditText().getText());
                new YearFactsAsyncTask().execute(query);
            }
        });

        return rootView;
    }


    /**
     * Created by umang on 30/6/16.
     */
    class YearFactsAsyncTask extends AsyncTask<String, Void, String> {

        String yearFactCardTitle = new String();

        @Override
        protected String doInBackground(String... params) {
            yearFactCardTitle = params[0];
            String url = "http://numbersapi.com/" + params[0] + "/year";
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
            yearFactCard.setTitle("Year fact for number " + yearFactCardTitle);
            yearFactCard.setDescription(s);
//            pacmanIndicatorYear.setVisibility(View.INVISIBLE);
            yearFactCard.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInRight).duration(999).playOn(yearFactCard);

        }
    }
}
