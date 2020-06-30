package com.test.exchange_rate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.exchange_rate.MainActivity;
import com.test.exchange_rate.R;
import com.test.exchange_rate.model.CurrencyRate;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private MainActivity mainActivity;
    private List<CurrencyRate> currencyRateList;

    public CurrencyAdapter(MainActivity mContext, List<CurrencyRate> currencyRateListList) {
        this.mainActivity = mContext;
        this.currencyRateList = currencyRateListList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvType;
        public TextView tvRate;
        public CurrencyRate currencyRate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvType = itemView.findViewById(R.id.tv_type);
            tvRate = itemView.findViewById(R.id.tv_rate);
        }

        public void bindData(CurrencyRate currencyRate) {

            this.currencyRate= currencyRate;
            tvType.setText(currencyRate.getType());

            String rate = currencyRate.getValue() + " က်ပ္";
            tvRate.setText(rate);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final CurrencyRate currencyRate = currencyRateList.get(position);
        holder.bindData(currencyRate);
    }


    @Override
    public int getItemCount() {
        return currencyRateList.size();
    }


}
