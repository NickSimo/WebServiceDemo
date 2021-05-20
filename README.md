**Consegna Esercizio**

Creare un nuovo endpoint **GET** _/clienti/estrazione-per-cf_ che accetta in input una _String_ "cf" ed estrarre il cliente corrispondente nella tabella _Clienti_.

**Sviluppo**

1) Creare un metodo in _ClienteRepository_ che utilizza la seguente query per estrarre il cliente

        SELECT * FROM Clienti WHERE codice_fiscale = 'codiceFiscale'
        
    1) Se la query non dovesse tornare risultati il metodo deve tornare _null_

2) Creare un metodo in _EstraiClientiService_ che utilizza _ClienteRepository_ per estrarre il cliente
    2) Se il codiceFiscale passato in input risulta vuoto o null lanciare un'Eccezione custom **InputErratoException**, che restituisce uno status InternalServerError con @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    2) In caso contrario, ritornare il cliente in output
    
3) Creare l'Endpoint in ClienteController che utilizza il metodo di EstraiClienteService appena creato


**Testing**

1) Testare con dei test Unitari il metodo in _EstraiClientiService_
    1) Testiamo il metodo passando il cf = AAA e usiamo un Mock sul repository
    1) Testiamo il metodo passando il cf = AAA ma il repository non trovera nessun record
    1) Testiamo il metodo passando il cf = null e ci aspettiamo una InputErratoException
    1) Testiamo il metodo passando il cf = "" e ci aspettiamo una InputErratoException
2) Testare con dei test di Integrazione il metodo in _ClienteRepository_
    2) Testiamo il metodo estraendo un record esistente e controllando i valori del cliente
    2) Testiamo il metodo provando ad estrarre un record che non esiste e ci aspettiamo null
3) Testare con dei test End to End l'enpoint in _ClienteController_
    3) Testiamo l'endpoint con un codice fiscale esistente, controlliamo i dati e lo stato http 200
    3) Testiamo l'endpoint non valorizzando l'input, controlliamo lo stato http 500