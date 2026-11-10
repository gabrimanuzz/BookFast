package org.example.model;

public abstract class Pubblicazione {

    int codice;
    String titolo;
    boolean inPrestito;

    public Pubblicazione(boolean inPrestito, String titolo, int codice) {
        this.inPrestito = inPrestito;
        this.titolo = titolo;
        this.codice = codice;
    }

    public abstract double calcolaCostoPrestito(int giorni);

    public abstract int getMaxGiorniPrestito();

    public boolean isInPrestito() {
        return inPrestito;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getCodice() {
        return codice;
    }
}
