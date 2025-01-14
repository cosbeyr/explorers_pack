package com.WWU.explorerspack.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.IpSecManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class APIUtilities extends AsyncTask<String, Integer, ArrayList<MapListContent.MapListItem>> {
    private ProgressDialog p;
    private String JSONResultString;
    private ArrayList<MapListContent.MapListItem> resultList = new ArrayList<>();
    public AsyncResponse delegate = null;
    private Context mContext;
    private FusedLocationProviderClient fusedLocationClient;
    private String city = "default";
    private boolean isCached = false;
    private InputStream urlInputStream;
    private JSONObject imageCache = null;

    public APIUtilities (Context context){
        mContext = context;
    }

    @Override
    protected ArrayList<MapListContent.MapListItem> doInBackground(String... queryString) {
        MapListContent.MapListItem tempItem;
        Bitmap imagemap = null;
        String hikeName;
        String lat;
        String lon;
        String imageurl;
        String stars;
        String length;
        String hikeLocation;
        double defaultLat = 48.7519;
        double defaultLon = -122.4787;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        //TODO: fix null location error.
        try {
            Location location =  Tasks.await(fusedLocationClient.getLastLocation());
            List<Address> addresses;
            if(location != null){
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            }
            else{
                addresses = gcd.getFromLocation(defaultLat,defaultLon,1);
            }

            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();

                if (StorageUtilities.isFilePresent(mContext, StorageUtilities.apiCache)) {
                    String fileJSON = StorageUtilities.read(mContext, StorageUtilities.apiCache);
                    JSONObject queryList = new JSONObject(fileJSON);
                    if (queryList.has(city)) {
                        isCached = true;
                    }
                    //System.out.println(addresses.get(0).getLocality());
                } else {
                    isCached = false;
                }
            }
        }catch (ExecutionException e) {
            // The Task failed, this is the same exception you'd get in a non-blocking
            // failure handler.
            // ...
        } catch (InterruptedException e) {
            // An interrupt occurred while waiting for the task to complete.
            // ...
        }catch (IOException ex){
            //
        }
        catch (JSONException ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }


        if (isCached){
            try{

                String fileJSON = StorageUtilities.read(mContext,StorageUtilities.apiCache);
                JSONObject queryList = new JSONObject(fileJSON);
                JSONArray trails = queryList.getJSONArray(city);

                for (int i = 0; i < trails.length(); i++) {
                    hikeName = ((JSONObject) trails.get(i)).getString("name");
                    lat = ((JSONObject) trails.get(i)).getString("latitude");
                    lon = ((JSONObject) trails.get(i)).getString("longitude");
                    imageurl = ((JSONObject) trails.get(i)).getString("imgSqSmall");
                    try {
                        imagemap = getCacheImage(getImageName(imageurl));
                        if(imagemap == null){
                            URLConnection con = new URL(imageurl).openConnection();
                            con.setUseCaches(true);
                            urlInputStream = con.getInputStream();
                            imagemap = BitmapFactory.decodeStream(urlInputStream);

                            if (this.urlInputStream != null) {
                                try {
                                    this.urlInputStream.close();
                                } catch (IOException e) {
                                    ; // swallow
                                } finally {
                                    this.urlInputStream = null;
                                }
                            }
                        }

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    // can use some more params, i.e. caching directory etc
                    stars = ((JSONObject) trails.get(i)).getString("stars");
                    length = ((JSONObject) trails.get(i)).getString("length");
                    hikeLocation = ((JSONObject) trails.get(i)).getString("location");
                    tempItem = new MapListContent.MapListItem(Integer.toString(i), hikeName, lat, lon, imageurl, stars, length,hikeLocation, imagemap);
                    resultList.add(tempItem);
                    cacheTrailImage(mContext, getImageName(imageurl), imagemap);
                    Log.i("BUILDING", resultList.get(i).hikeName);

                }
            }catch (JSONException ex){
                ex.printStackTrace();
                throw new RuntimeException();
            }
        }
        else {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            if (android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            // Request a string response frcacheTrailImage()om the provided URL.
            RequestFuture<String> blockingrequest = RequestFuture.newFuture();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, queryString[0], blockingrequest, blockingrequest);
            queue.add(stringRequest);
            String response = "";

            try {
                response = blockingrequest.get(); // this will block
            } catch (InterruptedException e) {
                // exception handling
            } catch (ExecutionException e) {
                // exception handling
            }


            JSONResultString = response;

            //TODO: add expiration dates to each query and remove them over time. We could do this


            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray trails = jsonObject.getJSONArray("trails");
                //if the file exists append to it, otherwise create it
                if(StorageUtilities.isFilePresent(mContext, StorageUtilities.apiCache)){
                    String fileJSON = StorageUtilities.read(mContext,StorageUtilities.apiCache);
                    JSONObject queryList = new JSONObject(fileJSON);
                    queryList.put(city,trails);
                    StorageUtilities.create(mContext,StorageUtilities.apiCache,queryList.toString());
                }else{
                    JSONObject qObject = new JSONObject();
                    qObject.put(city,trails);
                    StorageUtilities.create(mContext,StorageUtilities.apiCache,qObject.toString());
                }

                for (int i = 0; i < trails.length(); i++) {
                    hikeName = ((JSONObject) trails.get(i)).getString("name");
                    lat = ((JSONObject) trails.get(i)).getString("latitude");
                    lon = ((JSONObject) trails.get(i)).getString("longitude");
                    imageurl = ((JSONObject) trails.get(i)).getString("imgSqSmall");
                    stars = ((JSONObject) trails.get(i)).getString("stars");
                    length = ((JSONObject) trails.get(i)).getString("length");
                    hikeLocation = ((JSONObject) trails.get(i)).getString("location");

                    try {
                        URLConnection con = new URL(imageurl).openConnection();
                        con.setUseCaches(true);
                        urlInputStream = con.getInputStream();
                        imagemap = BitmapFactory.decodeStream(urlInputStream);
                        if (this.urlInputStream != null) {
                            try {
                                this.urlInputStream.close();
                            } catch (IOException e) {
                                ; // swallow
                            } finally {
                                this.urlInputStream = null;
                            }
                        }

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    tempItem = new MapListContent.MapListItem(Integer.toString(i), hikeName, lat, lon, imageurl, stars, length,hikeLocation, imagemap);
                    resultList.add(tempItem);
                    Log.i("BUILDING", resultList.get(i).hikeName);

                }
//                            String item = ((JSONObject) trails.get(0)).getString("name");
//                            Log.i("JSON NAME: ", item);
            } catch (JSONException ex) {
                ex.printStackTrace();
                throw new RuntimeException();
            }
        }
        return resultList;
    }

    private void cacheTrailImage(Context context,String filename, Bitmap bmp) {
        try{
            // save the image to disk
            String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
            OutputStream fOut = null;
            File file = new File(path, filename);
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            if(StorageUtilities.isFilePresent(context, StorageUtilities.apiImagesCache)) {
                if (imageCache == null){
                    String imageCacheRaw = StorageUtilities.read(context, StorageUtilities.apiImagesCache);
                    imageCache = new JSONObject(imageCacheRaw);
                }
            } else {
                imageCache = new JSONObject();
            }
            JSONObject photo = new JSONObject();
            photo.put("path", file.getAbsolutePath());
            photo.put("exp", new Date());
            imageCache.put(filename,photo);
            StorageUtilities.create(mContext, StorageUtilities.apiImagesCache, imageCache.toString());

        } catch (Exception e){
         e.printStackTrace();
        }

    }

    // return cached image if exist
    private Bitmap getCacheImage(String filename){
        Bitmap result = null;
        try{
            if(StorageUtilities.isFilePresent(mContext, StorageUtilities.apiImagesCache)) {
                if (imageCache == null){
                    String imageCacheRaw = StorageUtilities.read(mContext, StorageUtilities.apiImagesCache);
                    imageCache = new JSONObject(imageCacheRaw);
                }
                JSONObject photo = imageCache.getJSONObject(filename);
                result = BitmapFactory.decodeFile(photo.getString("path"));
                return result;
            } else {
                return result;

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private String getImageName(String imageURL){
        String[] imageUrlSplit = imageURL.split("/");
        return imageUrlSplit[imageUrlSplit.length-1];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        super.onPreExecute();
        p = new ProgressDialog(mContext);
        p.setMessage("Hang on while we find hikes near you!");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
    }

    @Override
    protected void onPostExecute(ArrayList<MapListContent.MapListItem> queryResultList){
        super.onPostExecute(queryResultList);
        p.dismiss();
        if(queryResultList.size() != 0){
            Log.i("POSTEXECUTE","SIZE in post execute is not 0");
        }else{
            Log.i("POSTEXECUTE: ", "SIZE in post execute is 0");
        }
        delegate.processFinish(queryResultList);
        mContext = null;
    }

    public ArrayList<MapListContent.MapListItem> getResultList(){
        return resultList;
    }

    public interface AsyncResponse {
        void processFinish(ArrayList<MapListContent.MapListItem> resultList);
    }

}
