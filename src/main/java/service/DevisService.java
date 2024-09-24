package service;

import model.Devis;

import java.util.HashMap;

import Repository.RepositoryImpl.DevisRepository;

public class DevisService {
    private DevisRepository devisRepository;

    public DevisService(DevisRepository devisRepository) {
        this.devisRepository = devisRepository;
    }

    public HashMap<Integer, Devis> getAllDevis() {
        return devisRepository.findAll();
    }

    public Devis getDevisById(int id) {
        return devisRepository.findById(id);
    }

    public Devis getDevisByProjectID(int projectId) {
        return devisRepository.findByProjectID(projectId);
    }

    public int saveDevis(Devis devis) {
        return devisRepository.save(devis);
    }
}
