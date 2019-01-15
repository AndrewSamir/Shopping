package com.solution.internet.shopping.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.utlities.SharedPrefHelper;

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

        public MyViewHolder(View view) {
            super(view);
            tvChatMsg = view.findViewById(R.id.tvChatMsg);
            tvChatMsgTime = view.findViewById(R.id.tvChatMsgTime);
            imgChatMsg = view.findViewById(R.id.imgChatMsg);

            imgChatMsg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          /*  if (SharedPrefHelper.getInstance(activity).getUserid().equals(adapterList.get(getAdapterPosition()).getSenderId()))
                ((BaseActivity) activity).addContentFragment(MoreFragment.init(), false);
            else
                ((BaseActivity) activity).addContentFragment(ProfileFragment.init(adapterList.get(getAdapterPosition()).getSenderId()), true);
*/
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
        }

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelChatMessage messagesChat = adapterList.get(position);

        holder.tvChatMsg.setText(messagesChat.getReply());
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

        if (adapterList.get(position).getUserid() == SharedPrefHelper.getInstance(activity).getUserid())
            sender = 1;
        else
            sender = 0;

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

