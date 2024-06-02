package com.MovieVerse.globalClasses.series;
public class Series {
    private String pathCopertina;
    private String pathBG;
    private String titolo;
    private String genere;
    private int annoProd;
    private String trama;

    private  float vote;

    public Series(String pathCopertina, String pathBG, String titolo, String genere, int annoProd, String trama,float vote) {
        this.pathCopertina = pathCopertina;
        this.pathBG = pathBG;
        this.titolo = titolo;
        this.genere = genere;
        this.annoProd = annoProd;
        this.trama = trama;
        this.vote=vote;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
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

    public float getVote () {
        return vote;
    }
}
