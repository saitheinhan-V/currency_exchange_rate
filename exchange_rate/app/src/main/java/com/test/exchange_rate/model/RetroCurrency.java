package com.test.exchange_rate.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RetroCurrency {

    @SerializedName("info")
    private String info;

    @SerializedName("description")
    private  String description;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("rates")
    private Map<String , Object> rates;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public Map<String, Object> getRates() {
        return rates;
    }

    public void setRates(Map<String, Object> rates) {
        this.rates = rates;
    }
}
