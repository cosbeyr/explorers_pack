package com.WWU.explorerspack.ui.guide;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Context;

import android.graphics.BitmapFactory;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;


import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.guide.GuideListFragment.OnListFragmentInteractionListener;
import com.WWU.explorerspack.ui.guide.ChapterData.ChapterContent.ChapterItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ChapterItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyChaptersRecyclerViewAdapter extends RecyclerView.Adapter<MyChaptersRecyclerViewAdapter.ViewHolder> {

    private final List<ChapterItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyChaptersRecyclerViewAdapter(List<ChapterItem> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chapters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        String iconName = holder.mItem.content + ".png";
        iconName = iconName.replaceAll("-"," ");
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);


        try {
            holder.mImageView.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open(iconName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.mImageView.setColorFilter(filter);


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
        public final ImageView mImageView;
        public final TextView mContentView;
        public ChapterItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
