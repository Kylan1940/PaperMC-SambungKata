package org.kylan1940.sambungkata.word;

import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.Room;

public class WordValidator {

    public static ValidationResult validate(Room room, String word) {

        word = word.toLowerCase().trim();
        if (!SambungKata.getInstance().getWordManager().exists(word)) {
            return ValidationResult.WORD_NOT_FOUND;
        }

        if (room.getUsedWords().contains(word)) {
            return ValidationResult.WORD_ALREADY_USED;
        }


        if (room.getLastWord() == null) {
            return ValidationResult.VALID;
        }

        String last = room.getLastWord();
        String required = last.substring(last.length() - 1);

        if (!word.startsWith(required)) {
            return ValidationResult.INVALID_PREFIX;
        }
        return ValidationResult.VALID;
    }
}