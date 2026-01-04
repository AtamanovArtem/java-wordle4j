package ru.yandex.practicum;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    @Test
    public void testCheckWord() {
        WordleDictionary dictionary = new WordleDictionary(new ArrayList<>());
        WordleGame game = new WordleGame("дочка", dictionary, new PrintWriter(System.out));
        assertTrue(game.checkWord("дочка"));
        assertFalse(game.checkWord("точка"));
    }

    @Test
    public void testFilterWords() {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("привет", "пять", "море"));
        WordleGame game = new WordleGame("море", dictionary, new PrintWriter(System.out));

        Set<Character> excludedLetters = new HashSet<>(Arrays.asList('п', 'в'));
        Set<Character> includedLetters = new HashSet<>(Arrays.asList('м', 'о'));
        Map<Integer, Character> correctPositions = new HashMap<>();
        correctPositions.put(2, 'р');

        List<String> filteredWords = game.filterWords(dictionary.getWords(), excludedLetters, includedLetters, correctPositions);
        assertEquals(1, filteredWords.size());
        assertTrue(filteredWords.contains("море"));
    }

    @Test
    public void testSuggestWord() {
        WordleDictionary dictionary = new WordleDictionary(Arrays.asList("море", "гора", "поле"));
        WordleGame game = new WordleGame("море", dictionary, new PrintWriter(System.out));

        Set<Character> excludedLetters = new HashSet<>();
        Set<Character> includedLetters = new HashSet<>(Arrays.asList('м', 'о'));
        Map<Integer, Character> correctPositions = new HashMap<>();
        correctPositions.put(0, 'м');

        String suggestedWord = game.suggestWord(dictionary.getWords(), excludedLetters, includedLetters, correctPositions);
        assertEquals("море", suggestedWord);
    }

    @Test
    public void testCompareWords() {
        WordleDictionary dictionary = new WordleDictionary(new ArrayList<>());
        WordleGame game = new WordleGame("привет", dictionary, new PrintWriter(System.out));
        assertEquals("++++++", game.compareWords("привет", "привет"));
        assertEquals("+-^---", game.compareWords("пятьпд", "привет"));
    }
}
