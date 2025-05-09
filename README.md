# **TheKnife**

Laboratorio A - UNI Insubria 2024, 2025

## 1. Introduzione al progetto

TheKnife è una piattaforma che consente di trovare ristoranti in tutto il mondo e selezionarli in base al luogo, alla tipologia del ristorante, alla sua fascia del prezzo, alla possibilità o meno di prenotare un tavolo o di ordinare da asporto. TheKnife, dunque, simula alcune delle funzionalità della celebre piattaforma TheFork.

## 2. Funzionalità

Il progetto TheKnife prevede diverse funzionalità divise per categoria di utente, tra cui:

* **Utente base**
  * Visualizzazione di ristoranti tramite appositi filtri (vicinanza, prezzo, prenotazione online, ...)
* **Utente registrato**
  * Funzionalità utente base
  * Visualizzazione e gestione dei ristoranti preferiti
  * Visualizzazione e gestione (aggiunta, modifica, eliminazione) delle recensioni aggiunte
* **Gestore**
  * Funzionalità utente base
  * Gestione (aggiunta, modifica eliminazione) dei ristoranti
  * Possibilità di visualizzare e rispondere alle recensioni dei propri ristoranti

## 3. Struttura del progetto

All'interno di questo progetto è possibile trovare:

* File autori.txt contenente il nome, cognome, matricola e sede di ogni membro del team e il link al repository GitHub
* Directory /src contenente il codice sorgente del progetto composta con il pattern MVC e DAO
* Directory /bin contenente il file eseguibile *.jar* dell'applicazione
* Directory /data conentente i file utilizzati dal progetto per funzionare
* Directory /doc contenente la documentazione del progetto in formato pdf (manuale utente e tecnico) e la JavaDoc
* Directory /library contenente le librerie utilizzate dal progetto
* File README.txt contenente le precise istruzioni per installare e compilare correttamente il progetto

## 4. La JavaDoc

E' uno strumento fondamentale per documentare i file sorgenti di un programma usando un formato predefinito che viene poi convertito in un html. Attenzione: JavaDoc è pensata per solo descrivere package, classe, metodi e attributi non codici!

```coffeescript
/**
* @param nome_parametro descrizione parametro (facoltativa)
* @return descrizione parametro (facoltativa, presente solo se il metodo non è void)
* @author autore della classe (presente solo se il commento viene posto alla classe)
*/

--- EXAMPLE --- 

/**
* Metodo statico per il caricamento dei ristoranti da un file JSON
* @param path Percorso del file in cui sono salvati i ristoranti
* @return Lista di ristoranti caricati
* @throws IOException Eccezione lanciata in caso di errore durante il caricamento
*/
public static List<Ristorante> caricaRistoranti(String path) throws IOException {
   Gson gson = new GsonBuilder().setPrettyPrinting().create();
   Path filePath = Paths.get(path);
   try (Reader reader = Files.newBufferedReader(filePath)) {
       return gson.fromJson(reader, new TypeToken<List<Ristorante>>() {}.getType());
   } catch (IOException e) {
       throw new IOException("Errore durante il caricamento dei ristoranti: " + e.getMessage(), e);
   }
}
```

## 5. Librerie usate

Per questo progetto è stata scelta la libreria** ****Gson** nella versione** ****2.10.1**, integrata nel progetto tramite la sezione** *Project Structure* di IntelliJ IDEA.

**Gson**, sviluppata da Google, è una libreria pensata per facilitare l'utilizzo del formato** **JSON** in Java, basandosi su due concetti fondamentali:

* **Serializzazione**: conversione di un oggetto Java in una rappresentazione JSON.
* **Deserializzazione**: conversione di un JSON in un oggetto Java.

```coffeescript
import com.google.gson.Gson;
import com.google.gson.JsonObject;
public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String jsonString = "{\"nome\":\"Mario\", \"età\":\"30\", \"cognome\":\"Rossi\"}";
        //stringa --> json
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        System.out.println("Oggetto JSON: " + jsonObject);
        //json --> stringa
        String jsonStringConverted = gson.toJson(jsonObject);
        System.out.println("Stringa JSON riconvertita: " + jsonStringConverted);
    }
}

```

