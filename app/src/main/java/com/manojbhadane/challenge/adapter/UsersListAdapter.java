package com.manojbhadane.challenge.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.manojbhadane.challenge.R;
import com.manojbhadane.challenge.app.App;
import com.manojbhadane.challenge.customview.RoundedImageView;
import com.manojbhadane.challenge.listener.OnItemClickListener;
import com.manojbhadane.challenge.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manoj.bhadane on 16-12-2017.
 */
public class UsersListAdapter extends RecyclerView.Adapter implements Filterable
{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private ImageLoader mImageLoader;
    private List<UserModel> mUsersList;
    private OnItemClickListener mListener;
    private List<UserModel> mUsersListFilter;
    private ItemFilter mFilter = new ItemFilter();

    public UsersListAdapter(List<UserModel> usersList, OnItemClickListener listener)
    {
        mListener = listener;
        mUsersList = usersList;
        mUsersListFilter = usersList;
        mImageLoader = App.getInstance().getImageLoader();
    }

    @Override
    public int getItemViewType(int position)
    {
        return mUsersList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType)
    {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_users_list, parent, false);
            vh = new ViewHolder(v);
        } else
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progressbar, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        if (holder instanceof ViewHolder)
        {
            UserModel model = (UserModel) mUsersListFilter.get(position);

            mImageLoader.get(model.getAvatar(), ImageLoader.getImageListener(((ViewHolder) holder).mImgProfile, R.mipmap.ic_launcher, R.mipmap.ic_launcher));

            ((ViewHolder) holder).mImgProfile.setImageUrl(model.getAvatar(), mImageLoader);
            ((ViewHolder) holder).mTxtName.setText(model.getFirst_name() + " " + model.getLast_name());

            ((ViewHolder) holder).mTxtName.setTypeface(App.getUbuntuTypeFace());
            ((ViewHolder) holder).mTxtSubText.setTypeface(App.getUbuntuTypeFace());
            ((ViewHolder) holder).mTxtDesignation.setTypeface(App.getUbuntuTypeFace());
            ((ViewHolder) holder).mCard.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View view)
                {
                    mListener.onItemClick(position);
                }
            });
        } else
        {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount()
    {
        return mUsersListFilter.size();
    }

    @Override
    public long getItemId(int position)
    {
        return mUsersListFilter.indexOf(mUsersListFilter.get(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        protected CardView mCard;
        protected RoundedImageView mImgProfile;
        protected TextView mTxtName, mTxtSubText, mTxtDesignation;

        public ViewHolder(View v)
        {
            super(v);
            mCard = (CardView) v.findViewById(R.id.card);
            mTxtName = (TextView) v.findViewById(R.id.txt_user_name);
            mTxtSubText = (TextView) v.findViewById(R.id.txt_sub_text);
            mTxtDesignation = (TextView) v.findViewById(R.id.txt_sub_text1);
            mImgProfile = (RoundedImageView) v.findViewById(R.id.img_profile);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder
    {
        protected ProgressBar progressBar;

        public ProgressViewHolder(View v)
        {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public Filter getFilter()
    {
        return mFilter;
    }

    private class ItemFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            final List<UserModel> list = mUsersList;
            int count = list.size();
            final List<UserModel> nlist = new ArrayList<UserModel>(count);
            for (int i = 0; i < count; i++)
            {
                UserModel model = list.get(i);
                if (model != null)
                    if ((list.get(i).getFirst_name() + list.get(i).getLast_name())
                            .toLowerCase().contains(constraint.toString().toLowerCase()))
                        nlist.add(list.get(i));
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            mUsersListFilter = (ArrayList<UserModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
