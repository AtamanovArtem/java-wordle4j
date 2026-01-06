package ru.yandex.practicum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {
    @Test
    public void testPrepareWords() {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("Привет", "МИР", "пЧёлка"));
        List<String> result = dictionary.prepareWords(Arrays.asList("ПРИВЕТ", "Почка", "ТЁЛКА"));
        assertEquals(2, result.size());
        assertTrue(result.contains("почка"));
        assertTrue(result.contains("телка"));
    }

    @Test
    public void testGetRandomWord() {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("РАЗ", "два", "Три"));
        String randomWord = dictionary.getRandomWord(dictionary.getWords());
        assertTrue(dictionary.getWords().contains(randomWord));
    }

    @Test
    public void testPrepareUserInput() {
        WordleDictionary dictionary = new WordleDictionary(new ArrayList<>());

        assertEquals("пачка", dictionary.prepareUserInput("пАчка"));
        assertEquals("телка", dictionary.prepareUserInput("Тёлка"));
        assertEquals("донка", dictionary.prepareUserInput("ДОНКА"));
        assertEquals("тест", dictionary.prepareUserInput("тест"));
        assertNull(dictionary.prepareUserInput(null));
        assertEquals("", dictionary.prepareUserInput(""));
    }

}
