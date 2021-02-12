package org.miholc.spelling.service;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class WordNetSearch {

    public void testDictionary(String searchTerm, POS pos) throws IOException {

        // construct the URL to the Wordnet dictionary directory
//        String wnhome = System.getenv(" WNHOME ");
        String wnhome = "D:\\software\\WordNet\\2.1";
        String path = wnhome + File.separator + "dict";
        URL url = new URL("file", null, path);

        // construct the dictionary object and open it
        IDictionary dict = new Dictionary(url);
        dict.open();

        // look up first sense of the word "dog "
        IIndexWord idxWord = dict.getIndexWord(searchTerm, pos);

        List<IWordID> wordIDs = idxWord.getWordIDs();
        for (IWordID wordID : wordIDs) {
            IWord word = dict.getWord(wordID);
            System.out.println("Id = " + wordID);
            System.out.println(" Lemma = " + word.getLemma());
            System.out.println(" Gloss = " + word.getSynset().getGloss());
        }
    }
}
