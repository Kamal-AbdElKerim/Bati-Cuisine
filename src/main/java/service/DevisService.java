package service;

import Repository.DAO.DevisRepository;
import model.Devis;

import java.sql.Connection;
import java.util.HashMap;

public class DevisService {
    private DevisRepository devisRepository;

    public DevisService( Connection connection) {
        this.devisRepository = new DevisRepository(connection);
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
