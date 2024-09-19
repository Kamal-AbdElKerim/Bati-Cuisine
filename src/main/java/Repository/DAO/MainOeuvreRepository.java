package Repository.DAO;

import model.MainOeuvre;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import Repository.Repository;

public class MainOeuvreRepository implements Repository<MainOeuvre> {

    private Connection connection;

    public MainOeuvreRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public HashMap<Integer, MainOeuvre> findAll() {
        HashMap<Integer, MainOeuvre> mainOeuvreList = new HashMap<>();
        String sql = "SELECT * FROM MainOeuvre";

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                MainOeuvre mainOeuvre = mapRow(rs);
                mainOeuvreList.put(mainOeuvre.getMainOeuvreID(), mainOeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mainOeuvreList;
    }

    @Override
    public MainOeuvre findById(int id) {
        String sql = "SELECT * FROM MainOeuvre WHERE Id = ?";
        MainOeuvre mainOeuvre = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    mainOeuvre = mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mainOeuvre;
    }

    @Override
    public MainOeuvre findByName(String name) {
        return null;
    }

    @Override
    public int save(MainOeuvre mainOeuvre) {
        String sql = "INSERT INTO MainOeuvre (Id, taux_horaire, heures_travail, productivite_ouvrier, Composants_id) " +
                "VALUES (?, ?, ?, ?, ?) ON CONFLICT(Id) DO UPDATE SET " +
                "taux_horaire = EXCLUDED.taux_horaire, heures_travail = EXCLUDED.heures_travail, " +
                "productivite_ouvrier = EXCLUDED.productivite_ouvrier, Composants_id = EXCLUDED.Composants_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(2, mainOeuvre.getTauxHoraire());
            preparedStatement.setDouble(3, mainOeuvre.getHeuresTravail());
            preparedStatement.setDouble(4, mainOeuvre.getProductiviteOuvrier());
            preparedStatement.setInt(5, mainOeuvre.getComposantID()); // Ensure this field matches your database schema

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insertData(HashMap<String, MainOeuvre> mainOeuvreMap, int projetID) throws Exception {
        String insertComposantQuery = "INSERT INTO gestion_des_composants (nom, cout_unitaire, quantite, type_composant, taux_tva, projet_id) "
                +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING composant_id";
        String insertMainOeuvreQuery = "INSERT INTO main_oeuvre (type_mainoeuvre, taux_horaire, heures_travail, productivite_ouvrier, composants_id) "
                +
                "VALUES (?, ?, ?, ?, ?)";

        for (Map.Entry<String, MainOeuvre> entry : mainOeuvreMap.entrySet()) {
            MainOeuvre mainOeuvre = entry.getValue();

            // 1. Insert into gestion_des_composants and retrieve composant_id
            int composantId = -1;
            try (PreparedStatement stmtComposant = connection.prepareStatement(insertComposantQuery)) {
                stmtComposant.setString(1, mainOeuvre.getNom());
                stmtComposant.setDouble(2, mainOeuvre.getCoutUnitaire());
                stmtComposant.setDouble(3, mainOeuvre.getQuantite());
                stmtComposant.setString(4, mainOeuvre.getTypeComposant().name());
                stmtComposant.setDouble(5, mainOeuvre.getTauxTVA());
                stmtComposant.setInt(6, projetID);

                ResultSet rs = stmtComposant.executeQuery();
                if (rs.next()) {
                    composantId = rs.getInt("composant_id");
                }
            }

            // 2. Insert into main_oeuvre using the retrieved composant_id
            if (composantId != -1) {
                try (PreparedStatement stmtMainOeuvre = connection.prepareStatement(insertMainOeuvreQuery)) {
                    stmtMainOeuvre.setString(1, mainOeuvre.getMainOeuvreType());
                    stmtMainOeuvre.setDouble(2, mainOeuvre.getTauxHoraire());
                    stmtMainOeuvre.setDouble(3, mainOeuvre.getHeuresTravail());
                    stmtMainOeuvre.setDouble(4, mainOeuvre.getProductiviteOuvrier());
                    stmtMainOeuvre.setInt(5, composantId);

                    stmtMainOeuvre.executeUpdate();
                }
            }
        }
    }

    public MainOeuvre mapRow(ResultSet rs) throws SQLException {
        // Ensure this method correctly maps the ResultSet to a MainOeuvre object
        // return new MainOeuvre(
        // rs.getInt("Id"),
        // rs.getDouble("taux_horaire"),
        // rs.getBigDecimal("heures_travail"),
        // rs.getBigDecimal("productivite_ouvrier"),
        // rs.getInt("Composants_id")
        // );
        return null;
    }
}
