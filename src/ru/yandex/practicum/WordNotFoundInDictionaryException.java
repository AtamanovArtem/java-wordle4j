package ru.yandex.practicum;

public class WordNotFoundInDictionaryException extends Exception {
    public WordNotFoundInDictionaryException(final String message) {
        super(message);
    }
}
