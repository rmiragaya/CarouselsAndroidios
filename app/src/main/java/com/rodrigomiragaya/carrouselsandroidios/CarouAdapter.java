package com.rodrigomiragaya.carrouselsandroidios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CarouAdapter extends RecyclerView.Adapter<CarouAdapter.carouViewHolder> {

    private Context mContext;
    private ArrayList<Carousel> carouselArrayList;

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

        String tituloCarou = carouselactual.getTipo().toLowerCase().trim();
        ArrayList<Peliculas> peliculasArrayList = carouselactual.getArrayPeliculas();

        if (carouselactual.getTipo().equalsIgnoreCase("thumb")){
            carouViewHolder.titulocarru.setText("FOX PLAY +");
        }else {
            carouViewHolder.titulocarru.setText("FOX Contenido Básico");
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        carouViewHolder.recyclerViewPelis.setLayoutManager(layoutManager);
        carouViewHolder.recyclerViewPelis.setHasFixedSize(true);
        PeliAdapter peliAdapter = new PeliAdapter(mContext, peliculasArrayList, carouselactual.getTipo());
        carouViewHolder.recyclerViewPelis.setAdapter(peliAdapter);
        peliAdapter.notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return this.carouselArrayList.size();
    }

    public class carouViewHolder extends RecyclerView.ViewHolder{
        public TextView titulocarru;
        public RecyclerView recyclerViewPelis;

        public carouViewHolder(@NonNull View itemView) {
            super(itemView);
            titulocarru = itemView.findViewById(R.id.titulocarru);
            recyclerViewPelis = itemView.findViewById(R.id.recyclerViewHori);
        }
    }
}
