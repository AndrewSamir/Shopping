package com.solution.internet.shopping.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.BaseActivity;
import com.solution.internet.shopping.fragments.InvoiceDetails;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterChatMessage extends RecyclerView.Adapter<AdapterChatMessage.MyViewHolder> {

    private List<ModelChatMessage> adapterList;
    Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvChatMsg, tvChatMsgTime;
        CircleImageView imgChatMsg;
        ImageView imgChatPhoto;

        public MyViewHolder(View view) {
            super(view);
            tvChatMsg = view.findViewById(R.id.tvChatMsg);
            tvChatMsgTime = view.findViewById(R.id.tvChatMsgTime);
            imgChatMsg = view.findViewById(R.id.imgChatMsg);
            imgChatPhoto = view.findViewById(R.id.imgChatPhoto);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ModelChatMessage messagesChat = adapterList.get(getAdapterPosition());
            if (messagesChat.getType().equals("invoice")) {
                if (SharedPrefHelper.getInstance(activity).getUserType().equals("user"))
                    ((BaseActivity) activity).addContentFragment(InvoiceDetails.init(messagesChat.getCrId()), true);
                else
                    ((BaseActivity) activity).addContentFragment(InvoiceDetails.init(messagesChat.getCrId()), true);
            }

        }
    }


    public AdapterChatMessage(List<ModelChatMessage> ModelChatMessageTests, Activity activity) {
        this.adapterList = ModelChatMessageTests;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutRes = 0;
        switch (viewType) {
            case 1:
                layoutRes = R.layout.xml_chat_sent;
                break;
            case 0:
                layoutRes = R.layout.xml_chat_received;
                break;

            case 4:
                layoutRes = R.layout.xml_photo_sent;
                break;
            case 3:
                layoutRes = R.layout.xml_photo_received;
                break;
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelChatMessage messagesChat = adapterList.get(position);

        if (messagesChat.getType().equals("text"))
            holder.tvChatMsg.setText(messagesChat.getReply());

        else if (messagesChat.getType().equals("photo"))
            Picasso.with(activity)
                    .load(messagesChat.getReply())
                    .placeholder(activity.getResources().getDrawable(R.mipmap.ic_launcher))
                    .into(holder.imgChatPhoto);
        else
            Picasso.with(activity)
                    .load("zdcxads iads l ")
                    .placeholder(activity.getResources().getDrawable(R.mipmap.ic_launcher))
                    .error(activity.getResources().getDrawable(R.mipmap.ic_launcher))
                    .into(holder.imgChatPhoto);

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        String dateString = formatter.format(new Date(Long.parseLong(messagesChat.getTime() + "000")));
        holder.tvChatMsgTime.setText(dateString);

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int sender;

        if (adapterList.get(position).getUserid() == SharedPrefHelper.getInstance(activity).getUserid()) {

            if (adapterList.get(position).getType().equals("text"))
                sender = 1;

            else if (adapterList.get(position).getType().equals("photo"))
                sender = 4;
            else
                sender = 4;
        } else {
            if (adapterList.get(position).getType().equals("text"))
                sender = 0;

            else if (adapterList.get(position).getType().equals("photo"))
                sender = 3;
            else
                sender = 3;
        }

        return sender;
    }

    public void addAll(List<ModelChatMessage> items) {
        int startIndex = adapterList.size();
        adapterList.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());

    }

    public void addItem(ModelChatMessage item) {
        insertItem(item, adapterList.size());
        notifyDataSetChanged();
    }

    public void insertItem(ModelChatMessage item, int position) {
        adapterList.add(position, item);
        notifyItemInserted(position);
    }

}

