package Repository.DAO;


import model.Project;
import model.Client;
import model.EtatProjet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Repository.Repository;
import config.DatabaseConnection;

public class ProjetRepository implements Repository<Project> {

    private Connection connection;

    public ProjetRepository(Connection connection) {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public HashMap<Integer, Project> findAll() {
        HashMap<Integer, Project> projets = new HashMap<>();
        try {
            String query = "SELECT * FROM Projet";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Project projet = mapRow(rs);
                projets.put(rs.getInt("Id"), projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projets;
    }

    @Override
    public Project findById(int id) {
        try {
            String query = "SELECT * FROM Projet WHERE Id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Project findByName(String name) {
        return null;
    }

    @Override
    public int save( Project projet) {
        try {
            String query = "INSERT INTO Projet (Id, nomProjet, margeBeneficiaire, coutTotal, etatProjet, clientId) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(2, projet.getNomProjet());
            statement.setDouble(3, projet.getMargeBeneficiaire());
            statement.setDouble(4, projet.getCoutTotal());
            statement.setString(5, projet.getEtatProjet().name());
            statement.setInt(6, projet.getClient().getClientID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Project mapRow(ResultSet rs) throws SQLException {
        String nomProjet = rs.getString("nomProjet");
        double margeBeneficiaire = rs.getDouble("margeBeneficiaire");
        double coutTotal = rs.getDouble("coutTotal");
        EtatProjet etatProjet = EtatProjet.valueOf(rs.getString("etatProjet"));
        int clientId = rs.getInt("clientId");

        // Fetching the client separately (assuming ClientRepository is implemented)
        Client client = new ClientRepository(connection).findById(clientId);

        Project projet = new Project(nomProjet, margeBeneficiaire, client);
        projet.setCoutTotal(coutTotal);
        projet.setEtatProjet(etatProjet);
        return projet;
    }
}

