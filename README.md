**Consegna Esercizio**

Creare un nuovo endpoint **GET** _/clienti/estrazione-per-cf_ che accetta in input una _String_ "cf" ed estrare il cliente corrispondente nella tabella _Clienti_.

**Passaggi**

1) Creare un metodo in _ClienteRepository_ che utilizza la seguente query per estrarre il cliente

        SELECT * FROM Clienti WHERE codice_fiscale = 'codiceFiscale'
        
    1) Se la query non dovesse tornare risultati il metodo deve tornare _null_

2) Creare un metodo in _EstraiClientiService_ che utilizza _ClienteRepository_ per estrarre il cliente
    2) Se il codiceFiscale passato in input risulta vuoto o null lanciare un'Eccezione custom **InputErratoException**
    2) In caso contrario, ritornare il cliente in output
    
3) Creare l'Endpoint in ClienteController che utilizza il metodo di EstraiClienteService appena creato


**Testing**

1) Testare con dei test Unitari il metodo in _EstraiClientiService_
2) Testare con dei test di Integrazione il metodo in _ClienteRepository_
3) Testare con dei test End to End l'enpoint in _ClienteController_