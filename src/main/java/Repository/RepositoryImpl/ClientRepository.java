package Repository.RepositoryImpl;


import model.Client;
import java.sql.*;
import java.util.HashMap;




import Repository.Repository;

public class ClientRepository implements Repository<Client> {

    private Connection connection;

    public ClientRepository(Connection connection) {
        this.connection = connection ;
    }

    @Override
    public HashMap<Integer, Client> findAll() {
        HashMap<Integer, Client> clients = new HashMap<>();
        String sql = "SELECT * FROM Client";
        
        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Client client = mapRow(rs);
                clients.put(client.getClientID(), client);
            }
        } catch (SQLException e) {
        }
        
        return clients;
    }

    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                client = mapRow(rs);
            }
        } catch (SQLException e) {
        }

        return client;
    }
@Override
    public Client findByName(String name) {
        String sql = "SELECT * FROM clients WHERE nom = ?";
        Client client = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
           
            
            if (rs.next()) {
                client = mapRow(rs);
            }
        } catch (SQLException e) {
        }

        return client;
    }

    @Override
    public int save(Client client) {
        String sql = "INSERT INTO clients (nom, adresse, telephone, est_professionnel) VALUES (?, ?, ?, ?)";
        int generatedId = -1;
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getAdresse());
            preparedStatement.setString(3, client.getTelephone());
            preparedStatement.setBoolean(4, client.isEstProfessionnel());
    
            preparedStatement.executeUpdate();
    
            // Retrieve the generated client_id
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return generatedId;
    }
    

    @Override
    public Client mapRow(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setClientID(rs.getInt("client_id"));
        client.setNom(rs.getString("nom"));
        client.setAdresse(rs.getString("adresse"));
        client.setTelephone(rs.getString("telephone"));
        client.setEstProfessionnel(rs.getBoolean("est_professionnel"));
        return client;
    }
}

