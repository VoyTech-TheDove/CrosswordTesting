package org.example;

import java.util.Random;
import java.util.Formatter;

public class Service {

    static int crosswordLenght = 5;
    static int crosswordHeight = 4;
    static Field[][] crossword = new Field[crosswordHeight][crosswordLenght];
    static int counter = 1;

    public void fillCrossword() {
        System.out.println("new crossword #" + counter);
        for (int y = 0; y < crosswordHeight; y++) {
            for (int x = 0; x < crosswordLenght; x++) {
                if (fieldIsEmpty(y, x)) {
                    if ((isNextWordPossible(y, x))) {
                        findingTheRightWordAndAddingIt(y, x);
                    } else {
                        crosswordCounterIncr();
                        printCrossword();
                        clearCrossword();
                        fillCrossword();
                    }
                }
            }
        }
    }

    public void clearCrossword() {
        crossword = new Field[crosswordHeight][crosswordLenght];
    }

    private boolean fieldIsEmpty(int y, int x) {
        return crossword[y][x] == null;
    }

    public void printCrossword() {
        for (int y = 0; y < crosswordHeight; y++) {
            for (int x = 0; x < crosswordLenght; x++) {
                Field field = crossword[y][x];
                String message = (field == null) ? "null" : field.displayFormat();
                System.out.printf("[%7s]", message);
            }
            System.out.println();
        }
    }

    private void findingTheRightWordAndAddingIt(int y, int x) {
        WordToAdd wordToAdd = findingTheRightWord(y, x);
        addingWordToCrossword(wordToAdd);
    }

    private WordToAdd findingTheRightWord(int y, int x) {
        boolean isHorizontal = isHorizontal(y, x);
        WordToAdd word = selectingRandomWord(y, x, isHorizontal);
        return (wordIsOK(word)) ? word : findingTheRightWord(y, x);
    }

    private boolean wordIsOK(WordToAdd word) {
        return ((word.isHorizontal()) ? horizontalWordIsOK(word) : verticalWordIsOK(word));
    }

    private boolean horizontalWordIsOK(WordToAdd word) {
        if (!nextHorizontalFieldIsOK(word)) {
            return false;
        }
        int x = word.x();
        int y = word.y();
        int lenght = word.word().length();
        for (int i = x + 1; i <= x + lenght; i++) {
            if (isFieldOutOfBounds(y, i)) {
                return false;
            }
            char ch = word.word().charAt(i - x - 1);
            Field field = crossword[y][i];
            if (field != null) {
                if (field.letter() == 0) {
                    return false;
                }
                char fieldCh = field.word().charAt(field.letter() - 1);
                if (ch != fieldCh) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verticalWordIsOK(WordToAdd word) {
        if (!nextVerticalFieldIsOK(word)) {
            return false;
        }
        int x = word.x();
        int y = word.y();
        int lenght = word.word().length();
        for (int i = y + 1; i <= y + lenght; i++) {
            if (isFieldOutOfBounds(y, x)) {
                return false;
            }
            char ch = word.word().charAt(i - y - 1);
            Field field = crossword[i][x];
            if (field != null) {
                if (field.letter() == 0) {
                    return false;
                }
                char fieldCh = field.word().charAt(field.letter() - 1);
                if (ch != fieldCh) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean nextVerticalFieldIsOK(WordToAdd word) {
        int lenght = word.word().length();
        int x = word.x();
        int y = word.y() + lenght + 1;
        if (isFieldOutOfBounds(y, x) || fieldIsEmpty(y, x)) {
            return true;
        }
        Field field = crossword[y][x];
        if (field.letter() == 0) {
            return true;
        }

        return false;
    }

    private boolean nextHorizontalFieldIsOK(WordToAdd word) {
        int lenght = word.word().length();
        int x = word.x() + lenght + 1;
        int y = word.y();
        if (isFieldOutOfBounds(y, x) || fieldIsEmpty(y, x)) {
            return true;
        }
        Field field = crossword[y][x];
        if (field.letter() == 0) {
            return true;
        }

        return false;
    }


    private boolean isFieldOutOfBounds(int y, int x) {
        return (y >= crosswordHeight -1) || (x >= crosswordLenght-1);
    }

    private boolean isFieldWithinBounds(int y, int x) {
        return ((y < crosswordHeight) && (x < crosswordLenght));
    }

    private boolean isFieldTaken(int y, int x) {
        Field field = crossword[y][x];
        return field != null;
    }

    private boolean isNextWordPossible(int y, int x) {
        if ((y > crosswordHeight - 4) && (x > crosswordLenght - 4)) {
            return false;
        }
        return true;
    }

    private void addingWordToCrossword(WordToAdd wordToAdd) {
        if (wordToAdd.isHorizontal()) {
            horizontallyAddingWordToCrossword(wordToAdd);
        } else {
            verticallyAddingWordToCrossword(wordToAdd);
        }
    }

    private void horizontallyAddingWordToCrossword(WordToAdd word) {
        int x = word.x();
        int y = word.y();
        int lenght = word.word().length();
        int l = 0;
        for (int i = x; ((i <= x + lenght) && (i < crosswordLenght)); i++) {
            crossword[y][i] = new Field(l, lenght, word.word());
            l++;
        }
    }

    private void verticallyAddingWordToCrossword(WordToAdd word) {
        int x = word.x();
        int y = word.y();
        int lenght = word.word().length();
        int l = 0;
        for (int i = y; ((i <= y + lenght) && (i < crosswordHeight)); i++) {
            crossword[i][x] = new Field(l, lenght, word.word());
            l++;
        }
    }

    private WordToAdd selectingRandomWord(int y, int x, boolean isHorizontal) {
        WordsData wd = new WordsData();
        int wordLenght = randomLenght(y, x, isHorizontal);
        return new WordToAdd(y, x, wd.randomWord(wordLenght), isHorizontal);
    }

    private int randomLenght(int y, int x, boolean isHorizontal) {
        try {
            int maxLenght = maxPossibleLenght(y, x, isHorizontal);
            if (maxLenght <5) {
                return maxLenght;
            }
            int[] possibleLenght= {maxLenght, maxLenght-1, maxLenght -2};
            int lenght = possibleLenght [new Random().nextInt(3 )];
            return lenght;
        } catch (StackOverflowError e) {
            crosswordCounterIncr();
            clearCrossword();
            fillCrossword();
        }
        return 2;
    }

    private void crosswordCounterIncr() {
        counter++;
    }

    private boolean isHorizontal(int y, int x) {
        if (((crosswordHeight - (y+1)) < 2)) {
            return true;
        } else if ((crosswordLenght - (x+1)) < 2) {
            return false;
        } else {
            return Math.random() < 0.65;
        }
    }

    private int maxPossibleLenght(int y, int x, boolean isHorizontal) {
        if (isHorizontal) {
            return crosswordLenght - x - 1;
        } else {
            return crosswordHeight - y - 1;
        }
    }


}
