package service;

import model.Composant;

import java.util.HashMap;

import Repository.RepositoryImpl.ComposantsRepository;

public class ComposantsService {
    private ComposantsRepository composantsRepository;

    public ComposantsService(ComposantsRepository composantsRepository) {
        this.composantsRepository = composantsRepository;
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
