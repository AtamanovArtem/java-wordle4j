package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Wordle {

    public static void main(String[] args) throws IOException, WordNotFoundInDictionaryException, lenghtWordNotRespondException {

        PrintWriter log = null;
        Scanner scanner = new Scanner(System.in);
        WordleGame game = null;
        WordleDictionary dictionary = null;

        try {
            log = new PrintWriter(new FileWriter("log.txt"));
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            List<String> words = loader.loadWords("words_ru.txt");
            dictionary = new WordleDictionary(words);
            List<String> preparedWords = dictionary.prepareWords(words);
            dictionary = new WordleDictionary(preparedWords);
            game = new WordleGame(dictionary.getRandomWord(preparedWords), dictionary, log);

            while (game.getSteps() <= 6) {
                System.out.println("Компьютер загадал слово из пяти букв! Ваша попытка: ");
                String userInput = scanner.nextLine();
                String userWord = dictionary.prepareUserInput(userInput);

                if (userWord == null || userWord.isEmpty()) {
                    System.out.println("Пожалуйста, введите непустое слово!");
                    log.println("Пользователь ввёл пустое слово");
                    continue;
                }

                if (dictionary != null) {
                    game.steps++;
                } else {
                    throw new RuntimeException("Словарь не был загружен.");
                }
                try {
                    if (!game.wordContainsDictionary(userWord)) {
                        throw new WordNotFoundInDictionaryException("Введённого слова нет в словаре! Количество оставшихся " +
                                "попыток: " + (6 - game.getSteps()));
                    }
                } catch (WordNotFoundInDictionaryException e) {
                    System.out.println("Произошла ошибка: " + e.getMessage());
                    log.println("Ошибка: " + e.getMessage());
                    e.printStackTrace(log);
                    continue;
                }
                try {
                    if (!game.lengthWord(userWord)) {
                        throw new lenghtWordNotRespondException("Длина введенного слова не соответствует условиям!" +
                                " Введите слово длиной в 5 символов!");
                    }
                } catch (lenghtWordNotRespondException exception) {
                    System.out.println("Произошла ошибка: " + exception.getMessage());
                    log.println("Ошибка: " + exception.getMessage());
                    exception.printStackTrace(log);
                    continue;
                }
                try {
                    if (game.checkWord(userWord)) {
                        System.out.println("Вы угадали слово! Количество оставшихся попыток: " + (6 - game.getSteps()));
                        break;
                    } else {
                        Set<Character> excludedLetters = new HashSet<>();
                        Set<Character> includedLetters = new HashSet<>();
                        Map<Integer, Character> correctPositions = new HashMap<>();
                        for (int i = 0; i < userWord.length(); i++) {
                            char letter = userWord.charAt(i);
                            if ((game.answer).indexOf(letter) == -1) {
                                excludedLetters.add(letter);
                            } else if (userWord.charAt(i) != (game.answer).charAt(i)) {
                                includedLetters.add(letter);
                            } else {
                                correctPositions.put(i + 1, letter); // Позиции начинаются с 1
                            }
                        }
                        String suggestedWord = game.suggestWord(preparedWords, excludedLetters, includedLetters,
                                correctPositions);
                        System.out.println(game.compareWords(userWord, game.answer));
                        System.out.println("Ближайшее слово: " + suggestedWord + " Количество оставшихся попыток: " +
                                (6 - game.getSteps()));
                    }
                } catch (RuntimeException e) {
                    System.out.println("Произошла критическая ошибка: " + e.getMessage());
                    log.println("Ошибка: " + e.getMessage());
                    e.printStackTrace(log);
                }
            }
            System.out.println("Разгадка: " + game.answer);
        } catch (IOException e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            log.println("Ошибка: " + e.getMessage());
            e.printStackTrace(log);
        }

        log.close();
    }
}
