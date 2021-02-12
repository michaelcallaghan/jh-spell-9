package org.miholc.spelling.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.miholc.spelling.service.PgcGenerator.dataPath;

public class SpellingUtils {
    public static String extractWordFromSpokenWord(String spokenWord) {
        int usPos = spokenWord.indexOf('_');
        if (usPos != -1) {
            return spokenWord.substring(0, usPos);
        }
        else {
            return spokenWord;
        }
    }

    public static void createSortedUniqueWordsList() throws IOException {
        TreeSet<String> sortedWords = new TreeSet<>();
        try (PrintWriter pw = new PrintWriter((new FileWriter(dataPath + "\\dictspellingsorteduniquewordsPGCetc.txt", true)))) {
            Files.readAllLines(Paths.get(dataPath + "\\" + "dictspellingwordsPGCetc.txt"), StandardCharsets.UTF_8).stream().forEach(w->sortedWords.add(w));
            sortedWords.stream().forEach(w -> pw.println(w));
        }

    }

    public static void createSortedUniqueWordsListFromList(List<String> wordList) throws IOException {
        TreeSet<String> sortedWords = new TreeSet<>();
        try (PrintWriter pw = new PrintWriter((new FileWriter(dataPath + "\\govsorteduniquewords.txt", true)))) {
           wordList.stream().forEach(w->sortedWords.add(w));
            sortedWords.stream().forEach(w -> pw.println(w));
        }

    }

    public static void createWordTestFile() throws IOException {
        try (PrintWriter pw = new PrintWriter((new FileWriter(dataPath + "\\dbessWordsTest1.txt", true)))) {
            Files.readAllLines(Paths.get(dataPath + "\\" + "matchedWords1592669146643.txt"), StandardCharsets.UTF_8).stream().filter(l->l.indexOf(':')!=-1).collect(Collectors.toList()).forEach(l->pw.println(l));
            Files.readAllLines(Paths.get(dataPath + "\\" + "unmatchedWords1592669146626.txt"), StandardCharsets.UTF_8).stream().filter(l->l.indexOf(':')!=-1).collect(Collectors.toList()).forEach(l->pw.println(l));
        }

    }

    public static void main(String[] args) throws IOException {
//        System.out.println(extractWordFromSpokenWord("a_s"));
        createWordTestFile();
//        createSortedUniqueWordsList();
//        createSortedUniqueWordsListFromList(PgcGenerator.mainTestTerms);
    }
}
