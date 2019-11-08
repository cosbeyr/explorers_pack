package com.WWU.explorerspack.ui.guide;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.WWU.explorerspack.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GuideFragment extends Fragment {

    private GuideViewModel guideViewModel;
    private JSONManager myJSONManager;
    private ArrayList<String> chaptersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        guideViewModel =
                ViewModelProviders.of(this).get(GuideViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guide, container, false);
        final TextView textView = root.findViewById(R.id.text_guide);


        guideViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        String json = loadJSONFromAsset(this.getContext());
        try {
            myJSONManager = new JSONManager(json);
            JSONArray chapters = myJSONManager.getL1Tiles();
            for (int i = 0; i < chapters.length(); i++) {

                //System.out.println(chapters.get(i));
                chaptersList.add(chapters.get(i).toString());

            }
        }
        catch (JSONException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);//JSON exceptions are rarley recoverable accourding to the docs.
        }



        return root;
    }


    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("guide.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}