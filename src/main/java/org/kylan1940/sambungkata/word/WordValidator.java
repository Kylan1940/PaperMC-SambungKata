package org.kylan1940.sambungkata.word;

import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.Game;

public class WordValidator {

    public static boolean validate(Game game, String word){

        word = word.toLowerCase();

        if(!SambungKata.getInstance().getWordManager().exists(word))
            return false;

        if(game.getUsedWords().contains(word))
            return false;

        if(game.getLastWord() == null)
            return true;

        String last = game.getLastWord();

        return word.startsWith(
                last.substring(last.length()-1)
        );

    }

}