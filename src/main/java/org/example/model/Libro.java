package org.example.model;

public class Libro extends Pubblicazione{

    String genere;
    double tariffa;

    public Libro(boolean inPrestito, String titolo, int codice, String genere) {
        super(inPrestito, titolo, codice);
        this.tariffa = 0.20;
        this.genere = genere;
    }

    @Override
    public double calcolaCostoPrestito(int giorni) {
        return tariffa * giorni;
    }

    @Override
    public int getMaxGiorniPrestito() {
        return 20;
    }

    public double getTariffa() {
        return tariffa;
    }

    public String getGenere() {
        return genere;
    }
}
