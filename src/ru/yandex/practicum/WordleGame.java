package ru.yandex.practicum;
import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    public String answer;
    public int steps;
    private WordleDictionary dictionary;
    private LinkedHashMap<Integer, String> userInputs;

    public int getSteps() {

        return steps;
    }

    public WordleGame(String answer, WordleDictionary dictionary, PrintWriter log) {
        this.answer = answer;
        this.steps = 0;
        this.dictionary = dictionary;
        userInputs = new LinkedHashMap<>();
    }

    public boolean checkWord(String userWord) {
        boolean isCorrect = userWord.equals(answer);
        userInputs.put(steps, userWord);
        return isCorrect;
    }

    public List<String> filterWords(List<String> dictionaryWords, Set<Character> excludedLetters,
                                    Set<Character> includedLetters, Map<Integer, Character> correctPositions) {
        List<String> filteredWords = new ArrayList<>();

        for (String word : dictionaryWords) {
            if (word.length() != answer.length()) {
                continue;
            }
            boolean hasExcludedLetters = false;
            for (Character letter : excludedLetters) {
                if (word.contains(letter.toString())) {
                    hasExcludedLetters = true;
                    break;
                }
            }
            if (hasExcludedLetters) {
                continue;
            }
            boolean containsAllIncludedLetters = includedLetters.stream()
                    .allMatch(letter -> word.contains(letter.toString()));
            if (!containsAllIncludedLetters) {
                continue;
            }
            boolean matchesCorrectPositions = correctPositions.entrySet().stream()
                    .allMatch(entry -> word.charAt(entry.getKey()) == entry.getValue());
            if (matchesCorrectPositions) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    public String suggestWord(List<String> preparedWords, Set<Character> excludedLetters,
                              Set<Character> includedLetters, Map<Integer, Character> correctPositions) {
        List<String> filteredWords = filterWords(preparedWords, excludedLetters, includedLetters, correctPositions);

        if (filteredWords.isEmpty()) {
            return "Нет подходящих слов.";
        }

        Random random = new Random();
        int index = random.nextInt(filteredWords.size());
        return filteredWords.get(index);
    }


    public String compareWords(String userWord, String answer) {
        StringBuilder result = new StringBuilder();
        boolean[] used = new boolean[answer.length()];

        for (int i = 0; i < userWord.length(); i++) {
            char userChar = userWord.charAt(i);
            char answerChar = answer.charAt(i);
            if (userChar == answerChar) {
                result.append('+');
                used[i] = true;
            } else {
                result.append('-');
            }
        }
        List<Character> remainingChars = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            if (!used[i]) {
                remainingChars.add(answer.charAt(i));
            }
        }
        for (int i = 0; i < userWord.length(); i++) {
            char userChar = userWord.charAt(i);
            if (result.charAt(i) == '-' && remainingChars.contains(userChar)) {
                result.setCharAt(i, '^');
                remainingChars.remove((Character) userChar);
            }
        }
        return result.toString();
    }

    public boolean lengthWord(String userWord) {
        boolean isCorrect = userWord.length() == answer.length();
        userInputs.put(steps, userWord);
        return isCorrect;
    }

    public boolean wordContainsDictionary(String userWord) {
        List<String> words = dictionary.getWords();
        for (String dictWord : words) {
            if (dictWord.equals(userWord)) {
                return true;
            }
        }
        return false;
    }
}



