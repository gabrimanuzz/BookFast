import org.example.exceptions.PubblicazioneNonDisponibileException;
import org.example.model.Libro;
import org.example.model.Pubblicazione;
import org.example.model.Rivista;
import org.example.persistenza.Database;

import java.sql.SQLException;

import static java.lang.IO.println;

void main() {
    Database db = null;

    try {
        db = Database.getInstance();
    } catch (SQLException e) {
        println("Connessione Database non riuscita!");
        System.exit(-1);
    }

    Libro l = new Libro(false, "Il mare", 3, "Michael");
    Rivista r = new Rivista(false, "Negro", 3, 4);

    try {
        db.inserisciPubblicazione(l);
    } catch (PubblicazioneNonDisponibileException e) {
        println(e.getMessage());
    }

    try {
        db.inserisciPubblicazione(r);
    } catch (PubblicazioneNonDisponibileException e) {
        println(e.getMessage());
    }

    try {
        db.registraPrestito(1);
    } catch (PubblicazioneNonDisponibileException e) {
        println(e.getMessage());
    }
}