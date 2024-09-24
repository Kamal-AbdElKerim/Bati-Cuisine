package service;

import model.Project;

import java.util.HashMap;
import java.util.List;

import Repository.RepositoryImpl.ProjetRepository;

public class ProjectService {
    private ProjetRepository projetRepository;

    public ProjectService(ProjetRepository projetRepository) {
        this.projetRepository =  projetRepository;
    }

    public HashMap<Integer, Project> getAllProjects() {
        return projetRepository.findAll();
    }

    public Project getProjectById(int id) {
        return projetRepository.findById(id);
    }

    public List<Project> getProjectsNoCout() {
        return projetRepository.selectProjectsNoCout();
    }

    public int createProject(Project project) {
        return projetRepository.save(project);
    }

    public boolean updateProject(Project project, int projectId) {
        return projetRepository.updateProject(project, projectId);
    }

    public void displayProjectsInTable(List<Project> projects) {
        projetRepository.displayProjectsInTable(projects);
    }
}
