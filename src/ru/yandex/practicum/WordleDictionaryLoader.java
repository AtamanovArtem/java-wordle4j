package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WordleDictionaryLoader {

    public List<String> loadWords(String filename) {
        List<String> words = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filename, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(fileReader)) {
            while (br.ready()) {
                String line = br.readLine();
                if (!words.contains(line)) {
                    words.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
