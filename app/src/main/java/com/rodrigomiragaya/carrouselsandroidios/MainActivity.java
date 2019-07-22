package com.rodrigomiragaya.carrouselsandroidios;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //For Test:
    //Link Json del Test
    private static final String URL1 = "https://api.myjson.com/bins/ej0ev";
    //Link Json con 1
    private static final String URL2 = "https://api.myjson.com/bins/8r3uf";
    //Link Json con 3
    private static final String URL3 = "https://api.myjson.com/bins/r7jlj";


    private RequestQueue mQueu;
    private ArrayList<Carousel> carouselArrayList = new ArrayList<>();
    private RecyclerView recyclerViewVertical;
    private CarouAdapter carouAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerViewVertical = findViewById(R.id.recyclerView1);
        recyclerViewVertical.setHasFixedSize(true);
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(this));

        mQueu = Volley.newRequestQueue(this);

        super.onStart();
        StartAsync task = new StartAsync(this);
        task.execute();

    }


    private static class StartAsync extends AsyncTask<Void, Void, ArrayList<Carousel>>{
        private WeakReference<MainActivity> activityWeakReference;

        StartAsync(MainActivity activity){
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Carousel> doInBackground(Void... voids) {

            //Creando una StrongReference
            MainActivity strongReference = activityWeakReference.get();
            if (strongReference == null || strongReference.isFinishing()){
                return null;
            }

            strongReference.jsonParse();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Carousel> arrayList) {
            super.onPostExecute(arrayList);
        }


    }


    private void jsonParse(){
        Log.d(TAG, "jsonParse: Llamado");

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL1, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Parse Carous
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String tituloCarru = jsonObject.getString("tittle");
                        String tipocarrou = jsonObject.getString("type");
                        ArrayList<Peliculas> pelisArraylist = new ArrayList<>();
                        JSONArray itemsArraylist = jsonObject.getJSONArray("items");


                        // Parse Pelis dento de cada Carou
                        for (int x = 0; x < itemsArraylist.length(); x++) {
                            JSONObject jsonObject2 = itemsArraylist.getJSONObject(x);
                            String tituloPeli = jsonObject2.getString("tittle");
                            String urlPeli = jsonObject2.getString("url");
                            String videoPeli = jsonObject2.getString("video");
                            Peliculas peli = new Peliculas(tituloPeli, urlPeli, videoPeli);
                            pelisArraylist.add(peli);
                        }

                        //Crea un Carou
                        Carousel carousel = new Carousel(tituloCarru, tipocarrou, pelisArraylist );
                        carouselArrayList.add(carousel);

                        //llama CarouAdapter
                        carouAdapter = new CarouAdapter(MainActivity.this,carouselArrayList);
                        recyclerViewVertical.setAdapter(carouAdapter);


                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error en la conexión ", Toast.LENGTH_SHORT).show();
            }
        });
        mQueu.add(request);

    }
}
