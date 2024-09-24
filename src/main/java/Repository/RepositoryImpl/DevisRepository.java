package Repository.RepositoryImpl;


import model.Devis;
import java.sql.*;
import java.util.HashMap;


import Repository.Repository;

public class DevisRepository implements Repository<Devis> {

    private Connection connection;

    public DevisRepository(Connection connection) {
       this.connection = connection;
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
        }

        return devis;
    }

    public Devis findByProjectID(int id) {
        String sql = "SELECT * FROM devis WHERE projet_id = ?";
        Devis devis = null;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
    
            if (rs.next()) {
                devis = mapRow(rs);
            }
        } catch (SQLException e) {
        }
    
        return devis;
    }
    

    @Override
    public Devis findByName(String name) {
        return null;
    }

    @Override
    public int save( Devis devis) {
        String sql = "INSERT INTO devis ( montant_estime, date_emission, date_validite, accepte , projet_id) " +
                     "VALUES (?, ?, ?, ?, ?)" ;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, devis.getMontantEstime());
            preparedStatement.setDate(2, Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(3, devis.getDateValidite() != null ? Date.valueOf(devis.getDateValidite()) : null);
            preparedStatement.setBoolean(4, devis.isAccepte());
            preparedStatement.setInt(5, devis.getProject().getProjetID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        return 0 ;
    }

    @Override
    public Devis mapRow(ResultSet rs) throws SQLException {
        Devis devis = new Devis();
        devis.setDevisID(rs.getInt("devis_id"));
        devis.setMontantEstime(rs.getDouble("montant_estime"));
        devis.setDateEmission(rs.getDate("date_emission").toLocalDate());
        devis.setDateValidite(rs.getDate("date_validite") != null ? rs.getDate("date_validite").toLocalDate() : null);
        devis.setAccepte(rs.getBoolean("accepte"));

        return devis;
    }
}

