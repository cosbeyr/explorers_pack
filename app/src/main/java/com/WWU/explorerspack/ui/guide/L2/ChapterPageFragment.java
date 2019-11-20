package com.WWU.explorerspack.ui.guide.L2;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent;
import com.WWU.explorerspack.ui.guide.JSONManager;
import com.WWU.explorerspack.ui.guide.L2.SubChapterData.SubChapterContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ChapterPageFragment extends Fragment {

    private ChapterPageViewModel mViewModel;
    private JSONObject chapterObj;
    private String id;
    private String title;
    private JSONArray subChapters;
    private int mColumnCount = 2;
    private OnSubListFragmentInteractionListener mListener;

    public static ChapterPageFragment newInstance() {
        return new ChapterPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            id = getArguments().getString("id");
        }
        String bartitle =(String) ChapterContent.ITEM_MAP.get(id).content;
        ((MainActivity) getActivity()).setActionBarTitle(bartitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        String bartitle =(String) ChapterContent.ITEM_MAP.get(id).content;
        ((MainActivity) getActivity()).setActionBarTitle(bartitle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         //may need to remove final
         final View rootView = inflater.inflate(R.layout.chapter_page_fragment, container, false);
//        Button L3Button = rootView.findViewById(R.id.go_to_button);
//
//        L3Button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavController mNav = Navigation.findNavController(rootView);
//                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
//                        "This is a message displayed in a Toast",
//                        Toast.LENGTH_SHORT);
//
//                toast.show();
//                mNav.navigate(R.id.action_navigation_chapter_to_sub_chapter_page);
//            }
//        });

         if(id != null){
             chapterObj = ChapterContent.ITEM_MAP.get(id).myObj;
             //System.out.println(chapterObj.toString());
             title = ChapterContent.ITEM_MAP.get(id).content;
             try {
                 subChapters = JSONManager.getInstance(null).getL2Tiles(title);
             } catch (Exception e) {
                 //DO nothing
             }
             System.out.println("");
         }
         //send the sub chapters to the recycler view.

        //set the adapter for the recycler view
        RecyclerView recyclerView =  (RecyclerView) rootView.findViewById(R.id.l2_list);
        if(mColumnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), mColumnCount));
        }
        //sub chapter content is a list of sub chapter items which are the sections for  a chapter.
        SubChapterContent subChapterContent = new SubChapterContent();

        JSONManager myJSONManager = JSONManager.getInstance(null);

        try{
            JSONArray sections = myJSONManager.getL2Tiles(title);
            for(int i = 0; i < sections.length(); i++){
                subChapterContent.addItem(new SubChapterContent.SubChapterItem(String.valueOf(i), title, sections.get(i).toString()));
            }
        }catch (JSONException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        recyclerView.setAdapter(new MySectionRecyclerViewAdapter(subChapterContent.ITEMS,mListener,this.getContext()));

         return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChapterPageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSubListFragmentInteractionListener) {
            mListener = (OnSubListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //this is implemented for the
    public interface OnSubListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSubListFragmentInteraction(SubChapterContent.SubChapterItem item);
    }

}
