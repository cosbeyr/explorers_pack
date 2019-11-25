package com.WWU.explorerspack.ui.logs.hiking_maps;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.WWU.explorerspack.R;


import com.WWU.explorerspack.ui.logs.hiking_maps.MapContent.MapListContent;
import com.WWU.explorerspack.ui.logs.hiking_maps.mapListFragment.OnMapListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MapListContent.MapListItem} and makes a call to the
 * specified {@link OnMapListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymapListItemRecyclerViewAdapter extends RecyclerView.Adapter<MymapListItemRecyclerViewAdapter.ViewHolder> {

    private final List<MapListContent.MapListItem> mValues;
    private final OnMapListFragmentInteractionListener mListener;

    public MymapListItemRecyclerViewAdapter(List<MapListContent.MapListItem> items, OnMapListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void updateData(ArrayList<MapListContent.MapListItem> newList){
        mValues.clear();
        mValues.addAll(newList);
        notifyDataSetChanged();
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
        holder.mIdView.setText(mValues.get(position).id);
        holder.mHikeNameView.setText(mValues.get(position).hikeName);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mHikeNameView;
        public MapListContent.MapListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mHikeNameView = (TextView) view.findViewById(R.id.hikeName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mHikeNameView.getText() + "'";
        }
    }
}
