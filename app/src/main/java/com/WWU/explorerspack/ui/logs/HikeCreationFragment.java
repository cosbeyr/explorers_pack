package com.WWU.explorerspack.ui.logs;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;

import com.WWU.explorerspack.R;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class HikeCreationFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.hike_creation_fragment, container, false);

        Button buttonView = rootView.findViewById(R.id.create_button);
        final EditText titleTextObj = rootView.findViewById(R.id.hike_title_text);
        final EditText notesTextObj = rootView.findViewById(R.id.notes_text);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(rootView);
                navController.navigateUp();
                String titleText = titleTextObj.getText().toString();
                String notesText = notesTextObj.getText().toString();
                JSONObject hikeLogs = createHike(titleText, notesText);
                Iterator<String> keys = hikeLogs.keys();
            }
        });

        return rootView;
    }
    private JSONObject createHike(String title, String notes) {
        /*
       Read in the current hike log storage and create a new object with new hike information.
       Return the new hike information object.
         */
        JSONObject hikeLogs = null;
        String storage = StorageUtilities.read(getActivity(), StorageUtilities.jsonStorageName);
        JSONObject hike = new JSONObject();
        try {
            hikeLogs = new JSONObject(storage);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load hike log information",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            hikeLogs.get(title);
            title = title + " (copy)";

        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            hike.put("notes", notes);
            hikeLogs.put(title, hike);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to save new hike information",
                    Toast.LENGTH_SHORT).show();
        }
        return hikeLogs;
    }
}
