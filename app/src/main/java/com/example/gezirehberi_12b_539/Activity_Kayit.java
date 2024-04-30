package com.example.gezirehberi_12b_539;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gezirehberi_12b_539.databinding.ActivityKayitBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class Activity_Kayit extends AppCompatActivity {

    FirebaseStorage FirebaseStorage;
    FirebaseFirestore FirebaseFirestore;
    StorageReference storageReference;
    private ActivityKayitBinding kayitBinding;
    private int pageNumber;
    Uri fotoBilgi; // Fotoğrafın Uri adresi tutulur.
    public static String yerAdiBilgi, ulkeAdiBilgi, sehirAdiBilgi, tarihceBilgi, hakkindaBilgi; //Tr bilgileri tutulur.
    public static String nameInfo, countryInfo, cityInfo, historyInfo, aboutInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        fotoBilgi = Uri.EMPTY;
        yerAdiBilgi = "";
        ulkeAdiBilgi = "";
        sehirAdiBilgi = "";
        tarihceBilgi = "";
        hakkindaBilgi = "";
        nameInfo = "";
        countryInfo = "";
        cityInfo = "";
        historyInfo = "";
        aboutInfo = "";
        FirebaseStorage = FirebaseStorage.getInstance();
        FirebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getReference();
        kayitBinding = ActivityKayitBinding.inflate(getLayoutInflater());
        View view = kayitBinding.getRoot();
        setContentView(view);
        pageNumber = 0;
        //Fotoğraf seçimi için ilk fragment çağrılır.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentFotoKayit fotoKayitFragment = new FragmentFotoKayit();
        fragmentTransaction.replace(R.id.constraintLayout_Sayfalar, fotoKayitFragment).commit();

        kayitBinding.buttonIleri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNumber == 0) {
                    // İlk sayfadaysak fotoğraf bilgisini alırız.
                    fotoBilgi = fotoKayitFragment.imageData;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentTrBilgi trBilgiFragment = new FragmentTrBilgi();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar, trBilgiFragment).commit();
                    kayitBinding.buttonIleri1.setText("İleri");
                } else if (pageNumber == 1) {
                    // İkinci sayfadaysak Türkçe bilgileri alırız.
                    yerAdiBilgi = FragmentTrBilgi.yerAdi.getText().toString();
                    ulkeAdiBilgi = FragmentTrBilgi.ulkeAdi.getText().toString();
                    sehirAdiBilgi = FragmentTrBilgi.sehirAdi.getText().toString();
                    tarihceBilgi = FragmentTrBilgi.tarihce.getText().toString();
                    hakkindaBilgi = FragmentTrBilgi.hakkindaGiris.getText().toString();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FragmentEndBilgi engBilgiFragment = new FragmentEndBilgi();
                    fragmentTransaction.replace(R.id.constraintLayout_Sayfalar, engBilgiFragment).commit();
                    kayitBinding.buttonIleri1.setText("Kaydet");
                } else if (pageNumber == 2) {
                    // Üçüncü sayfadaysak İngilizce bilgileri alırız ve kaydı yaparız.
                    nameInfo = FragmentEndBilgi.placeName.getText().toString();
                    countryInfo = FragmentEndBilgi.countryName.getText().toString();
                    cityInfo = FragmentEndBilgi.cityName.getText().toString();
                    historyInfo = FragmentEndBilgi.history.getText().toString();
                    aboutInfo = FragmentEndBilgi.aboutInfo.getText().toString();

                    // Verilerin veritabanına kaydedilmesi
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_Kayit.this);
                    alertDialog.setTitle("KAYIT");
                    alertDialog.setMessage("Kayıt Etmek İstiyor Musunuz");
                    alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Herhangi bir işlem yapılmaz.
                        }
                    });
                    alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Fotoğraf Uri'si boş değilse işlem devam eder.
                            if (fotoBilgi != Uri.EMPTY) {
                                UUID uuid = UUID.randomUUID();
                                String imageName = "images/" + uuid + ".jpg";
                                storageReference.child(imageName).putFile(fotoBilgi).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Fotoğraf yükleme başarılı olursa, veriler veritabanına eklenir.
                                        StorageReference yeniReferans = FirebaseStorage.getReference(imageName);
                                        yeniReferans.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String downloadUrl = uri.toString();
                                                HashMap<String, Object> yerData = new HashMap<>();
                                                yerData.put("adi", yerAdiBilgi);
                                                yerData.put("ulkesi", ulkeAdiBilgi);
                                                yerData.put("sehir", sehirAdiBilgi);
                                                yerData.put("tarihce", tarihceBilgi);
                                                yerData.put("hakkinda", hakkindaBilgi);
                                                yerData.put("gorselURL", downloadUrl);
                                                yerData.put("placeName", nameInfo);
                                                yerData.put("countryName", countryInfo);
                                                yerData.put("cityName", cityInfo);
                                                yerData.put("historyInfo", historyInfo);
                                                yerData.put("aboutInfo", aboutInfo);
                                                yerData.put("kayitTarihi", FieldValue.serverTimestamp());

                                                FirebaseFirestore.collection("YerKayit").add(yerData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        // Veri kaydı başarılı olursa bildirim gönderilir.
                                                        Toast.makeText(getApplicationContext(), "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                                                        finish();
                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Veri kaydı başarısız olursa hata mesajı gösterilir.
                                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Fotoğraf yükleme başarısız olursa hata mesajı gösterilir.
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

}
