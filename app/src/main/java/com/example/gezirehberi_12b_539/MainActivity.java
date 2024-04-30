package com.example.gezirehberi_12b_539;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gezirehberi_12b_539.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    Map<String, Object> mapData; //Çekilecek veriler bu Map'te tutulur.
    FirebaseFirestore FirebaseFirestore;
    ArrayList<GeziRehberiBilgileri> yerler;
    int dilSecimi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        yerler = new ArrayList<>();
        FirebaseFirestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        dilSecimi = intent.getIntExtra("dilSecimi", 0);
        verileriAlTr();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gezi_menu, menu);
        if (dilSecimi == 0) {
            menu.getItem(3).setTitle("English");
        } else {
            menu.getItem(3).setTitle("Türkçe");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.girisYap) {
        } else if (item.getItemId() == R.id.geziEkle) {
            Intent intent = new Intent(MainActivity.this, Activity_Kayit.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.geziSil) {
        } else if (item.getItemId() == R.id.dilSecimi) {
            if (dilSecimi == 0) {
                dilSecimi = 1;
                item.setTitle("Türkçe");
                verileriAlTr();
            } else {
                dilSecimi = 0;
                item.setTitle("English");
                verileriAlTr();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void verileriAlTr() {
        yerler.clear();
        FirebaseFirestore.collection("YerKayit").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        mapData = snapshot.getData();
                        String yerAdi = (String) mapData.get("adi");
                        String ulkeAdi = (String) mapData.get("ulkesi");
                        String sehirAdi = (String) mapData.get("sehir");
                        String tarihce = (String) mapData.get("tarihce");
                        String hakkinda = (String) mapData.get("hakkinda");
                        String placeName = (String) mapData.get("placeName");
                        String countryName = (String) mapData.get("countryName");
                        String cityName = (String) mapData.get("cityName");
                        String history = (String) mapData.get("historyInfo");
                        String about = (String) mapData.get("aboutInfo");
                        String imageURL = (String) mapData.get("gorselURL");
                        GeziRehberiBilgileri geziRehberBilgileri = new GeziRehberiBilgileri(imageURL, yerAdi, ulkeAdi, sehirAdi, tarihce, hakkinda, placeName, countryName, cityName, history, about);
                        yerler.add(geziRehberBilgileri);
                    }

                    ArrayAdapter<String> arrayAdapter = null;
                    if (dilSecimi == 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, yerler.stream().map(yer -> yer.yerAdi).collect(Collectors.toList()));
                        }
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, yerler.stream().map(yer -> yer.placeName).collect(Collectors.toList()));
                        }
                    }
                    activityMainBinding.ListViewYerler.setAdapter(arrayAdapter);

                    //ListViewdeki yerlerden birine tıklandığında ne yapılacağının yazıldığı bölümdür.
                    activityMainBinding.ListViewYerler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            finish();
                            Intent intentToDetail = new Intent(MainActivity.this, GeziRehberim.class);
                            intentToDetail.putExtra("geziYerleriDetay", yerler.get(i));
                            intentToDetail.putExtra("dilSecimi", dilSecimi);
                            startActivity(intentToDetail);
                        }
                    });
                }
            }
        });
    }
}
