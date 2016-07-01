package xyz.u999.creations.numer.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
public class YearFactsFragment extends Fragment {

    CardView yearFactCard;
    RippleView fetchYearFact;
    TextInputLayout getYear;
//    AVLoadingIndicatorView pacmanIndicatorYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_year_facts, container, false);
        ButterKnife.bind(this, rootView);

        YoYo.with(Techniques.FadeInDown).duration(500).playOn(rootView);

        yearFactCard = (CardView) rootView.findViewById(R.id.year_card);
        getYear = (TextInputLayout) rootView.findViewById(R.id.year_input);
        fetchYearFact = (RippleView) rootView.findViewById(R.id.year_get_fact);
//        pacmanIndicatorYear= (AVLoadingIndicatorView) rootView.findViewById(R.id.pacman_year);

        fetchYearFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                yearFactCard.setVisibility(View.GONE);
//                pacmanIndicatorYear.setVisibility(View.VISIBLE);
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
