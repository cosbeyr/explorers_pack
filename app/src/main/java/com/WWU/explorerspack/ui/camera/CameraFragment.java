package com.WWU.explorerspack.ui.camera;

import com.WWU.explorerspack.utilities.StorageUtilities;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.WWU.explorerspack.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends DialogFragment {
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private CameraViewModel cameraViewModel;
    private ImageView imageView;
    private Button takePicture;
    private String currentPhotoPath;
    private static final int CAMERA_REQUEST_CODE = 1;
    private JSONObject mainStorage;
    private String[] hikeArray;
    private Button yes, no;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);



    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getContext().getPackageManager())
                != null) {
            File photoFile = null;
            try {
                /* This method will create a file with the current timestamp
                   Really I feel this try catch is unnecessary
                   as the createImageFile
                   method I created had a try catch in there but
                   AndroidStudio complained.

                */
                Log.i("INFO","Trying to take pic..");
                photoFile = createImageFile();
                System.out.printf("PhotoFile Path %s\n",photoFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.WWU.explorerspack.fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                // This will open the
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        } else {

            // This will show in the event of a problem.com.WWU.explorerespack.fileprovider
            Toast.makeText(getActivity().getBaseContext(),
                    "Sorry there is a problem with taking photos.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("info", "permission granted");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        // getExternalFilesDir is private to app only
        // File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        // File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStorageDirectory();

        // must check permission at run time
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( //Method of Fragment
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }


    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            try{
                // only add to Gallery of settings say so. Yes by default
                JSONObject settings = mainStorage.getJSONObject("settings");
                boolean saveToGallery =  settings.getBoolean("saveToGallery");
                if (saveToGallery) {
                    addImageToGallery(currentPhotoPath, getActivity());
                }
            } catch (JSONException e){
                e.printStackTrace();
            }

            setPic();
            Toast.makeText(getActivity(), "Photo Taken And Saved",
                    Toast.LENGTH_SHORT).show();

            showAddToHike();
        } else {
            Toast.makeText(getActivity(), "The photo was not saved",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void showAddToHike(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.add_to_hike_dialog, null);
        dialog.setContentView(view);

        yes = (Button) dialog.findViewById(R.id.btn_yes);
        no = (Button) dialog.findViewById(R.id.btn_no);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                dialog.dismiss();
                if(hikeArray.length > 0){
                    showHikes();
                } else {
                    Toast.makeText(getActivity(), "You haven't started any hike yet!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(buttonClick);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showHikes(){
        AlertDialog.Builder window = new AlertDialog.Builder(getActivity());;
        window.setTitle("Select a hike")
                .setItems(hikeArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        try{
                            // load data
                            JSONObject hikeLogs = mainStorage.getJSONObject("hikeLogs");
                            JSONObject selectedHike = hikeLogs.getJSONObject(hikeArray[item]);
                            JSONArray photos = selectedHike.getJSONArray("photos");
                            JSONObject photo = new JSONObject();

                            // add to photos
                            photo.put(currentPhotoPath.split("/")[currentPhotoPath.split("/").length-1], currentPhotoPath);
                            photos.put(photo);
                            StorageUtilities.create(getActivity(), "storage.json", mainStorage.toString());
                            Toast.makeText(getActivity(), "Added photo to " + hikeArray[item] + " successfully.",
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed to add photo to " + hikeArray[item],
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        window.show();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {
        cameraViewModel =
                ViewModelProviders.of(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        imageView = root.findViewById(R.id.image_view);
        takePicture = root.findViewById(R.id.button);
        String storage = StorageUtilities.read(getActivity(), "storage.json");

        try {
            mainStorage = new JSONObject(storage);
            JSONObject hikes = mainStorage.getJSONObject("hikeLogs");
            Iterator<String> keys = hikes.keys();
            List<String> hikeList = new ArrayList<>();

            while(keys.hasNext()) {
                String key = keys.next();
                hikeList.add(key);
            }
            hikeArray = new String[hikeList.size()];
            for(int i = 0; i < hikeList.size(); i++){
                hikeArray[i] = hikeList.get(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to load settings",
                    Toast.LENGTH_SHORT).show();

        }

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });
        return root;
    }

}