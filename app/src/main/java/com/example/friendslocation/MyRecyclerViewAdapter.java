package com.example.friendslocation;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolders> {
    Context context;
    ArrayList<MyLocation>  data;
    String nom;
    String numero;
    int indice;


    public MyRecyclerViewAdapter(Context context, ArrayList<MyLocation> data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v=  inflater.inflate(R.layout.element,null);
        return new MyViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolders holder, int position) {
        //affectation holders
        MyLocation contact=data.get(position);
        holder.tv_nom.setText(contact.getNom());
        holder. tv_numérotl.setText(contact.getNuméro());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolders extends RecyclerView.ViewHolder {
      public   TextView tv_nom,tv_numérotl;
      public  ImageView imageViewDelete,imageViewLocalisation,imageViewCall;
        public MyViewHolders(@NonNull View itemView) {
            super(itemView);
            //récupérer holders
            tv_nom= itemView.findViewById(R.id.tv_nom_contact);
            tv_numérotl=itemView.findViewById(R.id.tv_numtl_contact);
            imageViewDelete=itemView.findViewById(R.id.imageView_delete);
            imageViewLocalisation=itemView.findViewById(R.id.imageView_localisation);






            //Creation events
            imageViewLocalisation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String longitude=data.get(getAdapterPosition()).getLongitude();
                    String latitude=data.get(getAdapterPosition()).getLatitude();
                    System.out.println(latitude);
                    System.out.println(longitude);
                    Intent i = new Intent(context,MapsActivity.class);
                    i.putExtra("longitude", longitude);
                    i.putExtra("latitude", latitude);
                    context.startActivity(i);

                }
            });

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    indice=getAdapterPosition();
                    numero= data.get(indice).getNuméro();
                    nom=data.get(indice).getNom();

                   new Delete().execute();
                    data.remove(indice);
                    notifyDataSetChanged();
                }
            });



        }
    }

    class Delete extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONParser parser=new JSONParser();
            int success_del;
            //String ip="10.0.2.2";
            String ip="192.168.43.179:8080";

            HashMap<String,String>m=new HashMap<>();

            m.put("nom", nom);
            m.put("numero", numero);
            JSONObject obj=parser.makeHttpRequest("http://"+ip+"/servicephp/delete.php","GET",m);
            try {
                success_del= obj.getInt("success");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }

}
