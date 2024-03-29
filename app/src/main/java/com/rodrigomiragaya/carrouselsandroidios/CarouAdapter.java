package com.rodrigomiragaya.carrouselsandroidios;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CarouAdapter extends RecyclerView.Adapter<CarouAdapter.carouViewHolder> implements PeliAdapter.OnItemClickListener {

    private Context mContext;
    private ArrayList<Carousel> carouselArrayList;
    private ArrayList<Peliculas> peliculasArrayList;

    public CarouAdapter(Context mContext, ArrayList<Carousel> carouselArrayList) {
        this.mContext = mContext;
        this.carouselArrayList = carouselArrayList;
    }

    @NonNull
    @Override
    public carouViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.carourecycler, viewGroup, false);
        return new carouViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull carouViewHolder carouViewHolder, int i) {
        Carousel carouselactual = carouselArrayList.get(i);

        peliculasArrayList = carouselactual.getArrayPeliculas();

        //selecciona el Titulo del Carou
        if (carouselactual.getTipo().equalsIgnoreCase("thumb")){
            carouViewHolder.titulocarru.setText(R.string.thumb);
        }else {
            carouViewHolder.titulocarru.setText(R.string.poster);
        }

        //Crea Recycler de Peliculas
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        carouViewHolder.recyclerViewPelis.setLayoutManager(layoutManager);
        carouViewHolder.recyclerViewPelis.setHasFixedSize(true);

        //Llama PeliAdapter
        PeliAdapter peliAdapter = new PeliAdapter(mContext, peliculasArrayList, carouselactual.getTipo());
        carouViewHolder.recyclerViewPelis.setAdapter(peliAdapter);
        peliAdapter.setOnItemClickListener(this);
        peliAdapter.notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return this.carouselArrayList.size();
    }

    @Override
    public void onItenClick(int posicion) {

        //Click Listener de cada pelicula
        Intent detallesdelIntent = new Intent(mContext, VideoActivitie.class);
        Peliculas peliClickeada = peliculasArrayList.get(posicion);
        detallesdelIntent.putExtra("VIDEOURL",peliClickeada.getVideo() );
        detallesdelIntent.putExtra("NOMBREPELICULA",peliClickeada.getTitulo() );
        if (peliClickeada.getVideo().equalsIgnoreCase("")){
            Toast.makeText(mContext, "Video No Disponible", Toast.LENGTH_SHORT).show();
        } else {
            mContext.startActivity(detallesdelIntent);
        }

    }

    class carouViewHolder extends RecyclerView.ViewHolder{
        private TextView titulocarru;
        private RecyclerView recyclerViewPelis;

        private carouViewHolder(@NonNull View itemView) {
            super(itemView);
            titulocarru = itemView.findViewById(R.id.titulocarru);
            recyclerViewPelis = itemView.findViewById(R.id.recyclerViewHori);
        }
    }
}
