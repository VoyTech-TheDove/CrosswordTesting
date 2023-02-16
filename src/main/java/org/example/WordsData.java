package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public class WordsData {
    static List<String> words2letter;
    String filepath2LetterWords = "C:\\Users\\USER\\Downloads\\krzyzowki-master\\krzyzowki-master\\2.txt";
    static List<String> words3letter;
    String filepath3LetterWords = "C:\\Users\\USER\\Downloads\\krzyzowki-master\\krzyzowki-master\\3.txt";
    static List<String> words4letter;
    String filepath4LetterWords = "C:\\Users\\USER\\Downloads\\krzyzowki-master\\krzyzowki-master\\4.txt";
    static List<String> words5letter;
    String filepath5LetterWords = "C:\\Users\\USER\\Downloads\\krzyzowki-master\\krzyzowki-master\\5.txt";
    static List<String> words6letter;
    String filepath6LetterWords = "C:\\Users\\USER\\Downloads\\krzyzowki-master\\krzyzowki-master\\6.txt";

    public void loadWordsFromFiles() {
        words2letter=loadWordsFromFile(filepath2LetterWords);
        words3letter=loadWordsFromFile(filepath3LetterWords);
        words4letter=loadWordsFromFile(filepath4LetterWords);
        words5letter=loadWordsFromFile(filepath5LetterWords);
        words6letter=loadWordsFromFile(filepath6LetterWords);
    }

    public List<String> loadWordsFromFile(String filepath) {
        try {
            List<String> list = Files.readAllLines(new File(filepath).toPath(), StandardCharsets.UTF_8);
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> wordsOfLenght(int lenght) {
        if (lenght == 3) {
            return words3letter;
        } else if (lenght == 2) {
            return words2letter;
        } else if (lenght == 4) {
            return words4letter;
        } else if (lenght == 5) {
            return words5letter;
        } else {
            return words6letter;
        }
    }

    public String randomWord(int lenght) {
        List<String> list = wordsOfLenght(lenght);
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

}
