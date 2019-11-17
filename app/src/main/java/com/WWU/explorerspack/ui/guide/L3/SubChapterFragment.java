package com.WWU.explorerspack.ui.guide.L3;

import androidx.annotation.NavigationRes;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.WWU.explorerspack.R;

import static androidx.navigation.Navigation.findNavController;

public class SubChapterFragment extends Fragment {

    private SubChapterViewModel mViewModel;

    public static SubChapterFragment newInstance() {
        return new SubChapterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.sub_chapter_fragment, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubChapterViewModel.class);
        // TODO: Use the ViewModel
    }


}
