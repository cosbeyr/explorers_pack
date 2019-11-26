package com.WWU.explorerspack.ui.logs;

import androidx.lifecycle.ViewModelProviders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.text.TextWatcher;
import android.text.Editable;
import android.media.ExifInterface;

import android.graphics.Matrix;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONObject;
import org.json.JSONArray;

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
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_search).collapseActionView();
        super.onPrepareOptionsMenu(menu);
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

       ///////////////////////////////////////////////////////////////// // Notes Setup
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

/*
        LinearLayout layout = (LinearLayout)findViewById(R.id.imageLayout);
        for(int i=0;i<10;i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            image.setMaxHeight(20);
            image.setMaxWidth(20);

            // Adds the view to the layout
            layout.addView(image);
        }
*/
        /////////////////////////////////////////////////////////////////////////////// Photos Setup
        LinearLayout photosLayout = (LinearLayout) rootView.findViewById(R.id.photos);

        try {
            JSONArray hikePhotosArray = hikeJSON.getJSONArray("photos");
            int len = hikePhotosArray.length();
            for(int i = 0; i < len; i++) {
                JSONObject photoObj = hikePhotosArray.getJSONObject(i);

                ImageView nextImage = new ImageView(getContext());

                int margin = (int) convertDpToPixel(8);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(margin, margin, margin, margin);
                nextImage.setLayoutParams(lp);

                /*
                                android:layout_width="100dp"
                android:layout_height="100dp"
                android:layout_margin="@dimen/text_margin"
                android:src="@drawable/ic_placeholder_photo"
                android:textAppearance="?attr/textAppearanceListItem" />
                 */

                JSONArray photoArray = photoObj.names();
                if(photoArray != null) {
                    String photoPath = photoArray.getString(0);

                    int px = (int) convertDpToPixel(100);

                    Bitmap bitmap = BitmapFactory.decodeFile(photoObj.getString(photoPath));
                    bitmap = bitmap.createScaledBitmap(bitmap, px, px, false);
                    int rotation = getPhotoRotation(photoPath);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    int bmw = bitmap.getWidth();
                    int bmh = bitmap.getHeight();
                    //bitmap.setWidth(100);
                    //bitmap.setHeight(100);

                    nextImage.setImageBitmap(bitmap);
                    photosLayout.addView(nextImage);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        setHasOptionsMenu(true);
        return rootView;

        


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SubHikeViewModel.class);
        // TODO: Use the ViewModel
    }

    public float convertDpToPixel(float dp){
        return dp * ((float) getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static int getPhotoRotation(String photoPath) {
        int rotation = 0;
        try {
            ExifInterface ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotation = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotation = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotation = 270;
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotation = 0;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotation;
    }
}
