package Repository.DAO;


import model.Devis;
import java.sql.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import Repository.Repository;
import config.DatabaseConnection;

public class DevisRepository implements Repository<Devis> {

    private Connection connection;
    private static final Logger logger = Logger.getLogger(DevisRepository.class.getName());

    public DevisRepository(Connection connection) {
       this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public HashMap<Integer, Devis> findAll() {
        HashMap<Integer, Devis> devisList = new HashMap<>();
        String sql = "SELECT * FROM Devis";

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Devis devis = mapRow(rs);
                devisList.put(devis.getDevisID(), devis);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all devis from database.", e);
        }

        return devisList;
    }

    @Override
    public Devis findById(int id) {
        String sql = "SELECT * FROM Devis WHERE Id = ?";
        Devis devis = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                devis = mapRow(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching devis by id from database.", e);
        }

        return devis;
    }

    @Override
    public Devis findByName(String name) {
        return null;
    }

    @Override
    public int save( Devis devis) {
        String sql = "INSERT INTO Devis (Id, MontantEstime, DateEmission, DateValidee, Accepte) " +
                     "VALUES (?, ?, ?, ?, ?)" +
                     "ON CONFLICT(Id) DO UPDATE SET MontantEstime = EXCLUDED.MontantEstime, " +
                     "DateEmission = EXCLUDED.DateEmission, DateValidee = EXCLUDED.DateValidee, Accepte = EXCLUDED.Accepte";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(2, devis.getMontantEstime());
            preparedStatement.setDate(3, Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(4, devis.getDateValidite() != null ? Date.valueOf(devis.getDateValidite()) : null);
            preparedStatement.setBoolean(5, devis.isAccepte());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error saving devis to database.", e);
        }
        return 0 ;
    }

    @Override
    public Devis mapRow(ResultSet rs) throws SQLException {
        Devis devis = new Devis();
        devis.setDevisID(rs.getInt("Id"));
        devis.setMontantEstime(rs.getDouble("MontantEstime"));
        devis.setDateEmission(rs.getDate("DateEmission").toLocalDate());
        devis.isValide(rs.getDate("DateValidee") != null ? rs.getDate("DateValidee").toLocalDate() : null);
        devis.setAccepte(rs.getBoolean("Accepte"));

        return devis;
    }
}

