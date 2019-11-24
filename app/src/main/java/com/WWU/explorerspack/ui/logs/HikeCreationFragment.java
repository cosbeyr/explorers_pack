package com.WWU.explorerspack.ui.logs;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;

import com.WWU.explorerspack.R;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HikeCreationFragment extends DialogFragment {
    private Button ok;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private static final int REQUEST_PERMISSIONS = 1;
    private final String[] requiredPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};

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
                // must check permission at run time
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions( //Method of Fragment
                            requiredPermissions,
                            REQUEST_PERMISSIONS
                    );
                }
                navController.navigateUp();
                String titleText = titleTextObj.getText().toString();
                String notesText = notesTextObj.getText().toString();
                JSONObject hikeLogs = createHike(titleText, notesText);
                hideKeyboardFrom(getActivity(),rootView);
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i=0; i < permissions.length; i++){
            if (permissions[i].equals(requiredPermissions[i])
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("info", requiredPermissions[i] + " permission granted");
            }
        }
    }

    private void showExistingHike(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.go_to_exists_hike, null);
        dialog.setContentView(view);

        ok = (Button) dialog.findViewById(R.id.btn_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private JSONObject createHike(String title, String notes) {
        /*
       Read in the current hike log storage and create a new object with new hike information.
       Return the new hike information object.
         */
        JSONObject hikeLogs = null;
        String storage = StorageUtilities.read(getActivity(), StorageUtilities.jsonStorageName);
        JSONObject hike = new JSONObject();
        JSONObject photo = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            hikeLogs = new JSONObject(storage);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load hike log information",
                    Toast.LENGTH_SHORT).show();
        }
        try {
            hikeLogs.get(title);
            showExistingHike();

        } catch (JSONException e1) {
            try {
                hike.put("notes", notes);
                jsonArray.put(photo);
                hike.put("map","");
                hike.put("photos", jsonArray);
                hikeLogs.put(title, hike);
                StorageUtilities.create(getActivity(), StorageUtilities.jsonStorageName, hikeLogs.toString());

            } catch (JSONException e2) {
                e2.printStackTrace();
                Toast.makeText(getActivity(), "Failed to save new hike information",
                        Toast.LENGTH_SHORT).show();
            }
        }

        return hikeLogs;
    }
}
