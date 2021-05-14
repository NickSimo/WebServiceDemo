DROP TABLE IF EXISTS clienti;

CREATE TABLE clienti
(
    id                    int AUTO_INCREMENT PRIMARY KEY,
    nome                  VARCHAR(50),
    cognome               VARCHAR(50),
    codice_fiscale        CHAR(16),
    data_nascita          DATE,
    indirizzo_residenza   VARCHAR(100),
    comune_residenza      VARCHAR(50),
    fl_residenza_estero      CHAR(1),
    indirizzo_domicilio   VARCHAR(100),
    comune_domicilio      VARCHAR(50),
    fl_domicilio_estero   CHAR(1),
    numero_carta_identita VARCHAR(30),
    nazione               VARCHAR(30)
);
