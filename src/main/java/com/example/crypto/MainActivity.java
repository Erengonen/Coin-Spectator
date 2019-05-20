package com.example.crypto;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {


    TextView _high, _low, _price, _open, _lastUpdate,_nameCoin;
    PriceReq priceReq;
    LineGraph lineGraph;
    LineChart lineChart;
    public String coin;
    public String text;
    int b;
    private ImageView img;
    String currency;
    private NetworkChangeReceiver receiver;//Network dinleyen receiver objemizin referans˝

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        _high=findViewById(R.id.high);
        _low=findViewById(R.id.low);
        _price=findViewById(R.id.price);
        _open=findViewById(R.id.open);
        _lastUpdate=findViewById(R.id.update);
        _nameCoin=findViewById(R.id.coinName);
        lineChart = findViewById(R.id.LineChart);
        img=findViewById(R.id.image_coin);





        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lineGraph=new LineGraph(lineChart);
        priceReq=new PriceReq(_high,_low,_price,_open,_lastUpdate,_nameCoin);
        coin="BTC";
        currency="USD";
        priceReq.PriceCall(this,coin,currency);


        //Receiverımızı register ediyoruz
        //Yani Çalıştırıyoruz

        connecting();
    }


    @Override
    protected void onDestroy() { //Activity Kapatıldığı zaman receiver durduralacak.Uygulama arka plana alındığı zamanda receiver çalışmaya devam eder

        super.onDestroy();

        unregisterReceiver(receiver);//receiver durduruluyor

    }

    public void connecting (){

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings: {

                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);


            }
            break;

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int a;
        switch (item.getItemId()) {

            case R.id.BTC: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "BTC",currency);
                coin = "BTC";
                _nameCoin.setText(getString(R.string.bitcoin));
                img.setImageResource(R.drawable.ic_btc);
                lineGraph._lineGraph(MainActivity.this, "BTC", b,currency);


            }
            break;

            case R.id.ETH: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "ETH",currency);
                coin = "ETH";
                _nameCoin.setText(getString(R.string.ethereum));
                img.setImageResource(R.drawable.ic_etc);
                lineGraph._lineGraph(MainActivity.this, "ETH", b,currency);
            }
            break;
            case R.id.BCH: {
               connecting();

                priceReq.PriceCall(MainActivity.this, "BCH",currency);
                coin = "BCH";
                _nameCoin.setText(R.string.bch);
                img.setImageResource(R.drawable.ic_bch);
                lineGraph._lineGraph(MainActivity.this, "BCH", b,currency);

            }
            break;
            case R.id.LTC: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "LTC",currency);
                coin = "LTC";
                _nameCoin.setText(R.string.ltc);
                img.setImageResource(R.drawable.ic_ltc);
                lineGraph._lineGraph(MainActivity.this, "LTC", b,currency);
            }
            break;
            case R.id.EOS: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "EOS",currency);
                coin = "EOS";
                _nameCoin.setText(R.string.eos);
                img.setImageResource(R.drawable.ic_eos);
                lineGraph._lineGraph(MainActivity.this, "EOS", b,currency);
            }
            break;
            case R.id.BNB: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "BNB",currency);
                coin = "BNB";
                _nameCoin.setText(R.string.binance_coin);
                img.setImageResource(R.drawable.ic_bnb);
                lineGraph._lineGraph(MainActivity.this, "BNB", b,currency);
            }
            break;
            case R.id.NEO: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "NEO",currency);
                coin = "NEO";
                _nameCoin.setText(R.string.neo);
                img.setImageResource(R.drawable.ic_neo);
                lineGraph._lineGraph(MainActivity.this, "NEO", b,currency);
            }
            break;
            case R.id.ZCASH: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "ZEC",currency);
                coin = "ZEC";
                _nameCoin.setText(R.string.zcash);
                img.setImageResource(R.drawable.ic_zzcash);
                lineGraph._lineGraph(MainActivity.this, "ZEC", b,currency);
            }
            break;
            case R.id.DASH: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "DASH",currency);
                coin = "DASH";
                _nameCoin.setText(R.string.dash);
                img.setImageResource(R.drawable.ic_dash);
                lineGraph._lineGraph(MainActivity.this, "DASH", b,currency);
            }
            break;
            case R.id.XMR: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "XMR",currency);
                coin = "XMR";
                _nameCoin.setText(R.string.xmr);
                img.setImageResource(R.drawable.ic_monero);
                lineGraph._lineGraph(MainActivity.this, "XMR", b,currency);
            }
            break;
            case R.id.bitcoin_gold: {
                connecting();
                priceReq.PriceCall(MainActivity.this, "BTG",currency);
                coin = "BTG";
                _nameCoin.setText(R.string.bitcon_gold);
                img.setImageResource(R.drawable.ic_bitcoin_gold);
                lineGraph._lineGraph(MainActivity.this, "BTG", b,currency);
            }
            break;
            case R.id.BSV: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "BSV",currency);
                coin = "BSV";
                _nameCoin.setText(R.string.bitcoin_sv);
                img.setImageResource(R.drawable.ic_bsvs);
                lineGraph._lineGraph(MainActivity.this, "BSV", b,currency);
            }
            break;
            case R.id.XTZ: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "XTZ",currency);
                coin = "XTZ";
                _nameCoin.setText(R.string.tezos);
                img.setImageResource(R.drawable.ic_xtz);
                lineGraph._lineGraph(MainActivity.this, "XTZ", b,currency);
            }
            break;
            case R.id.tether: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "USDT",currency);
                coin = "USDT";
                _nameCoin.setText(R.string.tether);
                img.setImageResource(R.drawable.ic_usdt);
                lineGraph._lineGraph(MainActivity.this, "USDT", b,currency);
            }
            break;
            case R.id.TRON: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "TRX",currency);
                coin = "TRX";
                _nameCoin.setText(R.string.tron);
                img.setImageResource(R.drawable.ic_tron);
                lineGraph._lineGraph(MainActivity.this, "TRX", b,currency);
            }
            break;
            case R.id.XLM: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "XLM",currency);
                coin = "XLM";
                _nameCoin.setText(R.string.stellar);
                img.setImageResource(R.drawable.ic_xlm);
                lineGraph._lineGraph(MainActivity.this, "XLM", b,currency);
            }
            break;
            case R.id.XRP: {
                connecting();

                priceReq.PriceCall(MainActivity.this, "XRP",currency);
                coin = "XRP";
                _nameCoin.setText(R.string.xrp);
                img.setImageResource(R.drawable.ic_xrp);
                lineGraph._lineGraph(MainActivity.this, "XRP", b,currency);
            }
            break;
            case R.id.ADA: {
                connecting();
                priceReq.PriceCall(MainActivity.this, "ADA",currency);
                coin = "ADA";
                _nameCoin.setText(R.string.ada);
                img.setImageResource(R.drawable.ic_ada);
                lineGraph._lineGraph(MainActivity.this, "ADA", b,currency);
            }
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        b = parent.getSelectedItemPosition();
        //text =String.valueOf(a);
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, LineGraph.class);
        i.putExtra("gonder", text);

        lineGraph._lineGraph(MainActivity.this,coin, b,currency);

    }
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
