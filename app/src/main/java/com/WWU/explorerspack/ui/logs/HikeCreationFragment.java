package com.WWU.explorerspack.ui.logs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.utilities.StorageUtilities;
import com.WWU.explorerspack.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HikeCreationFragment extends Fragment {
    private JSONObject mainStorage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.hike_creation_fragment, container, false);
        Button buttonView = rootView.findViewById(R.id.create_button);

        String storage = StorageUtilities.read(getActivity(), "storage.json");
        try {
            mainStorage = new JSONObject(storage);
            JSONObject hikes = mainStorage.getJSONObject("hikeLogs");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load settings",
                    Toast.LENGTH_SHORT).show();
        }

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(rootView);
                navController.navigateUp();
            }
        });

        return rootView;
    }

}
