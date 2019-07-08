package com.rodrigomiragaya.carrouselsandroidios;

public class Peliculas {
    private String titulo;
    private String url;
    private String video;

    public Peliculas(String titulo, String url, String video) {
        this.titulo = titulo;
        this.url = url;
        this.video = video;
    }

    @Override
    public String toString() {
        return "Peliculas{" +
                "titulo='" + titulo + '\'' +
                ", url='" + url + '\'' +
                ", video='" + video + '\'' +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrl() {
        return url;
    }

    public String getVideo() {
        return video;
    }
}
