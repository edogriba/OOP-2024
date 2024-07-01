 Tipologie prodotti  @media print { /\* adjusted to print the html to a single-page pdf \*/ body { font-size: 10pt; }

Tipologie prodotti
==================

Sviluppare un programma che simula le operazioni tra un venditore e i consumatori. Tutte le classi si trovano nel package **operations**. La classe principale è **Operations**.

La classe **TestApp** nel package **example** contiene degli esempi e fornisce i principali casi di test (ma non tutti). Le eccezioni sono lanciate tramite la classe **OException**; è richiesta la verifica solo delle condizioni esplicitamente specificate e non di tutte quelle possibili. Se un metodo lancia un'eccezione non viene fatta nessuna modifica ai dati presenti nella classe principale. Suggerimento: guardare la classe _TestApp_ per comprendere i requisiti dei metodi; dopo aver implementato ciascun metodo eseguire i test in _TestApp_ per verificare l'esito.

LA documentazione JDK è disponibile all'indirizzo: [https://oop.polito.it/api/](https://oop.polito.it/api/).

R1: Prodotti e prezzi
---------------------

È possibile aggiungere nuovi prodotti tramite il metodo **int addProductType (String productType, int n, int price) throws OException** che ha tre parametri: il tipo di prodotto, il numero di oggetti prodotto appartenenti al tipo, il prezzo di un singolo prodotto. È possibile assumere che i tipi sono etichette uniche che non contengono spazi. Il risultato è il prezzo totale di tutti i prodotti dello stesso tipo. Viene lanciata un'eccezione in caso di duplicati.

Il metodo **int getNumberOfProducts (String productType) throws OException** restituisce il numero di prodotti di un dato tipo; lancia un'eccezione se il tipo di prodotto non esiste.

Il metodo **groupingProductTypeByPrices()** raggruppa i tipi dei prodotti per prezzo (crescente). I tipi con lo stesso prezzo sono listati un ordine alfabetico.

R2: Sconti ed ordini dei clienti
--------------------------------

Il metodo **int addDiscount (String customer, int discount)** consente al venditore di aggiungere uno sconto per il cliente specificato. Lo stesso cliente può ricevere più sconti (additivi). Il risulto è il totale degli sconti permessi al cliente.

Gli ordini dei clienti possono essere inseriti tramite il metodo **int customerOrder(String customer, String ptpn, int discount) throws OException** che ha tre parametri: il cliente, i prodotti con le relative quantità, e lo sconto richiesto (eventualmente anche 0). Un esempio di uso nella classe _TestApp_ è il seguente: _customerOrder("ctr1", "tableX:2 loungeChair:1", 20)_; questa chiamata inserisce un ordine del cliente _ctr1_ per due tavoli (tableX è il tipo di prodotto) ed una loungeChair, chiedendo uno sconto di 20. La stringa di specifica ha due sottostringhe separate da uno spazio, ogni sottostringa è formata dal tipo di prodotto e dal numero di oggetti, separati da _:_. Il risultato è il prezzo totale, dato dalla somma dei prezzi di tutti i prodotti meno lo sconto.  
Possibili casi problematici: 1) se non tutte le quantità di prodtti sono disponibili il risultato è 0 e il cliente non acquista nulla. 2) se lo sconto richiesto eccede lo sconto disponibile per il cliente, viene lanciata un'eccezione.  
Se non ci sono problemi, il numero di prodotti è ridotto del numero di prodotti acquistati tramite l'ordine. Inoltre lo sconto disponibile per il cliente è decrementato dello sconto richiesto; lo sconto disponibile è quello che potrà essere usato nelle successive chiamta del metodo.

Il metodo **int getDiscountAvailable (String customer)** restituisce lo sconto disponibile per un cliente (può essere 0).

R3: Valutazioni e punteggi
--------------------------

Il metodo **int evalByCustomer (String customer, String productType, int score) throws OException** permette ad un cliente di dare un punteggio nell'intervallo 4..10 ad un certo tipo di prodotto. Viene lanciata un'eccezione se il cliente non ha mai acquistato quel tipo di prodotto oppure ha già assegnato un punteggio al tipo, o se il punteggio non è nell'intervallo consentito. Il risutato è il numero di tipi di prodotto a cui il cliente ha assegnato un punteggio.

Il metodo **int getScoreFromProductType (String customer, String productType) throws OException** mostra il punteggio asegnato da un cliente al prodotto dato. Viene lanciata un'eccezione se il cliente non ha mai assegnato un punteggio al tipo di prodotto.

Il metodo **groupingCustomersByScores(String productType)** raggruppa il clienti per punteggi crescenti assegnati al tipo di prodotto dato. I clienti con pari punteggio sono in una lista ordinata alfabeticamente.

R4: Statistics
--------------

Il metodo **groupingCustomersByNumberOfProductsPurchased()** raggruppa i clienti per numero crescente di oggetti comprati. A parità di numero, i clienti sono listati in ordine alfabetico. I clienti che non hanno effettuato ordini non sono riportati.

Il metodo **largestExpenseForCustomer ()** fornisce la maggior spesa in un singolo ordine per ciascun cliente (in ordine alfabetico). I clienti che non hanno effettuato ordini non sono riportati.