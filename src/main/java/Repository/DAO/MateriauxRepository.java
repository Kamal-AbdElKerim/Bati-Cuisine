package Repository.DAO;


import model.Materiaux;
import java.sql.*;
import java.util.HashMap;

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

    @Override
    public Materiaux mapRow(ResultSet rs) throws SQLException {
        Materiaux materiel = new Materiaux();
        materiel.setMateriauID(rs.getInt("Id"));
        materiel.setCoutTransport(rs.getDouble("cout_transport"));
        materiel.setCoefficientQualite(rs.getDouble("coefficient_qualite"));
        materiel.setComposantID(rs.getInt("Composants_id"));
        
        return materiel;
    }
}

