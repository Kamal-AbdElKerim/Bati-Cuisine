package model;

public class RemiseService {
    public double calculerRemise(Client client, double montantProjet) {
        if (client.isEstProfessionnel()) {
            return montantProjet * 0.10; // 10% discount for professionals
        }
        return 0.0; // No discount for regular clients
    }
}
