package com.solution.internet.shopping.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.BaseActivity;
import com.solution.internet.shopping.fragments.ProductDetailsUserFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitRespAdapter;
import com.solution.internet.shopping.models.ModelCallDelivery.Items;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AdapterDeliveryItems extends RecyclerView.Adapter<AdapterDeliveryItems.MyViewHolder> implements HandleRetrofitRespAdapter
{

    private List<Items> adapterList;

    private Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvRvItemDelegateTitle, tvRvItemDelegatePrice,
                tvRvItemDelegateSpecialAdvert,
                tvRvItemDelegateAddress;
        ImageView imgRvItemDelegateItem, imgRvItemDelegateFavourite, imgItemDelegateEdit, imgItemDelegateDelete;
        LinearLayout llRvItemDelivery;

        public MyViewHolder(View view)
        {
            super(view);
            imgRvItemDelegateItem = view.findViewById(R.id.imgRvItemDelegateItem);
            imgRvItemDelegateFavourite = view.findViewById(R.id.imgRvItemDelegateFavourite);
            tvRvItemDelegateTitle = view.findViewById(R.id.tvRvItemDelegateTitle);
            tvRvItemDelegatePrice = view.findViewById(R.id.tvRvItemDelegatePrice);
            tvRvItemDelegateSpecialAdvert = view.findViewById(R.id.tvRvItemDelegateSpecialAdvert);
            tvRvItemDelegateAddress = view.findViewById(R.id.tvRvItemDelegateAddress);
            imgItemDelegateEdit = view.findViewById(R.id.imgItemDelegateEdit);
            imgItemDelegateDelete = view.findViewById(R.id.imgItemDelegateDelete);
            llRvItemDelivery = view.findViewById(R.id.llRvItemDelivery);

            llRvItemDelivery.setOnClickListener(this);
            imgItemDelegateEdit.setOnClickListener(this);
            imgItemDelegateDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.llRvItemDelivery:
                    ((BaseActivity) activity).addContentFragment(ProductDetailsUserFragment.init(adapterList.get(getAdapterPosition())), true);

                    break;
                case R.id.imgItemDelegateEdit:

                    break;
                case R.id.imgItemDelegateDelete:

                    break;
            }
        }
    }

    public AdapterDeliveryItems(List<Items> adapterList, Activity activity)
    {
        this.adapterList = adapterList;
        this.activity = activity;

        HandleCalls.getInstance(activity).setonRespnseSucessApapter(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_delivery_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        final Items items = adapterList.get(position);
        holder.tvRvItemDelegateTitle.setText(items.getTitle());
        holder.tvRvItemDelegatePrice.setText(items.getPrice() + " ريال ");
//        holder.tvRvItemDelegateName.setText(items.getCategoryname());


        Picasso.with(activity)
                .load(items.getPhoto())
                .into(holder.imgRvItemDelegateItem);

       /* if (modelConversationsDetails.getIslike())
            holder.imgRvItemLiterariesLike.setImageDrawable(activity.getResources().getDrawable(R.drawable.like_on));
        else
            holder.imgRvItemLiterariesLike.setImageDrawable(activity.getResources().getDrawable(R.drawable.like_off));

        if (position >= getItemCount() - 1 && onRequestMoreListener != null)
        {
            onRequestMoreListener.requestMoreData(this, position);
        }*/
    }


    @Override
    public int getItemCount()
    {
        return adapterList.size();
    }


    //region helper methods

    public void addItem(Items item)
    {
        insertItem(item, adapterList.size());
        notifyDataSetChanged();
    }


    public void insertItem(Items item, int position)
    {
        adapterList.add(position, item);
        notifyItemInserted(position);
    }


    public void clearAllListData()
    {
        int size = adapterList.size();
        adapterList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Items> items)
    {
        clearAllListData();
        int startIndex = adapterList.size();
        adapterList.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public List<Items> getAllData()
    {
        return adapterList;
    }


    //endregion

    //region call response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {

    }

    @Override
    public void onNoContent(String flag, int code)
    {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }
    //endregion

    //region calls


    //endregion


}

