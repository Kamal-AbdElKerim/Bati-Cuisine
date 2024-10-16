package Repository.RepositoryImpl;


import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import model.*;

import Repository.Repository;

public class MateriauxRepository implements Repository<Materiaux> {

    private Connection connection;

    public MateriauxRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public HashMap<Integer, Materiaux> findAll() {
        HashMap<Integer, Materiaux> materiauxList = new HashMap<>();
        String sql = "SELECT * FROM Materiaux";
        
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Materiaux materiel = mapRow(rs);
                materiauxList.put(materiel.getMateriauID(), materiel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materiauxList;
    }

    @Override
    public Materiaux findById(int id) {
        String sql = "SELECT * FROM Materiaux WHERE Id = ?";
        Materiaux materiel = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                materiel = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materiel;
    }

    public Map<Integer, Materiaux> findByProjectID(int id) {
        String sql = "SELECT * FROM projets p "
                   + "JOIN gestion_des_composants g ON p.projet_id = g.projet_id "
                   + "JOIN materiaux m ON g.composant_id = m.composants_id "
                   + "WHERE p.projet_id = ?";
    
        Map<Integer, Materiaux> materiauxMap = new HashMap<>();  // HashMap to store composant_id and Materiaux
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
    
            while (rs.next()) {
                Materiaux materiaux = mapRow(rs);  // Map each row
                int composantId = rs.getInt("composant_id");  // Use composant_id as key
                materiauxMap.put(composantId, materiaux);  // Add to HashMap
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Proper logging recommended in production
        }
    
        return materiauxMap;  // Return HashMap of composant_id -> Materiaux
    }
    

    @Override
    public Materiaux findByName(String name) {
        return null;
    }

    @Override
    public int save( Materiaux materiel) {
        String sql = "INSERT INTO Materiaux (Id, cout_transport, coefficient_qualite, Composants_id) " +
                     "VALUES (?, ?, ?, ?) ON CONFLICT(Id) DO UPDATE SET " +
                     "cout_transport = EXCLUDED.cout_transport, coefficient_qualite = EXCLUDED.coefficient_qualite, " +
                     "Composants_id = EXCLUDED.Composants_id";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(2, materiel.getCoutTransport());
            preparedStatement.setDouble(3, materiel.getCoefficientQualite());
            preparedStatement.setInt(4, materiel.getComposantID());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertData(HashMap<String, Materiaux> materiauxMap , int projetID) throws Exception{
        String insertComposantQuery = "INSERT INTO gestion_des_composants (nom, cout_unitaire, quantite, type_composant, taux_tva , projet_id) " +
                                      "VALUES (?, ?, ?, ?, ?, ?) RETURNING composant_id";
        String insertMateriauxQuery = "INSERT INTO materiaux (cout_transport, coefficient_qualite, composants_id) VALUES (?, ?, ?)";

        for (Map.Entry<String, Materiaux> entry : materiauxMap.entrySet()) {
            Materiaux materiau = entry.getValue();

            // 1. Insert into gestion_des_composants and retrieve composant_id
            int composantId = -1;
            try (PreparedStatement stmtComposant = connection.prepareStatement(insertComposantQuery)) {
                stmtComposant.setString(1, materiau.getNom());
                stmtComposant.setDouble(2, materiau.getCoutUnitaire());
                stmtComposant.setDouble(3, materiau.getQuantite());
                stmtComposant.setString(4, materiau.getTypeComposant().name());
                stmtComposant.setDouble(5, materiau.getTauxTVA());
                stmtComposant.setInt(6, projetID);


                ResultSet rs = stmtComposant.executeQuery();
                if (rs.next()) {
                    composantId = rs.getInt("composant_id");
                }
            }

            // 2. Insert into materiaux using the retrieved composant_id
            if (composantId != -1) {
                try (PreparedStatement stmtMateriaux = connection.prepareStatement(insertMateriauxQuery)) {
                    stmtMateriaux.setDouble(1, materiau.getCoutTransport());
                    stmtMateriaux.setDouble(2, materiau.getCoefficientQualite());
                    stmtMateriaux.setInt(3, composantId);

                    stmtMateriaux.executeUpdate();
                }
            }
        }
    }
@Override
public Materiaux mapRow(ResultSet rs) throws SQLException {
    // Retrieve data for the Materiaux table
    double coutTransport = rs.getDouble("cout_transport");
    double coefficientQualite = rs.getDouble("coefficient_qualite");

    // Retrieve data for the GestionDesComposants table
    String nomComposant = rs.getString("nom");
    double coutUnitaire = rs.getDouble("cout_unitaire");
    double quantite = rs.getDouble("quantite");
    double tauxTVA = rs.getDouble("taux_tva");

    // // Retrieve data for the Projets table
    // int projetId = rs.getInt("projet_id");
    // String nomProjet = rs.getString("nom_projet");
    // double margeBeneficiaire = rs.getDouble("marge_beneficiaire");
    // double coutTotal = rs.getDouble("cout_total");
    // String etatProjet = rs.getString("etat_projet");
    // int clientId = rs.getInt("client_id");
    // double surfaceCuisine = rs.getDouble("surface_cuisine");
    // double TVA = rs.getDouble("TVA");

    Materiaux materiaux = new Materiaux(nomComposant ,coutUnitaire ,quantite ,tauxTVA , coutTransport , coefficientQualite);

    return materiaux;
}

}

