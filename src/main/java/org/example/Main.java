package org.example;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        WordsData wordsData= new WordsData();
        wordsData.loadWordsFromFiles();

        Service service = new Service();
        service.printCrossword();
        service.fillCrossword();
        service.printCrossword();


    }
}