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
import com.solution.internet.shopping.fragments.DeliveryHomeFragment;
import com.solution.internet.shopping.fragments.HomeFragment;
import com.solution.internet.shopping.fragments.InvoiceDeliveryDetails;
import com.solution.internet.shopping.fragments.InvoiceDetails;
import com.solution.internet.shopping.fragments.UserSpecialOrderDetailsFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitRespAdapter;
import com.solution.internet.shopping.models.ModelNotification.ModelNotification;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.singleton.SingletonShopping;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.MyViewHolder> implements HandleRetrofitRespAdapter {

    private List<ModelNotification> adapterList;

    private Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNotificationTime, tvNotificationContent;

        public MyViewHolder(View view) {
            super(view);
            tvNotificationTime = view.findViewById(R.id.tvNotificationTime);
            tvNotificationContent = view.findViewById(R.id.tvNotificationContent);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final ModelNotification item = adapterList.get(getAdapterPosition());

            switch (item.getType()) {
                case "specialorder":
                    ((BaseActivity) activity).addContentFragment(UserSpecialOrderDetailsFragment.init(item.getItemid()), false);
                    break;
                case "chat":
                    SingletonShopping.getInstance().setChatUserId(item.getItemid());
                    ((BaseActivity) activity).addContentFragment(ChatFragment.init(), true);

                    break;
                case "invoice":
                    if (SharedPrefHelper.getInstance(activity).getUserType().equals("user"))
                        ((BaseActivity) activity).addContentFragment(InvoiceDetails.init(item.getItemid()), true);
                    else
                        ((BaseActivity) activity).addContentFragment(InvoiceDeliveryDetails.init(item.getItemid()), true);
                    break;
                case "alert":
                    if (SharedPrefHelper.getInstance(activity).getUserType().equals("user"))
                        ((BaseActivity) activity).addContentFragment(new HomeFragment(), true);
                    else
                        ((BaseActivity) activity).addContentFragment(new DeliveryHomeFragment(), true);
                    break;
            }
        }
    }

    public AdapterNotifications(List<ModelNotification> adapterList, Activity activity) {
        this.adapterList = adapterList;

        this.activity = activity;

        HandleCalls.getInstance(activity).setonRespnseSucessApapter(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ModelNotification items = adapterList.get(position);


        holder.tvNotificationTime.setText(items.getCreatedAt());
        holder.tvNotificationContent.setText(items.getBody());


    }


    @Override
    public int getItemCount() {
        return adapterList.size();
    }


    //region helper methods

    public void addItem(ModelNotification item) {
        insertItem(item, adapterList.size());
        notifyDataSetChanged();
    }


    public void insertItem(ModelNotification item, int position) {
        adapterList.add(position, item);
        notifyItemInserted(position);
    }


    public void clearAllListData() {
        int size = adapterList.size();
        adapterList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<ModelNotification> items) {
        clearAllListData();
        int startIndex = adapterList.size();
        adapterList.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public List<ModelNotification> getAllData() {
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

