DROP TABLE IF EXISTS clienti;

CREATE TABLE clienti
(
    id                    int AUTO_INCREMENT PRIMARY KEY,
    nome                  VARCHAR(50),
    cognome               VARCHAR(50),
    codice_fiscale        VARCHAR(16),
    indirizzo_residenza   VARCHAR(100)
);
