package com.WWU.explorerspack.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.health.SystemHealthManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.WWU.explorerspack.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class CameraFragment extends Fragment implements View.OnClickListener {

    private CameraViewModel cameraViewModel;
    private ImageView imageView;
    private Button takePicture;
    private String currentPhotoPath;
    static final int CAMERA_REQUEST_CODE = 1;

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
                System.out.println("Trying to take pic..");
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

//    private File createImageFile() throws IOException {
//
//        // A seperate Method to get the timestamp ina formatted manner
//        String timeStamp = getCurrentTimeStamp();
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        String imgStore = storageDir.getAbsolutePath()
//                + File.separator;
//        System.out.printf("storageDir %s\n",storageDir);
//        System.out.printf("imageStore %s\n",imgStore);
//
////        storageDir = new File(imgStore);
////        try {
////            if (!storageDir.exists()) {
////                System.out.println("trying to create storageDir...");
////                boolean stat = storageDir.mkdirs();
////                System.out.printf("create tempfolder %b\n", stat);
////            } else {
////                System.out.printf("%s exists", imgStore);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//        File imageFile = null;
//        try {
//            imageFile = File.createTempFile(imageFileName,
//                    ".jpg", storageDir);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        currentPhotoPath = imageFile.getAbsolutePath();
//        return imageFile;
//    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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


    public static String getCurrentTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        return timeStamp;
    }

    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {

            // This will add the photo to the users gallery
            galleryAddPic();

            /* This adds the image to the screen.
               For this app this creates an imageview each time a photo
               is taken and adds it to a Flexbox layout
           */

            setPic();

            Toast.makeText(getActivity(), "Photo Taken And Saved",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "The photo was not saved",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onClick(View view)
    {
        dispatchTakePictureIntent();
    }


        public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cameraViewModel =
                ViewModelProviders.of(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        final TextView textView = root.findViewById(R.id.text_camera);
        imageView = root.findViewById(R.id.image_view);
        takePicture = root.findViewById(R.id.button);
        takePicture.setOnClickListener(this);
        cameraViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}