package service;

import model.Client;

import java.util.HashMap;

import Repository.RepositoryImpl.ClientRepository;

public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository ) {
        this.clientRepository = clientRepository;
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
