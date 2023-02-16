package org.example;

public record Field (int letter, int lenght, String word) {

    public String displayFormat() {
        if(letter == 0){
            return word;
        }else {
            return String.valueOf(word.charAt(letter-1));
        }
    }
}
