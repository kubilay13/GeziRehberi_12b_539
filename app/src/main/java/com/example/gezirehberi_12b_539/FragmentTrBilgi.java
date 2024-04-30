package com.example.gezirehberi_12b_539;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.gezirehberi_12b_539.Activity_Kayit;
import com.example.gezirehberi_12b_539.R;
import com.example.gezirehberi_12b_539.databinding.ActivityFragmentTrBilgiBinding;
public class FragmentTrBilgi extends Fragment {
    public static EditText yerAdi,ulkeAdi,sehirAdi,tarihce,hakkindaGiris;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_tr_bilgi, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yerAdi = view.findViewById(R.id.editText_YerAdiGir);
        ulkeAdi = view.findViewById(R.id.editText_UlkesiniGir);
        sehirAdi = view.findViewById(R.id.editText_ŞehriniGir);
        tarihce = view.findViewById(R.id.editText_HistoryInput);
        hakkindaGiris = view.findViewById(R.id.editText_HakkındaGir);
        veriEkranaGetir();
    }
    public void veriEkranaGetir(){
        if(!Activity_Kayit.yerAdiBilgi.equals("")){
            yerAdi.setText(Activity_Kayit.yerAdiBilgi);
        }
        if(!Activity_Kayit.ulkeAdiBilgi.equals("")){
            ulkeAdi.setText(Activity_Kayit.ulkeAdiBilgi);
        }
        if(!Activity_Kayit.sehirAdiBilgi.equals("")){
            sehirAdi.setText(Activity_Kayit.sehirAdiBilgi);
        }
        if(!Activity_Kayit.tarihceBilgi.equals("")){
            tarihce.setText(Activity_Kayit.tarihceBilgi);
        }
        if(!Activity_Kayit.hakkindaBilgi.equals("")){
            hakkindaGiris.setText(Activity_Kayit.hakkindaBilgi);
        }
    }
}