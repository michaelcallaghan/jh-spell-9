package org.miholc.spelling.service;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MediaFinder {

    public void fetchMedia(String fileUrl, String fileName, int connectTimeout, int ReadTimeout) throws IOException {

        FileUtils.copyURLToFile(
                new URL(fileUrl),
                new File(fileName),
                connectTimeout,
                ReadTimeout);
    }

    public static void main(String[] args) throws IOException {
        MediaFinder finder = new MediaFinder();
        finder.fetchMedia("https://dictionary.cambridge.org/media/english/uk_pron/u/ukh/ukher/ukherse006.mp3", "firstWord.mp3", 5000, 5000);
    }
}