## 6. Json in breve

Il **JSON** (acronimo di *JavaScript Object Notation*) è un formato leggero per lo scambio di dati, facilmente leggibile sia da esseri umani che da macchine. È ampiamente utilizzato nelle comunicazioni tra client e server, specialmente nelle applicazioni web, grazie alla sua struttura semplice e alla compatibilità con la maggior parte dei linguaggi di programmazione. La struttura di un file JSON si basa su coppie **chiave-valore** e supporta tipi di dati come stringhe, numeri, booleani, array e oggetti annidaticoffeescript

{
"nome": "Mario",
"anni": 30,
"cogonome": "Rossi",
"animali_preferiti":[
"cane",
"gatto"
]
}

L'accesso ad un oggetto json avviene tramite la notazione puntata o con indice (come se fosse un array):

```coffeescript
--- JAVA EXAMPLE ---
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) {
        JsonObject jsobj = new JsonObject();
        jsobj.addProperty("nome", "Mario");
        jsobj.addProperty("anni", 30);
        jsobj.addProperty("cognome", "Rossi");
        jsobj.addProperty("animali_preferiti", ["cane", "gatto"]);
        System.out.println("Nome: " + jsobj.get("nome").getAsString());
        System.out.println("Cognome: " + jsobj.get("cognome").getAsString());
    }
}

--- JAVASCRIPT EXAMPLE --- 
const json = {
   "nome": "Mario",
   "anni": 30,
   "cogonome": "Rossi",
   "animali_preferiti":[
        "cane",
        "gatto"
    ]
}
console.log("Nome: " + json.nome);
console.log("Cognome: " + json["cognome"]);
```

## 7. Pattern MVC e pattern DAO

Un ****pattern** (o** **design pattern**) è un modo per organizzare il codice all’interno di un programma. Aiuta a risparmiare tempo nella scrittura, lettura e manutenzione del codice, perché suddivide il programma in parti ben distinte, ognuna con un compito preciso.

Durante gli anni sono stati sviluppati diversi pattern tra cui:

* M.V.C (**Model View Controller**) che prevede una struttura ben definita
  * Directory model: in questa cartella sono presenti le classi che rappresentano gli oggetti
  * Directory view: gestisce l'interfaccia utente. In questo progetto per esempio ci saranno la view generale, per cliente registrato e gestore di ristoranti
  * Directory controller: gestisce l’input dell’utente e aggiorna Model e View di conseguenza.
* DAO (**Data Access Object**) che prevede una struttura semplice:
  * Directory DAO : contiene le classi che permettono al programma di salvare dati su database, file, ... in questo modo la logica del programma è separata dalla logica di salvataggio dei dati

## 8. Sistema di versionamento

Per questo progetto, essendo a gruppi, è stato utilizzato il programma** ****Git** insieme alla piattaforma** ****GitHub** per gestire il versionamento del codice. Questo ha permesso al team di:

* Collaborare, sviluppando in contemporanea parti di codice distinti
* Tenere traccia delle modifiche fatte nel tempo

Ogni membro del gruppo ha clonato il repository GitHub, sviluppato la propria parte di codice e, una volta completate le modifiche, ha salvato il lavoro nel repository condiviso. Ogni aggiornamento è stato accompagnato da un messaggio di commit chiaro e ben definito, utile per identificare facilmente le modifiche apportate.

## 9. Componenti gruppo

* Strazzullo Ciro Andrea
* Mongelli Matteo
* Rubini Riccardo Giovanni

## 10. Passi

1. Sviluppo delle classi model (Recensione, Ristorante, Utente (abstract), Cliente, Ristoratore)
2. Sviluppo delle classi DAO (GestoreFile)
3. Sviluppo delle classi view (VistaRistoratore, VistaBase, VistaCliente)
4. Sviluppo della classe controller (Main)
5. Testing generale
6. Redazione della documentazione tecnica e utente
7. Testing finale e consegna
