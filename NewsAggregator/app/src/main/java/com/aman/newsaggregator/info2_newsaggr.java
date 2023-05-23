package com.aman.newsaggregator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aman.newsaggregator.frontnews.news_aggr;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
//Done
public class info2_newsaggr extends Fragment implements View.OnClickListener{
    ImageView imagenews;
    private news_aggr news_list;

    public static final info2_newsaggr newInstance(news_aggr news, String pos, String sum) {

        info2_newsaggr details = new info2_newsaggr();
        Bundle detail_bundle = new Bundle(1);
        detail_bundle.putSerializable(getNews_frag_data(), news);
        detail_bundle.putString(getPos_data(), pos);
        detail_bundle.putString(getNews_total(), sum);
        details.setArguments(detail_bundle);
        return details;
    }


    public static final String news_total = "DATA_TOTAL";
    @NonNull
    private static String getNews_total() {

        return news_total;
    }
    public static final String news_frag_data = "DATA_ARTICLE_FRAG";
    @NonNull
    private static String getNews_frag_data() {

        return news_frag_data;
    }
    public static final String pos_data = "DATA_INDICE";
    @NonNull
    private static String getPos_data() {

        return pos_data;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup viewgroup, Bundle bundle) {
        View view = inflater.inflate(R.layout.news_page, viewgroup, false);
        String news_total = getArguments().getString(info2_newsaggr.news_total);
        news_list = (news_aggr) getArguments().getSerializable(news_frag_data);
        String pos = getArguments().getString(pos_data);

        TextView tvtimestamp = view.findViewById(R.id.news_time);
        TextView tvdescription = view.findViewById(R.id.article_preview);
        TextView tvhead = view.findViewById(R.id.news_title);
        TextView tvwriter = view.findViewById(R.id.news_author);
        TextView tvnumber = view.findViewById(R.id.article_count);
        imagenews = view.findViewById(R.id.news_image);

        tvdescription.setOnClickListener(this);
        tvhead.setOnClickListener(this);
        imagenews.setOnClickListener(this);

        tvdescription.setText(news_list.getNewsdata());
        tvhead.setText(news_list.getNewstitle());
        tvwriter.setText(news_list.getNewsWriter());
        tvnumber.setText(Integer.parseInt(pos) + 1 + " of " + news_total);

        if (news_list.getNewstime() != null) {
            DateFormat dateF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateF.setLenient(false);
            DateFormat reddate = new SimpleDateFormat("MMM dd, yyyy hh:mmaa");
            reddate.setLenient(false);
            String reformatted = news_list.getNewstime();
            Date updateddate;
            int trial = 0;
            int max_tries = 2;
            boolean flag = false;
            while (!flag) {
                try {
                    updateddate = dateF.parse(reformatted);
                    flag = true;
                    tvtimestamp.setText(reddate.format(updateddate));
                } catch (Exception e) {
                    dateF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
                    if (++trial == max_tries) {
                        flag = true;
                        tvtimestamp.setText("");
                    }
                }
            }
        }
        if (news_list.getPicture().length() >= 1)
            showPhoto(news_list.getPicture(), view);
        else
            showPhoto("null", view);
        return view;
    }

    private void showPhoto(String url, View v) {
        Picasso picassoPhoto = new Picasso.Builder(this.getContext()).listener((picasso1, uri, exception) -> picasso1.load(R.drawable.brokenimage).into(imagenews)).build();
        picassoPhoto.load(url).error(R.drawable.brokenimage).placeholder(R.drawable.loading).into(imagenews);
    }

    @Override
    public void onClick(View view) {
        Intent news_intent = new Intent(Intent.ACTION_VIEW);
        String news_url = news_list.getNewsURL();
        news_intent.setData(Uri.parse(news_url));
        startActivity(news_intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savestate) {

        super.onSaveInstanceState(savestate);
    }

    @Override
    public void onActivityCreated(Bundle articlebundle) {
        super.onActivityCreated(articlebundle);
        if (articlebundle != null) { }
    }

}