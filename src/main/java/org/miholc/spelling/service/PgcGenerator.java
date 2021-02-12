package org.miholc.spelling.service;

import edu.mit.jwi.item.POS;
import org.miholc.spelling.domain.Grapheme;
import org.miholc.spelling.domain.Pgc;
import org.miholc.spelling.domain.Phoneme;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class PgcGenerator {
    public static final List<String> singleTestTerm = Arrays.asList("dyke");
    public static final List<String> mainTestTerms = Arrays.asList("rogue", "catalogue", "league", "guest", "ghost", "gut", "accident", "accidentally", "actual", "actually", "address", "answer", "appear", "arrive", "believe",
        "bicycle", "breath", "breathe", "build", "busy", "business", "calendar", "caught", "centre", "century", "certain", "circle",
        "complete", "consider", "continue", "decide", "describe", "different", "difficult", "disappear", "early", "earth", "eight",
        "eighth", "enough", "exercise", "experience", "experiment", "extreme", "famous", "favourite", "February", "forward", "forwards",
        "fruit", "grammar", "group", "guard", "guide", "heard", "heart", "height", "history", "imagine", "increase", "important",
        "interest", "island", "knowledge", "learn", "length", "library", "material", "medicine", "mention", "minute", "natural",
        "naughty", "notice", "occasionally", "often", "opposite", "ordinary", "particular", "peculiar", "perhaps",
        "popular", "position", "possess", "possession", "possible", "potatoes", "pressure", "probably", "promise", "purpose", "quarter",
        "question", "recent", "regular", "reign", "remember", "sentence", "separate", "special", "straight", "strange", "strength",
        "suppose", "surprise", "therefore", "though", "although", "thought", "through", "various", "weight", "woman", "women",
        "accommodate", "accompany", "according", "achieve", "aggressive", "amateur", "ancient", "apparent", "appreciate", "attached",
        "available", "average", "awkward", "bargain", "bruise", "category", "cemetery", "committee", "communicate", "community",
        "competition", "conscience", "conscious", "controversy", "convenience", "correspond", "critic", "criticise", "curiosity",
        "definite", "desperate", "determined", "develop", "dictionary", "disastrous", "embarrass", "environment", "equip", "equipped",
        "equipment", "especially", "exaggerate", "excellent", "existence", "explanation", "familiar", "foreign", "forty", "frequently",
        "government", "guarantee", "harass", "hindrance", "identity", "immediate", "immediately", "individual", "interfere", "interrupt",
        "language", "leisure", "lightning", "marvellous", "mischievous", "muscle", "necessary", "neighbour", "nuisance", "occupy", "occur",
        "opportunity", "parliament", "persuade", "physical", "prejudice", "privilege", "profession", "programme", "pronunciation",
        "queue", "recognise", "recommend", "relevant", "restaurant", "rhyme", "rhythm", "sacrifice", "secretary", "shoulder", "signature",
        "sincere", "sincerely", "soldier", "stomach", "sufficient", "suggest", "symbol", "system", "temperature", "thorough", "twelfth",
        "variety", "vegetable", "vehicle", "yacht",
        "the", "that", "not", "look", "put", "and", "with", "then", "don’t", "could", "a", "all", "were", "come", "house", "to", "we", "go", "will",
        "old", "said", "can", "little", "into", "too",
        "in", "are", "as", "back", "by",
        "he", "up", "no", "from", "day",
        "I", "had", "mum", "children", "made",
        "of", "my", "one", "him", "time",
        "it", "her", "them", "Mr", "I’m",
        "was", "what", "do", "get", "if",
        "you", "there", "me", "just", "help",
        "they", "out", "down", "now", "Mrs",
        "on", "this", "dad", "came", "called",
        "she", "have", "big", "oh", "here",
        "is", "went", "when", "about", "off",
        "for", "be", "it’s", "got", "asked",
        "at", "like", "see", "their", "saw",
        "his", "some", "looked", "people", "make",
        "but", "so", "very", "your", "an", "water", "other", "fast", "air", "use", "away", "food", "only", "trees", "along", "good", "fox", "many", "bad", "plants", "want", "through", "laughed", "tea", "dragon", "over", "way", "let’s", "top", "pulled", "how", "been", "much", "eyes", "we’re", "did", "stop", "suddenly", "fell", "fly", "man", "must", "told", "friends", "grow", "going", "red", "another", "box", "where", "door", "great", "dark", "would", "right", "why", "grandad", "or", "sea", "cried", "there’s", "took", "these", "keep", "looking", "school", "began", "room", "end", "think", "boy", "last", "than", "home", "animals", "jumped", "best", "who", "never", "because", "better", "didn’t", "next", "even", "hot", "ran", "first", "am", "sun", "know", "work", "before", "across", "bear", "lots", "gran", "gone", "can’t", "need", "clothes", "hard", "again", "that’s", "tell", "floppy", "cat", "baby", "key", "really",
        "long", "fish", "fun", "wind", "things", "gave", "place", "wish", "new", "mouse", "mother", "eggs", "after", "something", "sat", "once", "wanted", "bed", "boat", "please", "eat", "may", "window", "thing", "everyone", "still", "sleep", "stopped", "our", "found", "feet", "ever", "two", "live", "morning", "miss", "has", "say", "queen", "most", "yes", "soon", "each", "cold", "play", "night", "book", "park", "take", "narrator", "its", "lived", "thought", "small", "green", "birds", "dog", "car", "different", "duck", "well", "couldn’t", "let", "horse", "find", "three", "girl", "rabbit", "more", "head", "which", "white", "I’ll", "king", "inside", "coming", "round", "town", "run", "he’s", "tree", "I’ve", "any", "river", "magic", "around", "under", "liked", "shouted", "every", "hat", "giant", "us", "garden", "snow", "looks");

    //    public Map<String, String> pgcMap = Map.ofEntries(
//    );
    public static final Map<String, String> pgcMap = new HashMap<>();
    public static final Set<String> VOWEL_PHONEMES_THAT_MAY_REQUIRE_FINAL_E = Set.of("eɪ", "iː", "aɪ", "əʊ", "uː", "juː", "j");
    public static final Set<String> SHORT_VOWEL_PHONEMES = Set.of("æ", "e", "I", "ɒ", "ʌ", "ʊ", "ə");
    public static final Set<String> OTHER_DIPHTHONG_VOWEL_PHONEMES = Set.of("aʊ", "ɔɪ", "eə", "ɪə", "ʊə");
    public static final Set<String> OTHER_LONG_PURE_VOWEL_PHONEMES = Set.of("ɑː", "ɜː", "ɔː");
    public static final Set<String> CONSONANT_PHONEMES = Set.of("b", "k", "tʃ", "d", "f", "ɡ", "h", "dʒ", "m", "n", "ŋ", "p", "r", "s", "ʃ", "ʒ", "t", "θ", "ð", "v", "w", "j", "z");
//    public static String IPA_PARSER_REGEX = "jʊə|ɡjə|jə|ɡz(?!$)|aɪə|aɪə|aʊə|ɪər|ɪə(?!=r)|ʊə|ɔɪ|əʊ|eər|eə(?!=r)|eɪ|aɪ|aʊ|wʌ|tʃ|tθ|ks|kʃ|dʒ|əl$|juː|(?<!eə|ə)rəliː$|(?<![əæeɪɒʊʌ])riː$|b|t|ɪ|k|d|ð|f|ɡ(?!jə)|h|l|m|n|ŋ|θ|p|r|s|ʃ|tʃ|v|w|wʌ|j|z|ʒ|aː|ɒ|æ|e|iː|ɑː|ɔː|ʊ|ʌ|uː|ɜː|ə";
    public static String IPA_PARSER_REGEX = "jʊə|ɡjə|jə|ɡz(?!$)|aɪə|aʊə|ɪə|ʊə|ɔɪ|əʊ|eər|eə(?!r)|eɪ|aɪ|aʊ|wʌ|tʃ|tθ|ks|kʃ|dʒ|əl$|juː|(?<!eə|ə)rəliː$|(?<![əæeɪɒʊʌ])riː$|b|t|ɪ|k|d|ð|f|ɡ(?!jə)|h|l|m|n|ŋ|θ|p|r|s|ʃ|tʃ|v|w|wʌ|j|z|ʒ|aː|ɒ|æ|e|iː|ɑː|ɔː|ʊ|ʌ|uː|ɜː|ə";

    public static final String dataPath = "D:\\dev\\spelling\\data\\pgcs";
    public static final String wordListsPath = "D:\\dev\\spelling\\data\\wordListsetc";
    public static final String soundsPath = "D:\\dev\\spelling\\data\\sounds";

    public static final String gMappingDefault = "((?<=[aeiou]{2})gue$|(?<=[aeiou]{2})gue(?=s)|gg|gu|gh|g)";
    public static final String gMappingModForRogue = "(gue$|gue(?=s)|gg|gu|gh|g)";
    public static final String tMappingDefault = "(tte$|tt|(?<=e)ct(?=i)|pt|Th|tw|ed$|t)";
    public static final String tMappingModForMinute = "(tte$|tt|te(?!ur|er)|(?<=e)ct(?=i)|pt|Th|tw|ed$|t)";

    public PgcGenerator() {
        initializeDefaultPgcMap();
    }

    public void initializeDefaultPgcMap() {
        pgcMap.clear();
        pgcMap.put("b", "(bb|bh|bu|b)");
        pgcMap.put("juː", "(you|ue|ui|u|eau|iew|ew|eu)");
//        pgcMap.put("t", "(tt|pt|Th|tw|ed$|te(?!ur)|t)");
        pgcMap.put("t", tMappingDefault); // tte for French, but beware rotted etc.
        pgcMap.put("ɪ", "(hi|ai(?=n)|ie|ia|i|e|y|a|o|u)");
        pgcMap.put("əl", "(le|al|el|il|ol|ul)");
//        pgcMap.put("ən", "(ain|en|n)"); // NB this might break things - meant to help with didn't etc. minus apos (IT DOES!)
        pgcMap.put("k", "(cc|ck|ch|c|k|q)");
        pgcMap.put("ks", "(xc|x|ks|cks|cs|cc)");
        pgcMap.put("kʃ", "(ksh|xi|x)"); // for anxious, luxury etc.
        pgcMap.put("d", "(ddh|dd|de|d|ed)");
        pgcMap.put("ð", "(the|th)");
        pgcMap.put("f", "(ff|f|ph|ft|gh)");
        pgcMap.put("ɡ", gMappingDefault);
        pgcMap.put("ɡjə", "(gu)");
        pgcMap.put("jə", "(eu|u)");
        pgcMap.put("h", "(h|wh|j)");
        pgcMap.put("ɡz", "(xh|x)");
        pgcMap.put("dʒ", "(j|dge|dg|di|d|gg|ge|g)"); // add <gi> for legion, allegiance etc.
        pgcMap.put("l", "(lle(?=$|s$)|ll|le(?=$|s$)|l)");
        pgcMap.put("m", "(mme|mm|mb|mn|me|m)");
        pgcMap.put("n", "(nn|gn|kn|ne(?![dw])|n)");
        pgcMap.put("ŋ", "(ngue|ng|n(?=xi|[gk]))");
        pgcMap.put("θ", "(th)");
        pgcMap.put("tθ", "(th)");
        pgcMap.put("p", "(pp|ph|p)");
        pgcMap.put("r", "(rr|rh|wr|r)");
        pgcMap.put("s", "(ss|sc|st(?=le|en|n|m|b|c|ly)|se|sw|ce|s|c|z)");
        pgcMap.put("ʃ", "(ssi|sci|ss|sh|si|ti|ch|ci|c|s)");
//        pgcMap.put("tʃ", "(tch|ch|ti|t(?=ure|ual))");
        pgcMap.put("tʃ", "(tch|cz|ch|ti|th|t(?=u))");
        pgcMap.put("v", "(ve|v|(?<=o)f)");
        pgcMap.put("w", "(wh|w|(?<=[qgs])u)");
        pgcMap.put("wʌ", "(^o)");
        pgcMap.put("j", "(y|i|u|h)");
        pgcMap.put("z", "(zz|ze|cz|z|se|ss|si|s)");
        pgcMap.put("ʒ", "(si|s|ge$|g)"); // add <g> for regime? <ge> for mirage
        pgcMap.put("ɑː", "(ear|er|are|ar|al|au|a)");
        pgcMap.put("ɒ", "(ach|ou|ow|au|^ho|o|a|i|e)");
        pgcMap.put("aʊ", "(ou|ow)");
        pgcMap.put("aʊə", "(^hour|our|ower)");
        pgcMap.put("æ", "(au(?=gh)|a)");
        pgcMap.put("aɪ", "(igh|eigh|ei|eye|ye|ae|y|is|i)"); // ie?
        pgcMap.put("aɪə", "(ire|yre|iah|io|ie)"); // needs postprocessing to assign the /aɪ/ and the /ə/
        pgcMap.put("e", "(ea|ei|ay|ai|ie|e|a)");
        pgcMap.put("eɪ", "(aigh|ai|ay|ea|et|eigh|eig|ey|a|é)");
        pgcMap.put("eə", "(air|are|ear|ere|eir|ar|a)");
        pgcMap.put("eər", "(air|are|ear|ere|eir|ar|a)");
        pgcMap.put("əʊ", "(ough|ow|ou|oa|oh$|o)");
        pgcMap.put("ɪə", "(eer|ere|ear|ier|ea|er)");
        pgcMap.put("ɪər", "(eer|ear|ier|er)"); // not right
        pgcMap.put("iː", "(ee|ea|ey|eo|ie|ae|oe|i|e|y)"); // add <ae> for algae? <oe> for amoeba
        pgcMap.put("riː", "(ory|ary|ery|ree|rie|ry)"); // NOT?  added ree for pedigree, rie for lingerie, ry for hungry etc, otherwise for elided vowels
        pgcMap.put("rəliː", "(arily)");
        pgcMap.put("ɔː", "(ough|augh|our|oar|oor|oer|ore|au|aw|ar|or|a)");
        pgcMap.put("ɔɪ", "(oi|oy)");
        pgcMap.put("ʊ", "(oo|oul|o|u)");
        pgcMap.put("ʌ", "(oo|ou|o|u)");
        pgcMap.put("ʊə", "(oor|our|u)");
        pgcMap.put("jʊə", "(u(?=r(?!e))(?!e)|ure)");
        pgcMap.put("uː", "(ough|ue|ui|ou|oe|eu|ew|oo|u|o)");
        pgcMap.put("ɜː", "(our|eur|ear|ere|err|er|ir|or|ur)");
        pgcMap.put("ə", "(ough|eur(?=$|s)|ire|ure|ur|our|or|ou|re|ar|au|ae|ei|er|ai(?=n)|ah|e|o|i|a|u)");
    }

    public String createRegexFromPhonemeList(List<Phoneme> phonemes) {
        String regex = "";
        regex = "(" + phonemes.stream().map(p -> p.getIpa()).map(ipa -> pgcMap.get(ipa)).collect(joining("")) + ")";
        return regex;
    }

    public String createRegexFromPhonemeStringList(List<String> phonemes, boolean splitEpresent, int splitEPhonemeIndex) {
        String regex = "(?i)(";
//        regex = "(" + phonemes.stream().map(ipa -> pgcMap.get(ipa)).collect(joining("")) + ")";
        for (int i = 0; i < phonemes.size(); i++) {
            String phoneme = phonemes.get(i);
            regex = regex + pgcMap.get(phoneme);
            if (splitEpresent && i == splitEPhonemeIndex+1) {
                regex = regex + "e";
            }
        }
        regex = regex + ")";
        return regex;
    }

    public List<Pgc> generatePgcsFromIpaStringAndWord(String ipaString, String spokenWord, PrintWriter unmatchedWriter, PrintWriter matchedWriter) throws IOException {
        System.out.println(ipaString + " : " + spokenWord);
        List<Pgc> pgcs = new ArrayList<>();
        List<String> pgcStrings = new ArrayList<>();
        String word = null;
        word = SpellingUtils.extractWordFromSpokenWord(spokenWord);
//        BufferedWriter matchedWriter = null;
//        try {
//            matchedWriter = new BufferedWriter(new FileWriter(dataPath + "\\" + word + ".txt"));
        List<String> phonemes = parsePhonemesFromIpaString(ipaString, word);
        int aposIndex1 = word.indexOf('’');
        int aposIndex2 = word.indexOf('\'');
        boolean apos1Present = aposIndex1 != -1;
        boolean apos2Present = aposIndex2 != -1;
        String defaultIpaParserRegex = IPA_PARSER_REGEX;
        if (apos1Present) { // apostrophe present
            word = word.substring(0, aposIndex1) + word.substring(aposIndex1+1);
            IPA_PARSER_REGEX = "ən" + IPA_PARSER_REGEX;
            pgcMap.put("ən", "(n)"); // for didn't etc.  ?????
        }
        if (apos2Present) { // apostrophe present
            word = word.substring(0, aposIndex2) + word.substring(aposIndex2+1);
            IPA_PARSER_REGEX = "ən" + IPA_PARSER_REGEX;
            pgcMap.put("ən", "(n)"); // for didn't etc.  ?????
        }
        System.out.println("Word after apos removal: " + word);
        boolean fullyMatched = false;
        boolean partialMatch = false;
        System.out.println(phonemes.toString());
        boolean splitEpresent = false;
        int splitEPhonemeIndex = findPhonemeWithSplitE(phonemes, ipaString, word);
        splitEpresent = splitEPhonemeIndex > -1;
        System.out.println("Split index:" + splitEPhonemeIndex);
        applyRulesToModifyPgcMap(phonemes, ipaString, word, splitEpresent, splitEPhonemeIndex);
//        System.out.println(phonemes.size());
        String regex = createRegexFromPhonemeStringList(phonemes, splitEpresent, splitEPhonemeIndex);
        System.out.println(regex);
//        boolean possibleSplitE = false;
//        boolean splitEpresent = false;
//        for (String phoneme : phonemes) {
//            if (VOWEL_PHONEMES_THAT_MAY_REQUIRE_FINAL_E.contains(phoneme)) {
//                regex = regex + "(e)?";
//                possibleSplitE = true;
//                break;
//            }
//        }
        boolean aposPresent = word.indexOf('’') != -1;
//        System.out.println(regex);
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(word);
        StringBuilder pgcList = new StringBuilder();
        if (m.find()) {
            System.out.println("Group count: " + m.groupCount());
            System.out.println("Number of phonemes: " + phonemes.size());
//                if (m.groupCount() - (splitEpresent?2:1) == phonemes.size()) {
//                    fullyMatched = true;
//                    System.out.println("FULL MATCH");
//                }
//                else {
//                    partialMatch = true;
//                    System.out.println("PARTIAL MATCH");
//                }
            int matchedCharCount = 0;
            for (int i = 2; i <= m.groupCount(); i++) {
//                System.out.println("i = " + i);
//                if (m.group(i) != null && i == m.groupCount() && m.group(i).equals("e") && possibleSplitE) {
//                    pgcList.append("(split e)");
//                    splitEpresent = true;
//                } else
                if (i == m.groupCount() && m.group(i) == null) {
                } else {
                    String pgcString = phonemes.get(i - 2) + "->" + m.group(i) + ((splitEpresent && (splitEPhonemeIndex + 2 == i)) ? ".e" : "") + " ";
                    pgcList.append(pgcString);
                    pgcStrings.add(pgcString.trim());
                    matchedCharCount += m.group(i).length();
                }
            }
            if ((matchedCharCount + ((splitEpresent) ? 1 : 0) + ((aposPresent) ? 1 : 0)) == word.length()) {
                fullyMatched = true;
            } else {
                partialMatch = true;
                System.out.println("PARTIAL MATCH");
            }
        } else {
            System.out.println("NO MATCH");
        }
        if (fullyMatched) {
            matchedWriter.println(ipaString + " : " + spokenWord);
            matchedWriter.println(regex);
            matchedWriter.println(pgcList.toString());
        } else {
            unmatchedWriter.println(ipaString + " : " + spokenWord);
            unmatchedWriter.println(regex);
            unmatchedWriter.println(pgcList.toString());
        }
        System.out.println(pgcList.toString());
        postProcessForDualFunctioning(pgcStrings);
        System.out.println("After post-processing for dual functioning: " + pgcStrings);
//        }
//        catch(IOException ex) {
//            try {
////                matchedWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        finally {
//            try {
////                matchedWriter.close();
//            } catch (IOException e) {
////                e.printStackTrace();
//            }
//
//        }
        initializeDefaultPgcMap();
        IPA_PARSER_REGEX = defaultIpaParserRegex;
        return pgcs;
    }

    private void postProcessForDualFunctioning(List<String> pgcStrings) {
        System.out.println("In postProcessForDualFunctioning");
        int urIndex = pgcStrings.indexOf("jʊə->u");  // not sure about this!!!99
        if (urIndex != -1 && "r->r".equals(pgcStrings.get(urIndex+1))) {
            System.out.println("In postProcessForDualFunctioning and found jʊə->u followed by r->r");
            pgcStrings.set(urIndex, "jʊə->u[r]");
        }
//        int airIndex = pgcStrings.indexOf("eər->er");  // not sure about this!!!
//        if (airIndex != -1 && "r->r".equals(pgcStrings.get(airIndex+1))) {
//            pgcStrings.set(airIndex, "eər->e[r]");
//        }
    }

    private int findPhonemeWithSplitE(List<String> phonemes, String ipaString, String word) {
        int index = -1;
        Pattern patternForDigraphWord = Pattern.compile("(.*)((?<![aeiou])(ui|[aeiouy])|(?<=u)a)(squ|ch|gn|gu|qu|ng|st|[bcdfgklmnpstvz])e(ful|able|ly|less|ness|s|d)?");
        Matcher matcherForDigraphWord = patternForDigraphWord.matcher(word);
        if (matcherForDigraphWord.matches()) {
            //  may involve a [V].e digraph; now check that the vowel phoneme is the right kind
            System.out.println("Found possible digraph: " + matcherForDigraphWord.group(2) + ".e");
            Pattern patternForDigraphIpa = Pattern.compile("(.*)(eɪ|iː|aɪ|əʊ|juː|j)(skw|ʃ|ɳdʒ|st|[bdfɡklmnptv])(fəl|əbəl|liː|ləs|nəs|s|d|t)?");
            Matcher matcherForDigraphIpa = patternForDigraphIpa.matcher(ipaString);
            if (matcherForDigraphIpa.matches()) {
                int groupCount = matcherForDigraphIpa.groupCount();
                System.out.println("matcherForDigraphIpa has " + groupCount + " groups");
                System.out.println("Confirmed digraph!: " + matcherForDigraphIpa.group(2) + ".e");
                String finalGroup = matcherForDigraphIpa.group(matcherForDigraphIpa.groupCount());
                System.out.println(("Final group is: " + finalGroup));
                int finalPhonemeGroupLength = 0;
                if (finalGroup != null) {
                    switch (finalGroup) {
                        case "fəl":
                            finalPhonemeGroupLength = 2;
                            break;
                        case "əbəl":
                            finalPhonemeGroupLength = 3;
                            break;
                        case "liː":
                            finalPhonemeGroupLength = 2;
                            break;
                        case "ləs":
                            finalPhonemeGroupLength = 3;
                            break;
                        case "nəs":
                            finalPhonemeGroupLength = 3;
                            break;
                        case "s":
                            finalPhonemeGroupLength = 1;
                            break;
                        case "d":
                            finalPhonemeGroupLength = 1;
                            break;
                        case "t":
                            finalPhonemeGroupLength = 1;
                            break;
                    }
                }
                index = phonemes.size() - (matcherForDigraphIpa.group(matcherForDigraphIpa.groupCount()) == null ? 2 : 2 + finalPhonemeGroupLength);
                System.out.println("Matched vowel: " + phonemes.get(index));

            }
        }

        return index;
    }

    private void applyRulesToModifyPgcMap(List<String> phonemes, String ipaString, String word, boolean splitEpresent, int splitEPhonemeIndex) {
        // an example - change the g mapping to enable analysis of rogue, league, catalogue
        // is the /g/ preceded by a short vowel?
//        System.out.println(phonemes.indexOf("ɡ"));
        if (phonemes.indexOf("ɡ") > 1) {
            if (SHORT_VOWEL_PHONEMES.contains(phonemes.get(phonemes.indexOf("ɡ") - 1))) {
                pgcMap.replace("ɡ", gMappingModForRogue);
            }
        }
        if (!splitEpresent) { // and check for following <ly> or <ness> and short vowels (because 'minute' x)
            pgcMap.replace("t", tMappingModForMinute);

        }
        if (true) { // if ipaString contains a /əm/ and the word contains a <m> preceded by a consonant, then add the mapping /əm/->m for rhythm etc.
            // and find adjacent /ə/ and /m/ phonemes in the list of phonemes and replace them with a single /əm/

        }


    }

    private List<String> parsePhonemesFromIpaString(String ipaString, String word) {
        int aposIndex1 = word.indexOf('’');
        int aposIndex2 = word.indexOf('\'');
        boolean apos1Present = aposIndex1 != -1;
        boolean apos2Present = aposIndex2 != -1;
        String defaultIpaParserRegex = IPA_PARSER_REGEX;
        if (apos1Present) { // apostrophe present
//            word = word.substring(0, aposIndex1) + word.substring(aposIndex1+1);
            IPA_PARSER_REGEX = "ən|" + IPA_PARSER_REGEX;
//            pgcMap.put("ən", "(n)"); // for didn't etc.  ?????
        }
        if (apos2Present) { // apostrophe present
//            word = word.substring(0, aposIndex2) + word.substring(aposIndex2+1);
            IPA_PARSER_REGEX = "ən|" + IPA_PARSER_REGEX;
//            pgcMap.put("ən", "(n)"); // for didn't etc.  ?????
        }
        System.out.println("Word after apos removal: " + word);
        List<String> phonemes = new ArrayList<>();
        Pattern pattern = Pattern.compile(IPA_PARSER_REGEX);
        Matcher m = pattern.matcher(ipaString);
        int count = 0;
        while (m.find()) {
            count++;
//            System.out.println("Found match at: " + m.start());
            phonemes.add(ipaString.substring(m.start(), m.end()));
        }
//        System.out.println(count + " found");
        IPA_PARSER_REGEX = defaultIpaParserRegex;
        return phonemes;
    }

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toSet());
    }

    public static void addDataToMatchedFile(PrintWriter matchedWriter) throws IOException {
        Set<String> pgcFileNames = listFilesUsingJavaIO(dataPath);
        for (String pgcFileName : pgcFileNames) {
            List<String> lines = Files.readAllLines(Paths.get(dataPath + "\\" + pgcFileName), StandardCharsets.UTF_8);
            for (String s : lines) {
                matchedWriter.println(s);
            }
        }
        matchedWriter.println();

    }

    public void contextualizeCambridgePhonemes() {

    }

    private String cleanRegex(String ipaText) {
        String cleaned = ipaText.replaceAll("\\.", "");
        cleaned = cleaned.replaceAll("ˈ", "");
        cleaned = cleaned.replaceAll("ˌ", "");
        cleaned = cleaned.replaceAll("u(?!ː)", "uː");
        cleaned = cleaned.replaceAll("i(?!ː)", "iː");
        cleaned = cleaned.replaceAll("i$", "ɪ");
        cleaned = cleaned.replaceAll("r$", "");
//        if (cleaned.lastIndexOf('i') == cleaned.length() - 1) {
//            cleaned = cleaned + "ː";
//        }
//        if (cleaned.substring(cleaned.length() - 2).equals("ər")) {
//            cleaned = cleaned.substring(0, cleaned.length() - 1);
//        }
        return cleaned;
    }

    public void testAllSearchTermsFromFile(PrintWriter unmatchedWriter, PrintWriter matchedWriter) throws IOException {
        List<String> allPairs = Files.readAllLines(Paths.get(wordListsPath + "\\" + "dbessWordsTest1.txt")).stream().filter(l -> !l.startsWith("UNKNOWN")).collect(Collectors.toList());
        for (String pair : allPairs) {
            String[] parts = pair.split(":");
            generatePgcsFromIpaStringAndWord(cleanRegex(parts[0].trim()), parts[1].trim(), unmatchedWriter, matchedWriter);
        }

    }

    private void testCurrentSet(PrintWriter unmatchedWriter, PrintWriter matchedWriter, String... testWords) throws IOException {
        List<String> allPairs = Files.readAllLines(Paths.get(dataPath + "\\" + "allWordsTest.txt"));
        List<String> testPairs = new ArrayList<>();
        for (String pair : allPairs) {
            for (String word : testWords) {
                if (pair.contains(word)) {
                    testPairs.add(pair);
                    break;
                }
            }
        }
        for (String pair : testPairs) {
            String[] parts = pair.split(":");
            generatePgcsFromIpaStringAndWord(cleanRegex(parts[0].trim()), parts[1].trim(), unmatchedWriter, matchedWriter);
        }


    }

    public void lookupAndTestSingleWord(String word, PrintWriter unmatchedWriter, PrintWriter matchedWriter) throws IOException {
        PageFetcher fetcher = new PageFetcher();
        WordDetail wordDetail = fetcher.fetchWordDetail(word, soundsPath);
        String ipaText = cleanRegex(wordDetail.ipaText);
        System.out.println(ipaText + " : " + wordDetail.spokenWord);
        List<Pgc> pgcs = generatePgcsFromIpaStringAndWord(ipaText, wordDetail.word, unmatchedWriter, matchedWriter);

    }

    public void lookupAndTestSectionOfSortedDbess(int start, int count, PrintWriter unmatchedWriter, PrintWriter matchedWriter) throws IOException {
        List<String> dbessWords = Files.readAllLines(Paths.get(wordListsPath + "\\" + "dictspellingsorteduniquewordsPGCetc.txt"), StandardCharsets.UTF_8).stream().filter(l->(!l.startsWith("\"") && !l.startsWith("*"))).collect(Collectors.toList());
        int MAX_WORDS = 50;
        for (int i = start; i < start + count; i++) {
            lookupAndTestSingleWord(dbessWords.get(i), unmatchedWriter, matchedWriter);
            randomPause(5, 15);
        }
    }

    public static void main(String args[]) { // throws IOException {
        PgcGenerator gen = new PgcGenerator();
//        String regex = gen.createRegexFromPhonemeStringList(Arrays.asList("k", "uː", "t"));
//        System.out.println(regex);
//        gen.listFilesUsingJavaIO(dataPath).stream().forEach(s -> System.out.println(s));
        PrintWriter unmatchedWriter = null;
        PrintWriter matchedWriter = null;
//        PrintWriter dbessWordTest = null;
        try {
            unmatchedWriter = new PrintWriter(new FileWriter(dataPath + "\\" + "unmatchedWords" + new Date().getTime() + ".txt", true));
            matchedWriter = new PrintWriter(new FileWriter(dataPath + "\\" + "matchedWords" + new Date().getTime() + ".txt", true));
//            dbessWordTest = new PrintWriter((new FileWriter(dataPath + "\\allWordsTest.txt", true)));
//            addDataToMatchedFile(matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("kjuːt", "cute", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("bjuːtɪfəl", "beautiful", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("æntɪsɪpeɪʃən", "anticipation", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("treʒə", "treasure", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("ðə", "the", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("huːz", "whose", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("bɪznɪs", "business", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("kætəɡriː", "category_s", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("kætəɡəriː", "category_l", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("ɪmædʒɪnriː", "imaginary_s", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("ɪmædʒɪnəriː", "imaginary_l", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("vɒləntrəliː", "voluntarily_s", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("vɒlənteərəliː", "voluntarily_l", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("æktʃuːəliː", "actually_l", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("æktʃuːəl", "actual_s", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("æksɪdənt", "accident", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("aɪlənd", "island", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("nɒlɪdʒ", "knowledge", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("leŋθ", "length", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord(gen.cleanRegex("mətɪəriəl"), "material", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("reɡjələ", "regular", unmatchedWriter, matchedWriter);
//            gen.generatePgcsFromIpaStringAndWord("reɪn", "reign", unmatchedWriter, matchedWriter);
            gen.generatePgcsFromIpaStringAndWord("/fjuːd/", "feud", unmatchedWriter, matchedWriter);
            gen.generatePgcsFromIpaStringAndWord("/djuːs/", "deuce", unmatchedWriter, matchedWriter);
            gen.generatePgcsFromIpaStringAndWord("/dʒuːs/", "deuce", unmatchedWriter, matchedWriter);
            gen.generatePgcsFromIpaStringAndWord("/dʒuːs/", "juice", unmatchedWriter, matchedWriter);

//            PageFetcher fetcher = new PageFetcher();
//        fetcher.fetchWordDetail("England");
//            gen.lookupAndTestSectionOfSortedDbess(80, 10, unmatchedWriter, matchedWriter);
//            gen.lookupAndTestSingleWord("Caesar", unmatchedWriter, matchedWriter);
//            gen.lookupAndTestSingleWord("Connecticut", unmatchedWriter, matchedWriter);
//            gen.lookupAndTestSingleWord("dyke", unmatchedWriter, matchedWriter);
//            gen.lookupAndTestSingleWord("leer", unmatchedWriter, matchedWriter);
//            gen.lookupAndTestSingleWord("Brexiteer", unmatchedWriter, matchedWriter);
//            WordNetSearch wn = new WordNetSearch();
//            wn.testDictionary("loving", POS.VERB);
//            gen.testAllSearchTermsFromFile(unmatchedWriter, matchedWriter);
//            gen.testCurrentSet(unmatchedWriter, matchedWriter, "separate", "make");
        } catch (IOException ex) {
            unmatchedWriter.close();
            matchedWriter.close();
            System.out.println(ex.getMessage());;
        } finally {
            unmatchedWriter.close();
            matchedWriter.close();
//            dbessWordTest.close();
        }
    }


    private static void randomPause(int minSecs, int maxSecs) {

        Random r = new Random();
        int seconds = r.ints(minSecs, (maxSecs + 1)).limit(1).findFirst().getAsInt();
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private static int randomWordIndex(int minIndex, int maxIndex) {

        Random r = new Random();
        int index = r.ints(minIndex, maxIndex).limit(1).findFirst().getAsInt();
        return index;
    }

}
