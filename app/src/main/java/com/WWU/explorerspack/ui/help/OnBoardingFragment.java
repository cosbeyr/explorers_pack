package com.WWU.explorerspack.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.camera.CameraViewModel;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OnBoardingFragment extends DialogFragment {

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_search).collapseActionView();
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.help, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("");
        setHasOptionsMenu(true);
        return root;
    }
}
