package com.rodrigomiragaya.carrouselsandroidios;


import java.util.ArrayList;

public class Carousel {
    private String titulo;
    private String tipo;
    private ArrayList<Peliculas> arrayPeliculas = new ArrayList<>();

    public Carousel(String titulo, String tipo, ArrayList<Peliculas> arrayPeliculas) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.arrayPeliculas = arrayPeliculas;
    }


    public String getTipo() {
        return tipo;
    }

    public ArrayList<Peliculas> getArrayPeliculas() {
        return arrayPeliculas;
    }


    @Override
    public String toString() {
        return "Carousel{" +
                "titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", arrayPeliculas=" + arrayPeliculas +
                '}';
    }
}
