package com.beytullahzengin.qrbarkodescanner;

import java.io.Serializable;


public class Item_Model implements Serializable {


    private String result, date_time;

    public Item_Model(String result, String date_time) {
        this.result = result;
        this.date_time = date_time;
    }

    public String getResult() {
        return result;
    }

    public String getDate_time() {
        return date_time;
    }
}
