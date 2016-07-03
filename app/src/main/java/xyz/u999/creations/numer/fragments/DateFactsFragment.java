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
 * Created by umang on 30/6/16.
 */
public class DateFactsFragment extends Fragment {

    CardView dateFactCard;
    TextInputLayout getDate;
    TextInputEditText editTextDate;
    RippleView fetchDateFact;
    AdView bannerAdDate;
    InputMethodManager inputMethodManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_date_facts, container, false);
        ButterKnife.bind(this, rootView);

        YoYo.with(Techniques.FadeInDown).duration(500).playOn(rootView);

        dateFactCard = (CardView) rootView.findViewById(R.id.date_card);
        getDate = (TextInputLayout) rootView.findViewById(R.id.date_input);
        editTextDate = (TextInputEditText) rootView.findViewById(R.id.edit_date);
        fetchDateFact = (RippleView) rootView.findViewById(R.id.date_get_fact);
        bannerAdDate = (AdView) rootView.findViewById(R.id.ad_banner_date);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        final AdRequest bannerAdDateRequest = BannerAd.getBannerAd();

        bannerAdDate.postDelayed(new Runnable() {
            @Override
            public void run() {
                bannerAdDate.loadAd(bannerAdDateRequest);
            }
        }, 300);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFactCard.setVisibility(View.INVISIBLE);
            }
        });

        fetchDateFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String query = String.valueOf(getDate.getEditText().getText());

                if (query.isEmpty())
                    Snackbar.make(rootView, "Please provide a date.", Snackbar.LENGTH_SHORT).show();
                else
                    new DateFactsAsyncTask().execute(query);
            }
        });

        return rootView;
    }


    /**
     * Created by umang on 30/6/16.
     */
    class DateFactsAsyncTask extends AsyncTask<String, Void, String> {

        String dateFactCardTitle;

        @Override
        protected String doInBackground(String... params) {
            dateFactCardTitle = params[0];
            String url = "http://numbersapi.com/" + params[0] + "/date";
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
            dateFactCard.setTitle("Date fact for number " + dateFactCardTitle);
            dateFactCard.setDescription(s);
            dateFactCard.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeInRight).duration(999).playOn(dateFactCard);

        }
    }
}

