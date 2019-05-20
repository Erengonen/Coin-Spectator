package com.example.crypto;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;




public class PriceReq {


    private String apiKey = "11b4007c6c7a7f84682b6519c51cefea9ec578f13008878a02e669ee9d6d2052";



    TextView _high, _low, _price, _open, _lastUpdate,_nameCoin;

    public PriceReq(TextView _high, TextView _low, TextView _price, TextView _open, TextView _lastUpdate, TextView nameCoin ) {
        this._high = _high;
        this._low = _low;
        this._price = _price;
        this._open = _open;
        this._lastUpdate = _lastUpdate;
        this._nameCoin= _nameCoin;


    }
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }

    public void PriceCall(Context contex, final String coin,final String currencyReq) {


        String URL = "https://min-api.cryptocompare.com/data/top/exchanges/full?fsym="+coin+"&tsym="+currencyReq+"&api_key="+apiKey;

        Currency currency = Currency.getInstance("USD");
        final String symbol = currency.getSymbol(Locale.US);
        //String a =
//        json_Parser = new JSONParser(_usd);

        RequestQueue requestQueue = Volley.newRequestQueue(contex);



        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                //Log.d("Main",response.toString());}


                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String yourFormattedString = formatter.format(100000);


                    try {
                        JSONObject Data = response.getJSONObject("Data");
                        JSONObject AggregatedData = Data.getJSONObject("AggregatedData");






                try {

                            String Price = AggregatedData.getString("PRICE");
                            currencyFormat(Price);
                            String formatPrice = formatter.format(Math.round(Float.valueOf(Price)));

                            _price.setText("Price : "+'\n'+ currencyFormat(Price)+symbol);


                        } catch (Error e) {
                            _price.setText("Data Not Avvaliable");

                        }


                    try {

                        String Open = AggregatedData.getString("OPENDAY");
                        String formatOpen = formatter.format(Math.round(Float.valueOf(Open)));
                        _open.setText("Open : "+'\n'+currencyFormat(Open)+symbol);


                    } catch (Error e) {
                        _open.setText("Data Not Avvaliable");

                    }

                    try {
                        String Low = AggregatedData.getString("LOWDAY");
                        String formatLow = formatter.format(Math.round(Float.valueOf(Low)));
                        _low.setText("Low : "+'\n'+ currencyFormat(Low)+symbol);


                    } catch (Error e) {
                        _low.setText("Data Not Avvaliable");

                    }

                    try {
                        String High = AggregatedData.getString("HIGHDAY");
                        String formatHigh = formatter.format(Math.round(Float.valueOf(High)));
                        _high.setText("High :"+'\n'+ currencyFormat(High)+symbol);


                    } catch (Error e) {
                        _high.setText("Data Not Avvaliable");

                    }




                    try {
                        String LastUpdate = AggregatedData.getString("LASTUPDATE");
                        String convert = unix_time(Long.parseLong(LastUpdate));
                        _lastUpdate.setText("Last Update :" + convert);
                    } catch (Error e) {
                        _lastUpdate.setText("Data Not Avvaliable");

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }



    public String unix_time(Long timestamp) {

        Date date = new Date(timestamp * 1000L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        simpleDateFormat.setTimeZone(TimeZone.getDefault());

        String formatteDate = simpleDateFormat.format(date);

        SimpleDateFormat _24HoursSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HoursSDF = new SimpleDateFormat("hh:mm a");

        Date _24HourDt = null;

        try {
            _24HourDt = _24HoursSDF.parse(formatteDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _12HoursSDF.format(_24HourDt);
    }



    }





