-- Create Client Table
CREATE TABLE clients (
    client_id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    adresse VARCHAR(255),
    telephone VARCHAR(15),
    est_professionnel BOOLEAN NOT NULL,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

-- Create Projet Table
CREATE TABLE projets (
    projet_id SERIAL PRIMARY KEY,
    nom_projet VARCHAR(255) NOT NULL,
    marge_beneficiaire DECIMAL(5, 2) NOT NULL, -- Beneficiary margin applied to the total cost
    cout_total DECIMAL(10, 2) DEFAULT 0,
    etat_projet VARCHAR(20) CHECK (etat_projet IN ('EN_COURS', 'TERMINE', 'ANNULE')) NOT NULL,
    client_id INT,
    surface_cuisine DECIMAL(5, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE
);

-- Create Devis Table
CREATE TABLE devis (
    devis_id SERIAL PRIMARY KEY,
    montant_estime DECIMAL(10, 2) NOT NULL,
    date_emission DATE NOT NULL,
    date_validite DATE NOT NULL,
    accepte BOOLEAN DEFAULT FALSE,
    projet_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (projet_id) REFERENCES projets(projet_id) ON DELETE CASCADE
);

-- Create GestionDesComposants Table
CREATE TABLE gestion_des_composants (
    composant_id SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    cout_unitaire DECIMAL(10, 2) NOT NULL,
    quantite DECIMAL(10, 2) NOT NULL,
    type_composant VARCHAR(20) CHECK (type_composant IN ('MATERIEL', 'MAIN_D_OEUVRE')) NOT NULL,
    taux_tva DECIMAL(5, 2) NOT NULL,
    projet_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (projet_id) REFERENCES projets(projet_id) ON DELETE CASCADE
);

-- Create Materiaux Table
CREATE TABLE materiaux (
    id SERIAL PRIMARY KEY,
    cout_transport DECIMAL(10, 2) DEFAULT 0,
    coefficient_qualite DECIMAL(5, 2) DEFAULT 1,
    composants_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (composants_id) REFERENCES gestion_des_composants(composant_id) ON DELETE CASCADE
);

-- Create MainOeuvre Table
CREATE TABLE main_oeuvre (
    id SERIAL PRIMARY KEY,
    taux_horaire DECIMAL(10, 2), -- Only for labor components
    heures_travail DECIMAL(5, 2), -- Only for labor components
    productivite_ouvrier DECIMAL(5, 2),
    composants_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (composants_id) REFERENCES gestion_des_composants(composant_id) ON DELETE CASCADE
);
