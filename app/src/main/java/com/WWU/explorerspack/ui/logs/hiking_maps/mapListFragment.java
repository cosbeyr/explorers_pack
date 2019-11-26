package com.WWU.explorerspack.ui.logs.hiking_maps;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent;
import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent.MapListItem;
import com.WWU.explorerspack.utilities.APIUtilities;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnMapListFragmentInteractionListener}
 * interface.
 */
public class mapListFragment extends Fragment implements APIUtilities.AsyncResponse {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnMapListFragmentInteractionListener mListener;
    private APIUtilities queryTask;//async task to handle the api call
    private MymapListItemRecyclerViewAdapter mAdapter;
    private String privateKey = "200643194-330249a32bc7dc3af1462976dc1c12cc";
    private String lat = "48.7697";//lat and lon for bellingham TODO: get location dynamically.
    private String lon = "-122.4858";
    private String maxDistance = "200"; //max distance in miles from location to search for hikes.
    private String sort = "distance";
    private String maxResults = "50";
    private String queryString;//the completed query string for the api.
    private FusedLocationProviderClient fusedLocationClient;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public mapListFragment() {
    }


    @SuppressWarnings("unused")
    public static mapListFragment newInstance(int columnCount) {
        mapListFragment fragment = new mapListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_list, container, false);



        queryTask = new APIUtilities(getActivity());
        queryTask.delegate = this;
        queryString = String.format("https://www.hikingproject.com/data/get-trails?lat=%s&lon=%s&maxDistance=%s&key=%s&sort=%s&maxResults=%s",lat,lon,maxDistance,privateKey,sort,maxResults);
        queryTask.execute(queryString);//TODO: implement query string.

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.ItemDecoration itemDecoration = new
                    DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MymapListItemRecyclerViewAdapter(MapListContent.ITEMS,mListener);
            ((MainActivity)getActivity()).setMapAdaptor(mAdapter);
            ((MainActivity)getActivity()).setActionBarTitle("Maps");
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapListFragmentInteractionListener) {
            mListener = (OnMapListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(ArrayList<MapListItem> resultList) {
        //Here you will receive the result fired from async task
        //of onPostExecute(result) method.
        if(resultList != null) {
            Log.i("PROGRESS:", "Result list is not null going to adapter");
            for (int i = 0; i< resultList.size(); i++){
                Log.i("PROGRESS2",resultList.get(i).hikeName);
            }
            mAdapter.updateData(resultList);
        }
        else{
            Log.i("ERROR:","Result list is null");
        }
        //Toast.makeText(getActivity(),resultList.get(0).hikeName.substring(0,10), Toast.LENGTH_SHORT).show();
        //TODO: save query to file then implement caching method here?
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMapListFragmentInteractionListener {

        void onListFragmentInteraction(MapListItem item);
    }
}
