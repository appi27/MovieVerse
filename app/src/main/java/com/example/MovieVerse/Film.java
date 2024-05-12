package com.example.MovieVerse;

import android.media.Image;

public class Film {
    private String pathCopertina;
    private String pathBG;
    private String titolo;
    private int genere;
    private int annoProd;
    private String trama;

    public Film(String pathCopertina, String pathBG, String titolo, int genere, int annoProd, String trama) {
        this.pathCopertina = pathCopertina;
        this.pathBG = pathBG;
        this.titolo = titolo;
        this.genere = genere;
        this.annoProd = annoProd;
        this.trama = trama;
    }

    public String getPathCopertina() {
        return pathCopertina;
    }

    public void setPathCopertina(String pathCopertina) {
        this.pathCopertina = pathCopertina;
    }

    public String getPathBG() {
        return pathBG;
    }

    public void setPathBG(String pathBG) {
        this.pathBG = pathBG;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getGenere() {
        return genere;
    }

    public void setGenere(int genere) {
        this.genere = genere;
    }

    public int getAnnoProd() {
        return annoProd;
    }

    public void setAnnoProd(int annoProd) {
        this.annoProd = annoProd;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }
    @Override
    public String toString() {
        return "Film{" +
                "pathCopertina='" + pathCopertina + '\'' +
                ", pathBG='" + pathBG + '\'' +
                ", titolo='" + titolo + '\'' +
                ", genere='" + genere + '\'' +
                ", annoProd=" + annoProd +
                ", trama=" + trama +
                '}';
    }
}
