package service;

import model.Composant;

import java.sql.Connection;
import java.util.HashMap;

import Repository.RepositoryImpl.ComposantsRepository;

public class ComposantsService {
    private ComposantsRepository composantsRepository;

    public ComposantsService(Connection connection) {
        this.composantsRepository = new ComposantsRepository(connection);
    }

    public HashMap<Integer, Composant> getAllComposants() {
        return composantsRepository.findAll();
    }

    public Composant getComposantById(int id) {
        return composantsRepository.findById(id);
    }

    public int saveComposant(Composant composant) {
        return composantsRepository.save(composant);
    }
}
