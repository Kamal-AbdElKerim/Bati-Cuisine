package service;

import model.Materiaux;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import Repository.RepositoryImpl.MateriauxRepository;

public class MateriauxService {
    private MateriauxRepository materiauxRepository;

    public MateriauxService(Connection connection) {
        this.materiauxRepository = new MateriauxRepository(connection);
    }

    public HashMap<Integer, Materiaux> getAllMateriaux() {
        return materiauxRepository.findAll();
    }

    public Materiaux getMateriauxById(int id) {
        return materiauxRepository.findById(id);
    }

    public Map<Integer, Materiaux> getMateriauxByProjectId(int projectId) {
        return materiauxRepository.findByProjectID(projectId);
    }

    public void saveMateriaux(Materiaux materiaux) {
        materiauxRepository.save(materiaux);
    }

    public void insertMateriauxData(HashMap<String, Materiaux> materiauxMap, int projetID) throws Exception {
        materiauxRepository.insertData(materiauxMap, projetID);
    }
}
