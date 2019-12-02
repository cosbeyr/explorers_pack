package com.WWU.explorerspack.ui.logs;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
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
import android.util.Log;
import android.view.View.OnClickListener;

import android.graphics.Matrix;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class SubHikeFragment extends Fragment implements OnMapReadyCallback{

    private SubHikeViewModel mViewModel;
    private String hikeTitle;
    private JSONObject hikeJSON;
    private JSONObject storage;
    private ImageView imagePreview;
    MapView mapView;
    GoogleMap map;

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
    public void onMapReady(GoogleMap map) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        this.map = map;
        float lat = 0.0f;
        float lon = 0.0f;
        String trailName = "";
        try {
            if (hikeJSON.has("map")){
                Object test = hikeJSON.get("map");
                if(test instanceof JSONObject) {
                    JSONObject mapObj = hikeJSON.getJSONObject("map");
                    if (mapObj.has("lat")) {
                        lat = Float.parseFloat(mapObj.getString("lat"));
                        lon = Float.parseFloat(mapObj.getString("lon"));
                        trailName = mapObj.getString("trail");
                    }
                }
            }

        }catch (JSONException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
        if(!trailName.equals("")) {
            LatLng trail = new LatLng(lat, lon);
            map.addMarker(new MarkerOptions().position(trail).title(trailName));
            map.moveCamera(CameraUpdateFactory.newLatLng(trail));
            map.animateCamera( CameraUpdateFactory.zoomTo( 10.0f ) );
        }
        mapView.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.sub_hike_fragment, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        MapsInitializer.initialize(getActivity());
        mapView.getMapAsync(this);



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
                    final String photoPath = photoArray.getString(0);

                    int px = (int) convertDpToPixel(100);
                    final String photoPathString = photoObj.getString(photoPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(photoPathString);
                    bitmap = bitmap.createScaledBitmap(bitmap, px, px, false);
                    int rotation = getPhotoRotation(photoPath);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    nextImage.setImageBitmap(bitmap);
                    nextImage.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            showImagePreview(photoPathString);
                        }

                    });
                    photosLayout.addView(nextImage);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        setHasOptionsMenu(true);
        return rootView;

        


    }



    private void showImagePreview(String photoPath){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.image_preview_dialog, null);
        dialog.setContentView(view);

        imagePreview = (ImageView) dialog.findViewById(R.id.imagePreview);
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
        //int rotation = getPhotoRotation(photoPath);
       // Matrix matrix = new Matrix();
       // matrix.postRotate(rotation);
       // bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        imagePreview.setImageBitmap(bitmap);

        imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mapView = (MapView) view.findViewById(R.id.map_view);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);

    }



    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            try {
                mapView.onDestroy();
            } catch (NullPointerException e) {
                Log.e("ERROR", "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
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
