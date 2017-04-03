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

        String[] stopWords = {"og", "i", "på", "du", "af", "semester", "at", "til", "med", "en", "for", "et", "er", "som",
                "der", "det", "kan", "de", "om", "skal", "eller", "den", "kurser", "arbejde", "inden", "dig", "vil", "har",
                "design", "and", "ects", "fra", "samt", "hvordan", "dette", "hvor", "fx", "får", "studerende",  "a", "som", "stand", "ca.",
                "Over", "ifølge", "derfor", "på tværs", "faktisk",
                "Efter", "bagefter", "igen", "imod", "aint", "alle",
                "Tillade", "giver", "næsten", "alene", "sammen", "allerede",
                "Også", "skønt", "altid", "AM", "blandt", "blandt", "en",
                "Og", "anden", "nogen", "nogen", "alligevel", "nogen", "noget",
                "Alligevel", "anyways", "hvor som helst", "hinanden", "synes", "værdsætter",
                "Passende", "er", "Arent", "omkring", "som", "side", "spørge", "spørger",
                "Forbundet", "ved", "til rådighed", "væk", "frygtelig", "være", "blev", "fordi",
                "Blive", "bliver til", "blive", "været", "før", "på forhånd", "bag", "at være",
                "Tror", "under", "ved siden af", "udover", "bedste", "bedre", "mellem", "hinsides", "både",
                "Korte", "men", "af", "Cmon", "cs", "kom", "kan", "kan ikke", "kan ikke", "kan ikke", "årsag", "årsager",
                "Vis", "helt sikkert", "ændringer", "klart", "co", "dk", "komme",
                "Kommer", "om", "hvorfor", "overveje", "overvejer", "indeholde",
                "Indeholdende", "indeholder", "tilsvarende", "kunne", "ikke kunne", "naturligvis", "i øjeblikket",
                "Absolut", "beskrevet", "på trods", "gjorde", "didnt", "anderledes", "gør", "gør",
                "Gør ikke", "gør", "dont", "færdig", "ned", "nedad", "under", "hver", "edu",
                "Fx", "otte", "enten", "andet", "andre steder", "nok", "helt", "specielt",
                "Et", "osv", "selv", "nogensinde", "hver", "alle", "alle", "alt", "overalt",
                "Ex", "præcis", "eksempel", "undtagen", "langt", "få", "FF", "femte", "første", "fem", "efterfulgt",
                "Efter", "følger", "for", "tidligere", "tidligere", "ud", "fire", "fra", "yderligere",
                "Desuden", "få", "får", "få", "givet", "giver", "gå", "går", "gå", "gået", "Fik", "fået", "hilsener", "måtte", "hadnt", "sker", "næppe", "har", "has not", "har",
                "Havent", "med", "han", "hes", "hello", "hjælp", "hvorfor", "hende", "her", "Heres", "herefter", "hermed", "heri ",
                "Herpå", "hendes", "selv", "hej", "ham", "sig selv", "hans", "hid", "forhåbentlig", "hvordan", "howbeit", "dog",
            "i", "id", "syg", "im", "ive", "det vil sige", "hvis", "ignoreret", "øjeblikkelig", "i", "for så vidt", "inc", "ja ",
            "indikerer", "angivet", "angiver", "indre", "omfang", "i stedet", "ind", "indad", "er", "Código", "det", "iTD", "itll",
            "sit", "sit", "selv", "bare", "holde", "holder", "holdt", "kender", "ved", "kendt", "sidste", "sidst ", "senere", "sidste",
            "den senere", "mindst", "mindre", "lest", "lad", "lader", "som", "kunne lide", "sandsynligt", "lille", "se", "søger", "ser",
            "ltd", "primært", "mange", "kan", "måske", "mig", "mener", "i mellemtiden", "blot", "kunne ", "mere", "i øvrigt", "de fleste",
            "for det meste", "meget", "must", "min", "mig selv", "navn", "nemlig", "nd", "nær", "næsten", "nødvendigt", "behov", "behov",
            "hverken", "aldrig", "alligevel", "ny", "næste", "ni", "nej", "ingen", "ikke " , "Ingen", "noone", "og heller ikke", "normal",
            "not", "intet", "roman", "nu", "ingen steder", "selvfølgelig", "af", "off", " ofte", "åh", "ok", "okay", "gamle", "til", "én gang",
            "en", "dem", "kun", "på", "eller", "andet" , "andre", "ellers", "burde", "vores", "vores", "os selv", "ud", "udenfor", "over", "samlet",
            "egen", "særlige", " især", "per", "måske", "placeret", "tak", "plus", "mulig", "formentlig", "sandsynligvis", "giver", "que", "helt", "qv" ,
            "snarere", "rd", "re", "virkelig", "rimelighed", "vedrørende", "uanset", "hilsen", "relativt", "henholdsvis", "højre", "sagde", " samme",
                "sav", "siger", "sige", "siger", "anden", "det andet", "se", "se", "synes", "syntes", "tilsyneladende", "synes", "set", "selv", "selv",
                "fornuftig", "sendt", "alvorlig", "alvorligt", "syv", "flere", "skal", "hun", "bør", " should", "da", "seks", "så", "nogle", "nogen",
                "en eller anden måde", "nogen", "noget", "engang", "nogle gange", "noget", "et sted" , "snart", "undskyld", "angivet", "angive", "Specificere",
                "stadig", "sub", "sådan", "sup", "sikker", "ts", "tage", "taget", "fortælle", "en tendens", "th", "end ", "tak", "tak", "thanx", "at", "thats",
                "thats", "den", "deres", "deres", "dem", "selv", "og derefter", "derfra", "der", "theres", "derefter", "derved", "derfor", "deri", "theres",
                "derefter", "disse", "de", "theyd", "theyll ", "de er", "ve", "tænke", "tredje", "dette", "grundig", "grundigt", "dem", "selvom", "tre", "til",
                "i hele", "igennem", "således", "til", "sammen", "for", "tog", "mod", "mod", "forsøgte", "forsøger", "sandhed", "prøve", "forsøger ", "to gange",
                "to", "un", "under", "desværre", "medmindre", "usandsynligt", "indtil", "til", "op", "efter", "os", "brug", "bruges", "anvendelige", "bruger",
                "ved hjælp af", "normalt", "værdi", "diverse", "meget", "via", "nemlig", "vs", "vil ", "ønsker", "var", "var ikke", "måde", "vi", "gift", "godt",
                "var", "weve", "velkommen", "godt", "gik", "var", "werent", "hvad", "hvad", "hvad", "når", "hvorfra", "hver gang", "hvor", "wheres" , "Hvorefter",
                "henviser", "hvorved", "hvor", "hvorefter", "hvor", "hvorvidt", "som", "mens", "hvorhen", "der", "whos", " hvem", "hele", "hvem", "hvis", "hvorfor",
                "vil", "villige", "ønske", "med",};


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