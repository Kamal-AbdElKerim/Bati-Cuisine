package Repository.DAO;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import Repository.Repository;
import config.DatabaseConnection;
import model.Composant;

public class ComposantsRepository implements Repository<Composant> {
        private Connection connection;

    public ComposantsRepository(Connection connection) {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public HashMap<Integer, Composant> findAll() {
        HashMap<Integer, Composant> composantsList = new HashMap<>();
        String sql = "SELECT * FROM Composant";
        
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Composant composant = mapRow(rs);
                composantsList.put(composant.getComposantID(), composant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return composantsList;
    }

    @Override
    public Composant findById(int id) {
        String sql = "SELECT * FROM Composant WHERE composant_id = ?";
        Composant composant = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                composant = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return composant;
    }

 

    @Override
    public Composant findByName(String name) {
        return null;
    }

    @Override
    public int save( Composant composant) {
        String sql = "INSERT INTO Composant (composant_id, nom, cout_unitaire, quantite, type_composant, taux_TVA, projet_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT(composant_id) DO UPDATE SET " +
                     "nom = EXCLUDED.nom, cout_unitaire = EXCLUDED.cout_unitaire, quantite = EXCLUDED.quantite, " +
                     "type_composant = EXCLUDED.type_composant, taux_TVA = EXCLUDED.taux_TVA, projet_id = EXCLUDED.projet_id";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(2, composant.getNom());
            preparedStatement.setDouble(3, composant.getCoutUnitaire());
            preparedStatement.setDouble(4, composant.getQuantite());
            preparedStatement.setString(5, composant.getTypeComposant().toString());
            preparedStatement.setDouble(6, composant.getTauxTVA());
            preparedStatement.setInt(7, composant.getProjet().getProjetID());
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Composant mapRow(ResultSet rs) throws SQLException {
        
        // int composant_id = rs.getInt("composant_id");
        // String nom = rs.getString("nom");
        // double cout_unitaire = rs.getDouble("cout_unitaire");
        // double quantite = rs.getDouble("quantite");
        // String type_composant = rs.getString("type_composant");
        // double taux_tva = rs.getDouble("taux_tva");
        // int projet_id = rs.getInt("projet_id");
        return null ;
    }
    
}


