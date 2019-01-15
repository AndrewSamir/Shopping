package com.solution.internet.shopping.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.BaseActivity;
import com.solution.internet.shopping.fragments.ChatFragment;
import com.solution.internet.shopping.fragments.ProductDetailsUserFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitRespAdapter;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.singleton.SingletonShopping;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterInbox extends RecyclerView.Adapter<AdapterInbox.MyViewHolder> implements HandleRetrofitRespAdapter {

    private List<ModelChatMessage> adapterList;

    private Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvRvItemMessageTime, tvRvItemMessageName,
                tvRvItemMessage;

        CircleImageView imhRvItemMessageImg;

        public MyViewHolder(View view) {
            super(view);
            tvRvItemMessageTime = view.findViewById(R.id.tvRvItemMessageTime);
            imhRvItemMessageImg = view.findViewById(R.id.imhRvItemMessageImg);
            tvRvItemMessageName = view.findViewById(R.id.tvRvItemMessageName);
            tvRvItemMessage = view.findViewById(R.id.tvRvItemMessage);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SingletonShopping.getInstance().setC_id(adapterList.get(getAdapterPosition()).getCId());
            SingletonShopping.getInstance().setChatUserId(adapterList.get(getAdapterPosition()).getUserid());
            SingletonShopping.getInstance().setCr_id(adapterList.get(getAdapterPosition()).getCrId());
            ((BaseActivity) activity).addContentFragment(ChatFragment.init(), true);
           /* Intent intent = new Intent(activity, ProductDetailsActivity.class);
            intent.putExtra("test", (Serializable) adapterList.get(getAdapterPosition()));
            activity.startActivity(intent);*/
//            ((BaseActivity) activity).addContentFragment(ProductDetailsUserFragment.init(adapterList.get(getAdapterPosition())), true);
        }
    }

    public AdapterInbox(List<ModelChatMessage> adapterList, Activity activity) {
        this.adapterList = adapterList;

        this.activity = activity;

        HandleCalls.getInstance(activity).setonRespnseSucessApapter(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_message, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelChatMessage items = adapterList.get(position);

       /* SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy | hh:mm a", Locale.ENGLISH);
        String dateString = formatter.format(new Date(Long.parseLong(items.getTime() + "")));
*/
        Locale locale = new Locale("en");

        SimpleDateFormat formatter = new SimpleDateFormat("DD/MM/YY | hh:mm aa", locale);
        long dateTime = Long.parseLong(items.getTime()+"000");
        String dateString = formatter.format(new Date(dateTime));

        holder.tvRvItemMessageTime.setText(dateString);
        holder.tvRvItemMessageName.setText(items.getFullname());
        holder.tvRvItemMessage.setText(items.getReply());
//        holder.tvRvItemDelegateName.setText(items.getCategoryname());


/*
        Picasso.with(activity)
                .load(items.getPhoto())
                .into(holder.imhRvItemMessageImg);
*/

    }


    @Override
    public int getItemCount() {
        return adapterList.size();
    }


    //region helper methods

    public void addItem(ModelChatMessage item) {
        insertItem(item, adapterList.size());
        notifyDataSetChanged();
    }


    public void insertItem(ModelChatMessage item, int position) {
        adapterList.add(position, item);
        notifyItemInserted(position);
    }


    public void clearAllListData() {
        int size = adapterList.size();
        adapterList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<ModelChatMessage> items) {
        clearAllListData();
        int startIndex = adapterList.size();
        adapterList.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public List<ModelChatMessage> getAllData() {
        return adapterList;
    }


    //endregion

    //region call response
    @Override
    public void onResponseSuccess(String flag, Object o) {

    }

    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }
    //endregion

    //region calls


    //endregion

}

