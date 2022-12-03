package com.example.friendslocation.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.friendslocation.JSONParser;
import com.example.friendslocation.MyLocation;
import com.example.friendslocation.MyRecyclerViewAdapter;
import com.example.friendslocation.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<MyLocation> data=new ArrayList<MyLocation>();
    MyRecyclerViewAdapter ad;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ad=new MyRecyclerViewAdapter(getActivity(), data);

        GridLayoutManager llm = new GridLayoutManager(getActivity(), 1,GridLayoutManager.VERTICAL,false);

        binding.lv.setLayoutManager(llm);

        binding.lv.setAdapter(ad);
        binding.btnDownload.setOnClickListener(v->{
            Telechargement t=new Telechargement(getActivity());
            t.execute();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    class Telechargement  extends AsyncTask
        {
            Context con;
            AlertDialog alertDialog;

            public Telechargement(Context con) {
                this.con = con;
            }

            @Override
            protected void onPreExecute() {
                //User interface thread
                //peut accéder au context courant / graphique
                // affiche boite dialogue
                AlertDialog.Builder builder=new AlertDialog.Builder(con);
                builder.setTitle("Téléchargement");
                builder.setMessage("Veuillez patientez ...");
                alertDialog=builder.create();
                alertDialog.show();

            }

            @Override
            protected Object doInBackground(Object[] objects) {
                //second thread pas accés a interface graphique


                //exécuter le service json
                JSONParser paraser=new JSONParser();
                /**
                 * LAN ipv4
                 * AVD 10.0.2.2
                 * internet www....com
                 * */


                //String ip="10.0.2.2";
                String ip="192.168.43.179:8080";
                String  url="http://"+ip+"/servicephp/getAll.php";

                JSONObject response=paraser.makeHttpRequest(url,"GET",null);
                try {
                    int success=response.getInt("success");
                    if(success==0){
                        String msg=response.getString("message");
                    }else{
                        JSONArray tableau=response.getJSONArray("Ami");
                        data.clear();
                        for (int i=0;i<tableau.length();i++){
                            JSONObject ligne=tableau.getJSONObject(i);
                            String nom=ligne.getString("nom");
                            String numero=ligne.getString("numero");
                            String longitude=ligne.getString("longitude");
                            String latitude=ligne.getString("latitude");
                            data.add(new MyLocation(nom,numero,longitude,latitude));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                //UI thread( user interface)
                ad.notifyDataSetChanged();
                alertDialog.dismiss();



            }
        }
}