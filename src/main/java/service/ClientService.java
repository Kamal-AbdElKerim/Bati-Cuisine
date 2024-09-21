package service;

import Repository.DAO.ClientRepository;
import model.Client;

import java.sql.Connection;
import java.util.HashMap;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(Connection connection) {
        this.clientRepository = new ClientRepository(connection);
    }

    public HashMap<Integer, Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(int id) {
        return clientRepository.findById(id);
    }

    public Client getClientByName(String name) {
        return clientRepository.findByName(name);
    }

    public int saveClient(Client client) {
        return clientRepository.save(client);
    }
}
