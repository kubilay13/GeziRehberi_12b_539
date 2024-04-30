package com.example.gezirehberi_12b_539;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gezirehberi_12b_539.databinding.ActivityGeziRehberimBinding;
import com.squareup.picasso.Picasso;

public class GeziRehberim extends AppCompatActivity {
    private ActivityGeziRehberimBinding activityGeziRehberiBinding;
      int dilSecimi;
    GeziRehberiBilgileri geziRehberBilgileri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGeziRehberiBinding = ActivityGeziRehberimBinding.inflate(getLayoutInflater());
        View view = activityGeziRehberiBinding.getRoot();
        setContentView(view);
        Intent intent = getIntent();
        geziRehberBilgileri = (GeziRehberiBilgileri) intent.getSerializableExtra("geziYerleriDetay");
        dilSecimi = intent.getIntExtra("dilSecimi", 0);
        verileriAl();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.gezi_menu, menu);
        if (dilSecimi == 0) {
            menu.getItem(0).setTitle("English");
        } else {
            menu.getItem(0).setTitle("Türkçe");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.button_geri1) {
            finish();
            Intent intent = new Intent(GeziRehberim.this, MainActivity.class);
            intent.putExtra("dilSecimi", dilSecimi);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.secim) {
            dilSecimi = (dilSecimi == 0) ? 1 : 0;
            item.setTitle((dilSecimi == 0) ? "English" : "Türkçe");
            verileriAl();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void verileriAl() {
        Picasso.get().load(geziRehberBilgileri.imageID).into(activityGeziRehberiBinding.imageViewGorsel);
        if (dilSecimi == 0) {
            activityGeziRehberiBinding.textViewYerBilgi.setText(geziRehberBilgileri.yerAdi);
            activityGeziRehberiBinding.textView1UlkeBilgisi.setText(geziRehberBilgileri.ulkeAdi);
            activityGeziRehberiBinding.textView2SehirBilgisi.setText(geziRehberBilgileri.sehirAdi);
            activityGeziRehberiBinding.textView3TarihBilgisi.setText(geziRehberBilgileri.tarihce);
            activityGeziRehberiBinding.textView4HakkNda.setText(geziRehberBilgileri.hakkinda);
        } else {
            activityGeziRehberiBinding.textViewYerBilgi.setText(geziRehberBilgileri.placeName);
            activityGeziRehberiBinding.textView1UlkeBilgisi.setText(geziRehberBilgileri.countryName);
            activityGeziRehberiBinding.textView2SehirBilgisi.setText(geziRehberBilgileri.cityName);
            activityGeziRehberiBinding.textView3TarihBilgisi.setText(geziRehberBilgileri.history);
            activityGeziRehberiBinding.textView4HakkNda.setText(geziRehberBilgileri.about);
        }
    }
}