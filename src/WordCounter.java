// File: WordCounter.java
// Program from Section 5.7 to illustrate the use of TreeMaps and Iterators.
// The program opens and reads a file called words.txt. Each line in this
// file should consist of one or more English words separated by spaces.
// The end of each line must not have any extra spaces after the last word.
// The program reads the file and then a table is printed of
// all words and their counts.

import java.util.*;  // Provides TreeMap, Iterator, Scanner
import java.io.*;    // Provides FileReader, FileNotFoundException

public class WordCounter
{
    public static void main(String[ ] args)
    {
        TreeMap<String, Integer> frequencyData = new TreeMap<String, Integer>( );

        readWordFile(frequencyData);
        printAllCounts(frequencyData);
    }

    public static int getCount
            (String word, TreeMap<String, Integer> frequencyData)
    {
        if (frequencyData.containsKey(word))
        {  // The word has occurred before, so get its count from the map
            return frequencyData.get(word); // Auto-unboxed
        }
        else
        {  // No occurrences of this word
            return 0;
        }
    }

    public static void printAllCounts(TreeMap<String, Integer> frequencyData)
    {
        System.out.println("-----------------------------------------------");
        //System.out.println("    Occurrences    Word");

        String[] stopWords = {"og", "i", "p�", "du", "af", "semester", "at", "til", "med", "en", "for", "et", "er", "som",
                "der", "det", "kan", "de", "om", "skal", "eller", "den", "kurser", "arbejde", "inden", "dig", "vil", "har",
                "design", "and", "ects", "fra", "samt", "hvordan", "dette", "hvor", "fx", "f�r", "studerende",  "a", "som", "stand", "ca.",
                "Over", "if�lge", "derfor", "p� tv�rs", "faktisk",
                "Efter", "bagefter", "igen", "imod", "aint", "alle",
                "Tillade", "giver", "n�sten", "alene", "sammen", "allerede",
                "Ogs�", "sk�nt", "altid", "AM", "blandt", "blandt", "en",
                "Og", "anden", "nogen", "nogen", "alligevel", "nogen", "noget",
                "Alligevel", "anyways", "hvor som helst", "hinanden", "synes", "v�rds�tter",
                "Passende", "er", "Arent", "omkring", "som", "side", "sp�rge", "sp�rger",
                "Forbundet", "ved", "til r�dighed", "v�k", "frygtelig", "v�re", "blev", "fordi",
                "Blive", "bliver til", "blive", "v�ret", "f�r", "p� forh�nd", "bag", "at v�re",
                "Tror", "under", "ved siden af", "udover", "bedste", "bedre", "mellem", "hinsides", "b�de",
                "Korte", "men", "af", "Cmon", "cs", "kom", "kan", "kan ikke", "kan ikke", "kan ikke", "�rsag", "�rsager",
                "Vis", "helt sikkert", "�ndringer", "klart", "co", "dk", "komme",
                "Kommer", "om", "hvorfor", "overveje", "overvejer", "indeholde",
                "Indeholdende", "indeholder", "tilsvarende", "kunne", "ikke kunne", "naturligvis", "i �jeblikket",
                "Absolut", "beskrevet", "p� trods", "gjorde", "didnt", "anderledes", "g�r", "g�r",
                "G�r ikke", "g�r", "dont", "f�rdig", "ned", "nedad", "under", "hver", "edu",
                "Fx", "otte", "enten", "andet", "andre steder", "nok", "helt", "specielt",
                "Et", "osv", "selv", "nogensinde", "hver", "alle", "alle", "alt", "overalt",
                "Ex", "pr�cis", "eksempel", "undtagen", "langt", "f�", "FF", "femte", "f�rste", "fem", "efterfulgt",
                "Efter", "f�lger", "for", "tidligere", "tidligere", "ud", "fire", "fra", "yderligere",
                "Desuden", "f�", "f�r", "f�", "givet", "giver", "g�", "g�r", "g�", "g�et", "Fik", "f�et", "hilsener", "m�tte", "hadnt", "sker", "n�ppe", "har", "has not", "har",
                "Havent", "med", "han", "hes", "hello", "hj�lp", "hvorfor", "hende", "her", "Heres", "herefter", "hermed", "heri ",
                "Herp�", "hendes", "selv", "hej", "ham", "sig selv", "hans", "hid", "forh�bentlig", "hvordan", "howbeit", "dog",
            "i", "id", "syg", "im", "ive", "det vil sige", "hvis", "ignoreret", "�jeblikkelig", "i", "for s� vidt", "inc", "ja ",
            "indikerer", "angivet", "angiver", "indre", "omfang", "i stedet", "ind", "indad", "er", "C�digo", "det", "iTD", "itll",
            "sit", "sit", "selv", "bare", "holde", "holder", "holdt", "kender", "ved", "kendt", "sidste", "sidst ", "senere", "sidste",
            "den senere", "mindst", "mindre", "lest", "lad", "lader", "som", "kunne lide", "sandsynligt", "lille", "se", "s�ger", "ser",
            "ltd", "prim�rt", "mange", "kan", "m�ske", "mig", "mener", "i mellemtiden", "blot", "kunne ", "mere", "i �vrigt", "de fleste",
            "for det meste", "meget", "must", "min", "mig selv", "navn", "nemlig", "nd", "n�r", "n�sten", "n�dvendigt", "behov", "behov",
            "hverken", "aldrig", "alligevel", "ny", "n�ste", "ni", "nej", "ingen", "ikke " , "Ingen", "noone", "og heller ikke", "normal",
            "not", "intet", "roman", "nu", "ingen steder", "selvf�lgelig", "af", "off", " ofte", "�h", "ok", "okay", "gamle", "til", "�n gang",
            "en", "dem", "kun", "p�", "eller", "andet" , "andre", "ellers", "burde", "vores", "vores", "os selv", "ud", "udenfor", "over", "samlet",
            "egen", "s�rlige", " is�r", "per", "m�ske", "placeret", "tak", "plus", "mulig", "formentlig", "sandsynligvis", "giver", "que", "helt", "qv" ,
            "snarere", "rd", "re", "virkelig", "rimelighed", "vedr�rende", "uanset", "hilsen", "relativt", "henholdsvis", "h�jre", "sagde", " samme",
                "sav", "siger", "sige", "siger", "anden", "det andet", "se", "se", "synes", "syntes", "tilsyneladende", "synes", "set", "selv", "selv",
                "fornuftig", "sendt", "alvorlig", "alvorligt", "syv", "flere", "skal", "hun", "b�r", " should", "da", "seks", "s�", "nogle", "nogen",
                "en eller anden m�de", "nogen", "noget", "engang", "nogle gange", "noget", "et sted" , "snart", "undskyld", "angivet", "angive", "Specificere",
                "stadig", "sub", "s�dan", "sup", "sikker", "ts", "tage", "taget", "fort�lle", "en tendens", "th", "end ", "tak", "tak", "thanx", "at", "thats",
                "thats", "den", "deres", "deres", "dem", "selv", "og derefter", "derfra", "der", "theres", "derefter", "derved", "derfor", "deri", "theres",
                "derefter", "disse", "de", "theyd", "theyll ", "de er", "ve", "t�nke", "tredje", "dette", "grundig", "grundigt", "dem", "selvom", "tre", "til",
                "i hele", "igennem", "s�ledes", "til", "sammen", "for", "tog", "mod", "mod", "fors�gte", "fors�ger", "sandhed", "pr�ve", "fors�ger ", "to gange",
                "to", "un", "under", "desv�rre", "medmindre", "usandsynligt", "indtil", "til", "op", "efter", "os", "brug", "bruges", "anvendelige", "bruger",
                "ved hj�lp af", "normalt", "v�rdi", "diverse", "meget", "via", "nemlig", "vs", "vil ", "�nsker", "var", "var ikke", "m�de", "vi", "gift", "godt",
                "var", "weve", "velkommen", "godt", "gik", "var", "werent", "hvad", "hvad", "hvad", "n�r", "hvorfra", "hver gang", "hvor", "wheres" , "Hvorefter",
                "henviser", "hvorved", "hvor", "hvorefter", "hvor", "hvorvidt", "som", "mens", "hvorhen", "der", "whos", " hvem", "hele", "hvem", "hvis", "hvorfor",
                "vil", "villige", "�nske", "med",};


        int cnt = 1;
        for(String word : frequencyData.keySet( ))
        {
            cnt++;
            for (int i = 0; i < stopWords.length; i++) {
                if(!word.equalsIgnoreCase(stopWords[i])) {
                    System.out.printf("%1d, %s\n", frequencyData.get(word), word);

                    break;
                }
            }
        }

            System.out.println(cnt + " -----------------------------------------------");
    }

    public static void readWordFile(TreeMap<String, Integer> frequencyData)
    {
        Scanner wordFile;
        String word;     // A word read from the file
        Integer count;   // The number of occurrences of the word

        try
        {
            wordFile = new Scanner(new FileReader("C:\\Users\\Nicolai\\Documents\\Github\\Web2JSON\\output\\total.txt"));
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e);
            return;
        }

        while (wordFile.hasNext( ))
        {
            // Read the next word and get rid of the end-of-line marker if needed:
            word = wordFile.next( );

            // Get the current count of this word, add one, and then store the new count:
            count = getCount(word, frequencyData) + 1;
            frequencyData.put(word, count);
        }
    }

}