package Repository.RepositoryImpl;

import model.Project;
import model.Client;
import model.EtatProjet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            String query = "SELECT * FROM projets where etat_projet = 'TERMINE'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Project projet = mapRow(rs);
                projets.put(rs.getInt("projet_id"), projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projets;
    }

    @Override
    public Project findById(int id) {
        try {
            String query = "SELECT * FROM projets WHERE projet_id = ? ";
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

    public List<Project> selectProjectsNoCout() {
        List<Project> projects = new ArrayList<>();
        try {
            String query = "SELECT * FROM projets WHERE etat_projet = 'EN_COURS' AND cout_total = 0.00 ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                projects.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public int save(Project projet) {
        int projetId = -1;
        try {
            String query = "INSERT INTO projets (nom_projet, marge_beneficiaire, cout_total, etat_projet, surface_cuisine, client_id) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING projet_id";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projet.getNomProjet());
            statement.setDouble(2, projet.getMargeBeneficiaire());
            statement.setDouble(3, projet.getCoutTotal());
            statement.setString(4, projet.getEtatProjet().name());
            statement.setDouble(5, projet.getSurfaceCuisine());
            statement.setInt(6, projet.getClient().getClientID());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                projetId = resultSet.getInt("projet_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projetId;
    }

    public boolean updateProject(Project projet, int projectId) {
        String sql = "UPDATE projets SET  marge_beneficiaire = ?, cout_total = ?, etat_projet = ?, " +
                " tva = ? WHERE projet_id = ?";
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setDouble(1, projet.getMargeBeneficiaire());
            pstmt.setDouble(2, projet.getCoutTotal());
            pstmt.setString(3, projet.getEtatProjet().name());
            pstmt.setDouble(4, projet.getTVA());
            pstmt.setInt(5, projectId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Project mapRow(ResultSet rs) throws SQLException {
        int idProjet = rs.getInt("projet_id");
        String nomProjet = rs.getString("nom_projet");
        double margeBeneficiaire = rs.getDouble("marge_beneficiaire");
        double coutTotal = rs.getDouble("cout_total");
        EtatProjet etatProjet = EtatProjet.valueOf(rs.getString("etat_projet"));
        int clientId = rs.getInt("client_id");
        double surfaceCuisine = rs.getDouble("surface_cuisine");
        double TVA = rs.getDouble("TVA");

        Client client = new ClientRepository(connection).findById(clientId);

        Project projet = new Project(idProjet, nomProjet, margeBeneficiaire, coutTotal, surfaceCuisine, TVA, client);

        return projet;
    }

    public void displayProjectsInTable(List<Project> projects) {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s %-20s %-20s\n",
                "ID", "Nom Projet", "Marge Beneficiaire", "Cout Total", "Surface Cuisine", "TVA", "Client");

        System.out.println(
                "--------------------------------------------------------------------------------------------------------------------------------");

        for (Project project : projects) {
            System.out.printf("%-10d %-20s %-20.2f %-20.2f %-20.2f %-20.2f %-20s\n",
                    project.getProjetID(),
                    project.getNomProjet(),
                    project.getMargeBeneficiaire(),
                    project.getCoutTotal(),
                    project.getSurfaceCuisine(),
                    project.getTVA(),
                    project.getClient().getNom());
        }
    }
}