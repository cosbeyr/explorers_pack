package com.WWU.explorerspack.ui.guide.L2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.WWU.explorerspack.R;
import com.WWU.explorerspack.ui.guide.JSONManager;
import com.WWU.explorerspack.ui.guide.L2.SubChapterData.SubChapterContent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySectionRecyclerViewAdapter extends RecyclerView.Adapter <MySectionRecyclerViewAdapter.ViewHolder>{

    private final List<SubChapterContent.SubChapterItem> mValues;
    private final ChapterPageFragment.OnSubListFragmentInteractionListener mListener;
    private final Context mContext;
    private ArrayList<SubChapterContent.SubChapterItem> removedItems = new ArrayList<>();
    private HashMap<String, Integer> indexMap = new HashMap<>();

    public MySectionRecyclerViewAdapter(List<SubChapterContent.SubChapterItem> items, ChapterPageFragment.OnSubListFragmentInteractionListener listener, Context context){
        mValues = items;
        mListener = listener;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_tiles,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).subChapter);
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(null != mListener){
                    mListener.onSubListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void search(String searchValue){
        returnItems();
        for(SubChapterContent.SubChapterItem item: mValues){
            if(item != null) {
                String subchapterContent = JSONManager.getInstance(null).getSubChapter(item.chapter, item.subChapter);
                int position = subchapterContent.indexOf(searchValue);
                if(!item.subChapter.trim().toLowerCase().startsWith(searchValue) && position == -1){
                    removedItems.add(item);
                    indexMap.put(item.subChapter,mValues.indexOf(item));

                }
            }
        }
        for(SubChapterContent.SubChapterItem item: removedItems){
            mValues.remove(item);
        }
        update();
    }


    public void returnItems(){
        while(!removedItems.isEmpty()){
            SubChapterContent.SubChapterItem item = removedItems.remove(0);
            if(item != null){
                try{
                    mValues.add(indexMap.get(item.subChapter),item);
                    indexMap.remove(item.subChapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        update();

    }

    private void update(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

//    @Override
//    public void onBindViewHolder(final MyChaptersRecyclerViewAdapter.ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        //holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
//        String iconName = holder.mItem.content + ".png";
//
//        ColorMatrix matrix = new ColorMatrix();
//        matrix.setSaturation(0);
//
//        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//
//
//        try {
//            holder.mImageView.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open(iconName)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        holder.mImageView.setColorFilter(filter);
//
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       public final View mView;
       //public final ImageView mImageView;
       public final TextView mContentView;
       public SubChapterContent.SubChapterItem mItem;

       public ViewHolder(View view) {
           super(view);
           mView = view;
           //mImageView = (ImageView) view.findViewById(R.id.item_image);
           mContentView = (TextView) view.findViewById(R.id.section);
       }

       @Override
       public String toString() {
           return super.toString() + " '" + mContentView.getText() + "'";
       }
   }
}
