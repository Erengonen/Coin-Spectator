package com.example.crypto;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;


public class LineGraph extends AppCompatActivity  {
    private String apiKey = "11b4007c6c7a7f84682b6519c51cefea9ec578f13008878a02e669ee9d6d2052";
    Context context;
    LineChart lineChart;
    String currencyReq;
    private static DecimalFormat mFormat;



    public LineGraph(LineChart lineChart){
        this.lineChart=lineChart;

    }
    public void _lineGraph(Context context,String coin,int b,String currencyReq){

        String saatlikVeri = "histohour";
        String dakikalikVeri = "histominute";

        List<Integer> veriSec = new ArrayList<Integer>();
        veriSec.add(0,60);
        veriSec.add(1,150);
        veriSec.add(2,300);

        veriSec.add(3,720);
        veriSec.add(4,1140);








        final List<Entry> entries = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String URL = "https://min-api.cryptocompare.com/data/"+dakikalikVeri+"?fsym="+coin+"&tsym="+currencyReq+"&limit="+veriSec.get(b)+"&aggregate=1&e=CCCAGG&api_key="+apiKey;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("Data");

                    float _close[] = new float [jsonArray.length()];
                    String _date [] = new String [jsonArray.length()];

                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                     String a = jsonObject.getString("close");


                     _close[i]=Float.valueOf(a);
                     _date[i] =jsonObject.getString("time");

                        Log.d("Main",String.valueOf(_close[i]));

                       entries.add(new Entry(Float.valueOf(_date[i]),_close[i]));




                    }
                    DisplayGraph(entries);


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
    public void MyValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal

    }


    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return mFormat.format(value) + " $"; // e.g. append a dollar-sign
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void DisplayGraph (List<Entry> entries) {

            LineDataSet lineDataSet = new LineDataSet(entries, "");
            final LineData lineData = new LineData(lineDataSet);

            lineDataSet.setColor(Color.rgb(27,156,252));

            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            lineDataSet.setCircleColor(Color.rgb(27, 156, 252));
            //lineDataSet.setCubicIntensity(0.2f);

            lineChart.getDescription().setText("Time");
            lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    lineChart.getDescription().setText("Time: "+unix_time((long)e.getX()));
                }

                @Override
                public void onNothingSelected() {



                }
            });
            lineChart.setData(lineData);
            lineChart.setDrawGridBackground(false);
            lineChart.setBackgroundColor(Color.TRANSPARENT);
            lineChart.setDrawBorders(false);


            lineChart.getAxisLeft().setDrawGridLines(false);
            lineChart.setTouchEnabled(true);

            lineChart.setScaleEnabled(true);

            lineChart.animateX(1000);
            lineChart.getAxisRight().setDrawGridLines(false);
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setEnabled(false);


            lineChart.getDescription().setTextSize(15);
            lineChart.getLegend().setEnabled(true);
            YAxis yAxis = lineChart.getAxisRight();
            yAxis.setAxisLineColor(Color.rgb(51, 204, 204));
            yAxis.setTextColor(Color.rgb(102, 153, 255));

            YAxis yAxis1 = lineChart.getAxisLeft();



            yAxis.setEnabled(true);
            yAxis1.setEnabled(false);
            lineDataSet.setCubicIntensity(0.05f);
            lineChart.setPinchZoom(false);
            lineChart.setDragDecelerationFrictionCoef(0);
            lineChart.setMaxVisibleValueCount(8);

            lineChart.getDefaultValueFormatter();
            //lineChart.valuesToHighlight();

            lineData.setValueTextColor(Color.BLUE);
            lineData.setValueTextSize(12);
            lineData.getYMin();
            lineData.getXMin();



            lineChart.setDragEnabled(true);

            lineChart.invalidate();


            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillColor(Color.CYAN);
            lineDataSet.setDrawCircles(false);

            lineDataSet.setLineWidth(4f);

            //lineDataSet.setDrawCircleHole(false);
            lineDataSet.setCircleRadius(5f);
            lineDataSet.setCircleHoleRadius(2.5f);

            //lineDataSet.setFormLineWidth(6f);
            //lineDataSet.setDrawValues(true);





        }

    public String unix_time (Long timeStamp){
        Date date = new Date(timeStamp * 1000L);

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

