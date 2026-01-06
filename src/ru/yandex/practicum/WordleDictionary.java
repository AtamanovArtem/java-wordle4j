package ru.yandex.practicum;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WordleDictionary {

    private List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public List<String> prepareWords(List<String> words) {
        List<String> preparedWords = new ArrayList<>();
        for (String word : words) {
            if (word != null && !word.isEmpty()) {
                String lowerCaseWord = word.toLowerCase();
                String replacedWord = lowerCaseWord.replace('ё', 'е');
                if (replacedWord.length() == 5) {
                    preparedWords.add(replacedWord);
                }
            }
        }
        return preparedWords;
    }

    public List<String> getWords() {
        return words;
    }

    public String getRandomWord(List<String> preparedWords) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    public String prepareUserInput(String userInput) {
        if (userInput == null) {
            return null;
        }
        String lowerCaseUserInput = userInput.toLowerCase();
        String replaceUserInput = lowerCaseUserInput.replace('ё', 'е');
        return replaceUserInput;
    }

}
