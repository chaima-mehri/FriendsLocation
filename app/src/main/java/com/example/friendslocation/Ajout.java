package com.example.friendslocation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.friendslocation.ui.home.HomeFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Ajout extends AppCompatActivity {
    EditText ednom,edNum,edLong,edLat;
    Button btnajout,btnquitter;
    String longitude="";
    String latitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        ednom= findViewById(R.id.ednom_ajout);
        edNum =findViewById(R.id.ednum_ajout);
        edLong=findViewById(R.id.edlong_ajout);
        edLat=findViewById(R.id.edlat_ajout);


        btnajout =findViewById(R.id.btnajout_ajout);
        btnquitter=findViewById(R.id.btnann_ajout);

        Intent x=getIntent();
        //x=i car on a définit i comme intent de accueil activity
        Bundle b=x.getExtras();
        //récupérer le nom de user par key
        String varlong=b.getString("long");
        String varlat=b.getString("lat");
        edLong.setText("Votre Longitude: "+varlong);
        longitude=varlong;
        latitude=varlat;

        edLat.setText("Votre Latitude: "+varlat);
        edLat.setEnabled(false);
        edLong.setEnabled(false);

        btnquitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return index of selected element
                Intent i=new Intent(Ajout.this,MainActivity.class);
                //lancerActivitÃ© i
                startActivity(i);
                finish();
            }

        });
        btnajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajout.Telechargement1 t=new Ajout.Telechargement1(Ajout.this);
                t.execute();

            }
        });
    }

    class Telechargement1  extends AsyncTask
    {
        Context con;
        AlertDialog alertDialog;

        public Telechargement1(Context con) {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            //second thread pas accés a interface graphique
            if (MainActivity.localisation_permission) {



                JSONParser paraser=new JSONParser();
            /**
             * LAN ipv4
             * AVD 10.0.2.2
             * internet www....com
             * */
            //String ip="10.0.2.2";
            String ip="192.168.43.179:8080";
            String  url="http://"+ip+"/servicephp/add.php";

            HashMap<String,String> param=new HashMap<String,String>();
            param.put("nom",ednom.getText().toString());
                System.out.println("------------"+ednom.getText().toString());
            param.put("numero",edNum.getText().toString());
                System.out.println("------------"+edNum.getText().toString());
            param.put("longitude",longitude);
                System.out.println("------------"+longitude.toString());
            param.put("latitude",latitude);

            JSONObject response=paraser.makeHttpRequest(url,"POST",param);
                System.out.println("maye5demch ya bnet il nesssss il objet ");
            try {
                int success=response.getInt("success");
                if(success==0){
                    String msg=response.getString("message");
                    System.out.println("--------"+msg);
                }else{
                    ednom.setText("");
                    edNum.setText("");
               }
            }
            catch (JSONException e) {
                e.printStackTrace();
                System.out.println("maye5demch ya bnet il nesssss");

            }}
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            //UI thread( user interface)
        }
    }

}