package com.WWU.explorerspack.ui.logs;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.WWU.explorerspack.utilities.StorageUtilities;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.logs.HikeFragment.OnListFragmentInteractionListener;
import com.WWU.explorerspack.ui.logs.hike_item.HikeList.HikeItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HikeItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHikeRecyclerViewAdapter extends RecyclerView.Adapter<MyHikeRecyclerViewAdapter.ViewHolder> {

    private final List<HikeItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHikeRecyclerViewAdapter(List<HikeItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hike, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);

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
    private void update(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public HikeItem mItem;
        public Button removeButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.hike_id);
            mContentView = (TextView) view.findViewById(R.id.content);
            removeButton = (Button) view.findViewById(R.id.remove_hike);
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String hike_to_be_removed = mValues.get(getAdapterPosition()).id;
                    final String storage = StorageUtilities.read(v.getContext(), StorageUtilities.jsonStorageName);
                    final Context inner = v.getContext();
                    new AlertDialog.Builder(inner)
                            .setTitle("Delete hike")
                            .setMessage("Are you sure you want to delete this hike?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    Log.i("info", "deleted");
                                    try {
                                        mValues.remove(getAdapterPosition());
                                        JSONObject hikes = new JSONObject(storage);
                                        hikes.remove(hike_to_be_removed);
                                        StorageUtilities.create(inner, StorageUtilities.jsonStorageName, hikes.toString());
                                        update();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
