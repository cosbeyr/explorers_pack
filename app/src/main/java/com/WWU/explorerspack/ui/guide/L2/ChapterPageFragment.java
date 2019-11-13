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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ChapterPageFragment extends Fragment {

    private ChapterPageViewModel mViewModel;
    private JSONObject chapterObj;
    private String id;

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
        TextView textView = rootView.findViewById(R.id.description);
        //textView.setText("description");
         if(id != null){
             chapterObj = ChapterContent.ITEM_MAP.get(id).myObj;
             //System.out.println(chapterObj.toString());
         }
         textView.setText(chapterObj.toString());

         return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChapterPageViewModel.class);
        // TODO: Use the ViewModel
    }

}