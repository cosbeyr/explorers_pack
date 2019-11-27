package com.WWU.explorerspack.ui.logs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Toast;

import com.WWU.explorerspack.MainActivity;
import com.WWU.explorerspack.R;

import com.WWU.explorerspack.ui.logs.hike_item.HikeList;
import com.WWU.explorerspack.ui.logs.hike_item.HikeList.HikeItem;
import com.WWU.explorerspack.utilities.StorageUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.WWU.explorerspack.ui.logs.hike_item.HikeList.HikeItem.addItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HikeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyHikeRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HikeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HikeFragment newInstance(int columnCount) {
        HikeFragment fragment = new HikeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        View view = inflater.inflate(R.layout.fragment_hike_list, container, false);
        hideKeyboardFrom(view.getContext(), view);
        ((MainActivity) getActivity()).setActionBarTitle("Hike Logs");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            JSONObject hikeLogs = null;
            String storage = StorageUtilities.read(getActivity(), StorageUtilities.jsonStorageName);
            try {
                hikeLogs = new JSONObject(storage);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to load hike log information",
                        Toast.LENGTH_SHORT).show();
            }
            Iterator<String> keys = hikeLogs.keys();
            HikeList.ITEMS = new ArrayList<HikeItem>();
            HikeList.ITEM_MAP = new HashMap<String, HikeItem>();

            while(keys.hasNext()) {
                String key = keys.next();
                addItem(key);
            }
            adapter = new MyHikeRecyclerViewAdapter(HikeList.ITEMS, mListener);
            recyclerView.setAdapter(adapter);
            ((MainActivity) getActivity()).setLogAdaptor(adapter);
        }
        return view;
    }

    public MyHikeRecyclerViewAdapter getLogsAdaptor(){
        return adapter;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(HikeItem item);
    }
}
