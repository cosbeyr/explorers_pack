package com.WWU.explorerspack.ui.logs;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.text.TextWatcher;
import android.text.Editable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SubHikeFragment extends Fragment {

    private SubHikeViewModel mViewModel;
    private String hikeTitle;
    private JSONObject hikeJSON;
    private JSONObject storage;

    public static SubHikeFragment newInstance() {
        return new SubHikeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            hikeTitle = getArguments().getString("id");
            ((MainActivity) getActivity()).setActionBarTitle(hikeTitle);
            JSONObject hikeLogs = null;
            try {
                storage = new JSONObject(StorageUtilities.read(getActivity(), StorageUtilities.jsonStorageName));
                hikeJSON = storage.getJSONObject(hikeTitle);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.sub_hike_fragment, container, false);
        EditText notes = rootView.findViewById(R.id.note_edit_text);
        try {
            notes.setText(hikeJSON.getString("notes"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hikeJSON.put("notes", s.toString());
                    storage.put(hikeTitle, hikeJSON);
                    StorageUtilities.create(getActivity(), StorageUtilities.jsonStorageName, storage.toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubHikeViewModel.class);
        // TODO: Use the ViewModel
    }

}
