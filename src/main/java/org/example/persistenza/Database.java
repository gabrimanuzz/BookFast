package org.example.persistenza;

import org.example.exceptions.PubblicazioneNonDisponibileException;
import org.example.model.Libro;
import org.example.model.Pubblicazione;
import org.example.model.Rivista;

import java.sql.*;

public class Database {

    private Connection connection = null;
    static Database instance = null;

    private Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
    }

    public static Database getInstance() throws SQLException {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void registraPrestito(int codice) throws PubblicazioneNonDisponibileException {

        if (!testConnection())
            throw new PubblicazioneNonDisponibileException("Pubblicazione inesistente");


        String checkPrestito = "SELECT p.in_prestito FROM Pubblicazioni p WHERE p.codice = ?";

        try (PreparedStatement statement = connection.prepareStatement(checkPrestito)) {

            statement.setInt(1, codice);
            try (ResultSet rs = statement.executeQuery()) {

                if (!rs.next()) {
                    throw new PubblicazioneNonDisponibileException("Pubblicazione inesistente");
                }

                // Controlla se è già in prestito
                boolean inPrestito = rs.getBoolean("in_prestito");
                if (inPrestito) {
                    throw new PubblicazioneNonDisponibileException("La pubblicazione è già in prestito");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "UPDATE Pubblicazioni SET in_prestito = true WHERE codice = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, codice);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void inserisciPubblicazione(Pubblicazione p) throws PubblicazioneNonDisponibileException {
        if (!testConnection())
            throw new PubblicazioneNonDisponibileException("Errore database");


        if (p instanceof Libro) {

            String query = "INSERT INTO Pubblicazioni (titolo, in_prestito, tipo, Genere) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, p.getTitolo());
                statement.setBoolean(2, p.isInPrestito());
                statement.setString(4, "Libro");
                statement.setString(3, ((Libro) p).getGenere());

                statement.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (p instanceof Rivista) {
            String query = "INSERT INTO Pubblicazioni (titolo, in_prestito, tipo, numeroEdizione) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, p.getTitolo());
                statement.setBoolean(2, p.isInPrestito());
                statement.setString(4, "Rivista");
                statement.setInt(3, ((Rivista) p).getNumeroEdizione());

                statement.execute();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }


    private boolean testConnection() {
        try {
            return connection.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
}
