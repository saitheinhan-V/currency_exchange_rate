package com.test.exchange_rate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.test.exchange_rate.adapters.CurrencyAdapter;
import com.test.exchange_rate.api.RetrofitClientApi;
import com.test.exchange_rate.dbHandler.CurrencyRateDBHandler;
import com.test.exchange_rate.model.CurrencyRate;
import com.google.gson.Gson;
import com.test.exchange_rate.model.RetroCurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public CurrencyRateDBHandler currencyRateDBHandler;
    public RecyclerView recyclerView;
    public CurrencyAdapter adapter;

    List<CurrencyRate> currencyRateList= new ArrayList<>();

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.show();

        recyclerView=findViewById(R.id.recycler_view);
        adapter = new CurrencyAdapter(MainActivity.this, currencyRateList);
        currencyRateDBHandler=new CurrencyRateDBHandler(this);

        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //check if database is empty or not
        //if empty - insert data for the first time
        //else just display with recycler view
        List<CurrencyRate> rateList = currencyRateDBHandler.getCurrencyRate();
        if( rateList.size() == 0){

            CurrencyClient apiService = RetrofitClientApi.getRetrofitInstance().create(CurrencyClient.class);

            Call<RetroCurrency> call = apiService.getAllCurrency();
            call.enqueue(new Callback<RetroCurrency>() {
                @Override
                public void onResponse(Call<RetroCurrency> call, Response<RetroCurrency> response) {

                    if(response.isSuccessful()){

                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                        Map map = response.body().getRates();

                        Object[] key=map.keySet().toArray();
                        Object[] value= map.values().toArray();

                        //save data for the first time
                        for(int row=0; row<key.length; row++){
                            CurrencyRate currencyRate=new CurrencyRate(row,key[row].toString(),value[row].toString());
                            currencyRateDBHandler.addCurrencyRate(currencyRate);
                        }

                        //display in recycler view
                        prepareCurrencyRate();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.toString() );
                    // Log error here since request failed
                }
            });
        }
        else{
            //display in recycler view
            prepareCurrencyRate();
        }


    }

    private void prepareCurrencyRate() {

        currencyRateList.clear();

        List<CurrencyRate> currencyRateLists=currencyRateDBHandler.getCurrencyRate();
        for(CurrencyRate currency_rate: currencyRateLists){

            currencyRateList.add(currency_rate);
            adapter.notifyDataSetChanged();

        }

        Log.d("Length===", String.valueOf(currencyRateList.size()));
        progressDialog.dismiss();
    }
}