package org.example.model;

public class Rivista extends Pubblicazione {

    int numeroEdizione;
    double tariffa;

    public Rivista(boolean inPrestito, String titolo, int codice, int numeroEdizione) {
        super(inPrestito, titolo, codice);
        this.numeroEdizione = numeroEdizione;
        this.tariffa = 0.35;
    }

    @Override
    public double calcolaCostoPrestito(int giorni) {
        return tariffa * giorni;
    }

    @Override
    public int getMaxGiorniPrestito() {
        return 25;
    }

    public int getNumeroEdizione() {
        return numeroEdizione;
    }

    public double getTariffa() {
        return tariffa;
    }
}
