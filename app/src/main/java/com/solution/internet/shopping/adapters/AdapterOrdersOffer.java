package com.solution.internet.shopping.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.internet.shopping.R;
import com.solution.internet.shopping.activities.BaseActivity;
import com.solution.internet.shopping.fragments.AcceptOrRejectOfferFragment;
import com.solution.internet.shopping.fragments.UserSpecialOrderDetailsFragment;
import com.solution.internet.shopping.interfaces.HandleRetrofitRespAdapter;
import com.solution.internet.shopping.models.ModelPrivateOrderInfo.Offers;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterOrdersOffer extends RecyclerView.Adapter<AdapterOrdersOffer.MyViewHolder> implements HandleRetrofitRespAdapter
{

    private List<Offers> adapterList;

    private Activity activity;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvInvoiceDate, tvInvoicePrice,
                tvInvoiceName;

        CircleImageView imgInvoice;

        public MyViewHolder(View view)
        {
            super(view);
            tvInvoiceDate = view.findViewById(R.id.tvInvoiceDate);
            imgInvoice = view.findViewById(R.id.imgInvoice);
            tvInvoiceName = view.findViewById(R.id.tvInvoiceName);
            tvInvoicePrice = view.findViewById(R.id.tvInvoicePrice);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            ((BaseActivity) activity).addContentFragment(AcceptOrRejectOfferFragment.init(adapterList.get(getAdapterPosition())), true);
        }
    }

    public AdapterOrdersOffer(List<Offers> adapterList, Activity activity)
    {
        this.adapterList = adapterList;

        this.activity = activity;

        HandleCalls.getInstance(activity).setonRespnseSucessApapter(this);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_special_order_offer, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        final Offers items = adapterList.get(position);

       /* SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy | hh:mm a", Locale.ENGLISH);
        String dateString = formatter.format(new Date(Long.parseLong(items.getTime() + "")));
*/

        holder.tvInvoiceDate.setText(items.getCreatedAt());
        holder.tvInvoiceName.setText(items.getFullname());
        holder.tvInvoicePrice.setText(items.getPrice() + " ريال ");
//        holder.tvRvItemDelegateName.setText(items.getCategoryname());


    }


    @Override
    public int getItemCount()
    {
        return adapterList.size();
    }


    //region helper methods

    public void addItem(Offers item)
    {
        insertItem(item, adapterList.size());
        notifyDataSetChanged();
    }


    public void insertItem(Offers item, int position)
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

    public void addAll(List<Offers> items)
    {
        clearAllListData();
        int startIndex = adapterList.size();
        adapterList.addAll(items);
        notifyItemRangeInserted(startIndex, items.size());
    }

    public List<Offers> getAllData()
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

