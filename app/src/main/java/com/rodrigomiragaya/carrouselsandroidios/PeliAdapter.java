package com.rodrigomiragaya.carrouselsandroidios;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PeliAdapter extends RecyclerView.Adapter<PeliAdapter.CViewHolder> {
    private Context mContext;
    private ArrayList<Peliculas> peliculasArrayList;
    private String tipodeCarou;


    public PeliAdapter(Context mContext, ArrayList<Peliculas> peliculasArrayList, String tipodeCarou) {
        this.mContext = mContext;
        this.peliculasArrayList = peliculasArrayList;
        this.tipodeCarou = tipodeCarou;
    }

    @NonNull
    @Override
    public CViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if (tipodeCarou.equalsIgnoreCase("thumb")){
              v = LayoutInflater.from(mContext).inflate(R.layout.imagcarruthumb, viewGroup, false);
        } else {
             v = LayoutInflater.from(mContext).inflate(R.layout.imagcarruposter, viewGroup, false);
        }

        return new CViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull CViewHolder cViewHolder, int i) {
        Peliculas peliculActual = peliculasArrayList.get(i);

        String imageUrl = peliculActual.getUrl();
        String tituloPeli = peliculActual.getTitulo();
        String ulrVideo = peliculActual.getUrl();

        cViewHolder.tituloPeli.setText(tituloPeli);
        Picasso.get().load(imageUrl).fit().centerCrop().into(cViewHolder.imagenPeli);
    }

    @Override
    public int getItemCount() {
        return peliculasArrayList.size();
    }

    public class CViewHolder extends RecyclerView.ViewHolder{
        public ImageView imagenPeli;
        public TextView tituloPeli;

        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPeli = itemView.findViewById(R.id.imagenPelicula);
            tituloPeli = itemView.findViewById(R.id.tituloPelicula);
        }
    }
}
