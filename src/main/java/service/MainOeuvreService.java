package service;

import model.MainOeuvre;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import Repository.RepositoryImpl.MainOeuvreRepository;

public class MainOeuvreService {

    private MainOeuvreRepository mainOeuvreRepository;

    public MainOeuvreService(Connection connection) {
        this.mainOeuvreRepository = new MainOeuvreRepository(connection);
    }

    // Service to fetch all MainOeuvre entries
    public HashMap<Integer, MainOeuvre> getAllMainOeuvres() {
        return mainOeuvreRepository.findAll();
    }

    // Service to fetch MainOeuvre by Id
    public MainOeuvre getMainOeuvreById(int id) {
        return mainOeuvreRepository.findById(id);
    }

    // Service to save MainOeuvre
    public int saveMainOeuvre(MainOeuvre mainOeuvre) {
        return mainOeuvreRepository.save(mainOeuvre);
    }

    // Service to fetch MainOeuvre entries by Project ID
    public Map<Integer, MainOeuvre> getMainOeuvreByProjectId(int projectId) {
        return mainOeuvreRepository.findByProjectID(projectId);
    }

    // Service to insert a map of MainOeuvre objects for a specific project
    public void insertMainOeuvreData(HashMap<String, MainOeuvre> mainOeuvreMap, int projectId) {
        try {
            mainOeuvreRepository.insertData(mainOeuvreMap, projectId);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception properly in production
        }
    }

    // Additional service methods as per your business needs can be added here.
}
