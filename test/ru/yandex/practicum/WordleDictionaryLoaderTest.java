package ru.yandex.practicum;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class WordleDictionaryLoaderTest {
    private WordleDictionaryLoader loader;

    @BeforeEach
    public void setUp() {
        loader = new WordleDictionaryLoader();
    }

    @Test
    public void testLoadWords() {
        List<String> words = loader.loadWords("words_test.txt");
        assertEquals(4, words.size());
        assertTrue(words.contains("почка"));
        assertTrue(words.contains("пачка"));
        assertTrue(words.contains("булка"));
        assertTrue(words.contains("полка"));
    }
}
