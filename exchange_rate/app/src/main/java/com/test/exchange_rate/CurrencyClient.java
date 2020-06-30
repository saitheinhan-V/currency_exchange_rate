package com.test.exchange_rate;

import com.test.exchange_rate.model.RetroCurrency;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyClient {

    @GET("/api/latest")
    Call<RetroCurrency> getAllCurrency();

}
