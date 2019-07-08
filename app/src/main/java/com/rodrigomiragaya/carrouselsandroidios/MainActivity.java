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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RequestQueue mQueu;
    private String url = "https://api.myjson.com/bins/ej0ev";
    private ArrayList<Carousel> carouselArrayList = new ArrayList<>();
    private RecyclerView recyclerViewVertical;
    private CarouAdapter carouAdapter;



    //Json de la prueba https://api.myjson.com/bins/ej0ev
    //Json con mas carrou https://api.myjson.com/bins/r7jlj
    //Json con un carrou https://api.myjson.com/bins/8r3uf


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewVertical = findViewById(R.id.recyclerView1);
        recyclerViewVertical.setHasFixedSize(true);
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(this));


        mQueu = Volley.newRequestQueue(this);

        super.onStart();
        StartAsync task = new StartAsync();
        task.execute();

    }


    private class StartAsync extends AsyncTask<Void, Void, ArrayList<Carousel>>{

        @Override
        protected ArrayList<Carousel> doInBackground(Void... voids) {
            jsonParse();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<Carousel> arrayList) {
            super.onPostExecute(arrayList);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }


    private ArrayList<Carousel> jsonParse(){
        Log.d(TAG, "jsonParse: Llamado");


        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String tituloCarru = jsonObject.getString("tittle");
                        String tipocarrou = jsonObject.getString("type");
                        ArrayList<Peliculas> pelisArraylist = new ArrayList<>();
                        JSONArray itemsArraylist = jsonObject.getJSONArray("items");


                        // aca va un for para agarrar todas las pelis dentro de ITEM
                        for (int x = 0; x < itemsArraylist.length(); x++) {
                            JSONObject jsonObject2 = itemsArraylist.getJSONObject(x);
                            String tituloPeli = jsonObject2.getString("tittle");
                            String urlPeli = jsonObject2.getString("url");
                            String videoPeli = jsonObject2.getString("video");
                            Peliculas peli = new Peliculas(tituloPeli, urlPeli, videoPeli);
                            pelisArraylist.add(peli);
                        }
                        // aca va un for para agarrar todas las pelis dentro de ITEM


                        Carousel carousel =new Carousel(tituloCarru, tipocarrou, pelisArraylist );
                        Log.d(TAG, "onResponse: " + carousel.toString());
                        carouselArrayList.add(carousel);

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
                Toast.makeText(MainActivity.this, "Unable to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mQueu.add(request);
        return carouselArrayList;

    }
}
