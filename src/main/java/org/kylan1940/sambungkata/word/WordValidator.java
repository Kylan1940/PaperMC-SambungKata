package org.kylan1940.sambungkata.word;

import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.Game;

public class WordValidator {

    public static ValidationResult validate(Game game, String word) {

        word = word.toLowerCase().trim();

        if (!SambungKata.getInstance().getWordManager().exists(word))
            return ValidationResult.WORD_NOT_FOUND;

        if (game.getUsedWords().contains(word))
            return ValidationResult.WORD_ALREADY_USED;

        if (game.getLastWord() == null)
            return ValidationResult.VALID;

        String last = game.getLastWord();

        if (!word.startsWith(last.substring(last.length() - 1)))
            return ValidationResult.INVALID_PREFIX;

        return ValidationResult.VALID;
    }

}