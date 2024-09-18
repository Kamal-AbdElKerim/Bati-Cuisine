package Repository.DAO;

import model.MainOeuvre;
import java.sql.*;
import java.util.HashMap;
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
    public int save( MainOeuvre mainOeuvre) {
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
        return 0 ;
    }

    public MainOeuvre mapRow(ResultSet rs) throws SQLException {
        // Ensure this method correctly maps the ResultSet to a MainOeuvre object
        // return new MainOeuvre(
        //     rs.getInt("Id"),
        //     rs.getDouble("taux_horaire"),
        //     rs.getBigDecimal("heures_travail"),
        //     rs.getBigDecimal("productivite_ouvrier"),
        //     rs.getInt("Composants_id")
        // );
        return null ;
    }
}
