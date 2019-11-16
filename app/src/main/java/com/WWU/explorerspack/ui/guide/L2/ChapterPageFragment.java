package com.WWU.explorerspack.ui.guide.L2;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.JSONManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import us.feras.mdv.MarkdownView;

public class ChapterPageFragment extends Fragment {

    private ChapterPageViewModel mViewModel;
    private JSONObject chapterObj;
    private String id;
    private String title;
    private String intro;
    private JSONArray subChapters;

    public static ChapterPageFragment newInstance() {
        return new ChapterPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            id = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         View rootView = inflater.inflate(R.layout.chapter_page_fragment, container, false);
        MarkdownView markdownView = rootView.findViewById(R.id.markdownView);

         if(id != null){
             chapterObj = ChapterContent.ITEM_MAP.get(id).myObj;
             //System.out.println(chapterObj.toString());
             title = ChapterContent.ITEM_MAP.get(id).content;
             try {
                 intro = chapterObj.getString("Introduction");
                 subChapters = JSONManager.getInstance(null).getL2Tiles(title);
             } catch (Exception e) {
                 //DO nothing
             }
             System.out.println("");
         }
         markdownView.loadMarkdown(intro);

         return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChapterPageViewModel.class);
        // TODO: Use the ViewModel
    }

}
