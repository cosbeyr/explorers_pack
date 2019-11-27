package com.WWU.explorerspack.ui.logs.hiking_maps;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.WWU.explorerspack.R;


import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent;
import com.WWU.explorerspack.ui.logs.hiking_maps.mapListFragment.OnMapListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MapListContent.MapListItem} and makes a call to the
 * specified {@link OnMapListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymapListItemRecyclerViewAdapter extends RecyclerView.Adapter<MymapListItemRecyclerViewAdapter.ViewHolder> {

    private final List<MapListContent.MapListItem> mValues;
    private final OnMapListFragmentInteractionListener mListener;
    private ArrayList<MapListContent.MapListItem> removedItems = new ArrayList<>();
    private HashMap<String,Integer> keyMap = new HashMap<>();

    public MymapListItemRecyclerViewAdapter(List<MapListContent.MapListItem> items, OnMapListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateData(ArrayList<MapListContent.MapListItem> newList){
        mValues.clear();
        mValues.addAll(newList);
        if (newList.size() != 0)
        Log.i("ADAPTER: ", newList.get(0).hikeName);
        else{
            Log.i("ADAPTER","list is empty");
        }
        notifyDataSetChanged();
        MapListContent.ITEMS.clear();
        MapListContent.ITEMS.addAll(newList);
        MapListContent.ITEM_MAP.clear();
        for (int i = 0; i<newList.size();i++){
            MapListContent.ITEM_MAP.put(newList.get(i).hikeName,newList.get(i));//insert the new hikes into the map by name.
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_map_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.mHikeNameView.setText(mValues.get(position).hikeName);
        if(!mValues.get(position).isdummy) {
            String length = mValues.get(position).length + " mi";
            holder.mlengthView.setText(length);
            holder.mLocationView.setText(mValues.get(position).location);
            holder.mratingView.setRating(Float.parseFloat(mValues.get(position).stars));
            if (mValues.get(position).imageMap != null){
                holder.mpictureView.setImageBitmap(mValues.get(position).imageMap);
            }

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private void update(){
        this.notifyDataSetChanged();
    }

    public void search(String keywords){
        returnItem();
        for (MapListContent.MapListItem map: mValues) {
            if(!map.hikeName.toLowerCase().startsWith(keywords)){
                // add to remove hike list to show again later
                removedItems.add(map);
                // keep track of order of item
                keyMap.put(map.id, mValues.indexOf(map));

            }
        }
        for (MapListContent.MapListItem map: removedItems){
            mValues.remove(map);
        }
        update();
    }
    public void returnItem(){
        while(!removedItems.isEmpty()){
            MapListContent.MapListItem map = removedItems.remove(0);
            try {
                mValues.add(keyMap.get(map.id),map);
                keyMap.remove(map.id);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        update();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mIdView;
        public final TextView mHikeNameView;
        public RatingBar mratingView;
        public  TextView mlengthView;
        public  ImageView mpictureView;
        public TextView mLocationView;
        public MapListContent.MapListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            mHikeNameView = (TextView) view.findViewById(R.id.hikeName);
            mlengthView = (TextView) view.findViewById(R.id.hike_length);
            mpictureView = (ImageView) view.findViewById(R.id.trail_image);
            mratingView = (RatingBar) view.findViewById(R.id.rating);
            mLocationView = (TextView) view.findViewById(R.id.location);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHikeNameView.getText() + "'";
        }
    }
}
