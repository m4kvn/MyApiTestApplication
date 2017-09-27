package com.masahirosaito.myapitestapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.masahirosaito.myapitestapplication.model.Shop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (LinearLayout) findViewById(R.id.linear);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://54.238.225.122/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        final Shop[] shop = new Shop[1];
        Call<Shop> call = service.getShop(1);
        try {
            call.enqueue(new Callback<Shop>() {
                @Override
                public void onResponse(Call<Shop> call, Response<Shop> response) {
//                    Log.d("MainActivity", response.body().toString());
                    shop[0] = response.body();
                    Log.d("MainActivity", shop[0].toString());
                }

                @Override
                public void onFailure(Call<Shop> call, Throwable t) {
                    Log.d("MainActivity", t.getMessage());
                    shop[0] = new Shop(-1, "dummy", 0, 0);
                    Log.d("MainActivity", shop[0].toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ArrayList<Shop> shops = new ArrayList<>();
        Call<List<Shop>> call2 = service.getShops();
        try {
            call2.enqueue(new Callback<List<Shop>>() {
                @Override
                public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
//                    Log.d("MainActivity", response.body().toString());
                    shops.addAll(response.body());
                    Log.d("MainActivity", shops.toString());

                    updateContainer(shops);
                }

                @Override
                public void onFailure(Call<List<Shop>> call, Throwable t) {
                    Log.d("MainActivity", t.getMessage());
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    shops.add(new Shop(-1, "dummy", 0, 0));
                    Log.d("MainActivity", shops.toString());

                    updateContainer(shops);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateContainer(ArrayList<Shop> shops) {
        for (Shop shop : shops) {
            TextView text = (TextView) getLayoutInflater().inflate(R.layout.my_text_view, null);
            text.setText(shop.getName());
            container.addView(text);
        }
    }
}
