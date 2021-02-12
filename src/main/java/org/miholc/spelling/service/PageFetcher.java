package org.miholc.spelling.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * A simple example, used on the jsoup website.
 */
public class PageFetcher {
    public WordDetail fetchWordDetail(String spokenWord, String pathForMedia) throws IOException {
        WordDetail detail = new WordDetail();
        detail.spokenWord = spokenWord;
        detail.word = SpellingUtils.extractWordFromSpokenWord(spokenWord);
        Document doc = Jsoup.connect("https://dictionary.cambridge.org/dictionary/english/" + detail.word).get();
        Element ipaEl = doc.select("span.ipa").first();
        Element wordEl = doc.select("span.hw.dhw").first();
//        System.out.println(ipaEl);
        if (ipaEl != null) {
            detail.ipaText = ipaEl.text().trim();
            String pageWord = wordEl.text().trim();
            if (!pageWord.equalsIgnoreCase(detail.word)) {
                System.out.println("Page word: " + pageWord + " not the same as spoken word: " + detail.word);
            }
            System.out.println(detail.ipaText);
            detail.mediaUrl = doc.select("amp-audio#ampaudio1 > source").first().attr("src");
            new MediaFinder().fetchMedia("https://dictionary.cambridge.org" + detail.mediaUrl, pathForMedia + "\\" + detail.spokenWord + ".mp3", 5000, 5000);
//        Elements mediaSources = doc.select("amp-audio#ampaudio1 > audio > source");
//        System.out.println(mediaSources);
//        Element mediaSource = mediaSources.first();

//        detail.mediaUrl = mediaSource.attr("src");
//        log("%s\n%s", detail.ipaText, detail.mediaUrl);
        }
        else {
            detail.ipaText = "UNKNOWN";
        }
        return detail;
    }

    private void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }

    public static void main(String[] args) throws IOException {
        PageFetcher fetcher = new PageFetcher();
        fetcher.fetchWordDetail("England", "D:\\dev\\spelling");
    }
}
