package com.WWU.explorerspack.ui.logs;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;

public class SubHikeFragment extends Fragment {

    private SubHikeViewModel mViewModel;
    private String hikeTitle;

    public static SubHikeFragment newInstance() {
        return new SubHikeFragment();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            hikeTitle = getArguments().getString("id");
            ((MainActivity) getActivity()).setActionBarTitle(hikeTitle);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.sub_hike_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubHikeViewModel.class);
        // TODO: Use the ViewModel
    }

}
