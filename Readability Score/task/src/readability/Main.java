package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //Scanner scanner = new Scanner(System.in);
        String input = "";

        if (args.length > 0) {
            try (Scanner scanner = new Scanner(new File(args[0]))) {
                input = scanner.nextLine();
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
        }

        System.out.printf("""
                                The text is:
                                %s\r\n""", input);

        String[] sentences = input.split("\\.|\\?|!");
        String[] words = input.split("\\s");
        double numberOfVisibleChars = String.join("", words).length();


        int syllablesCount = getSyllablesCount(words);
        int polysyllables = getPolysyllablesCount(words);

        System.out.println(String.format("Words: %d", words.length));
        System.out.println(String.format("Sentences: %d", sentences.length));
        System.out.println(String.format("Characters: %d", (int)numberOfVisibleChars));
        System.out.println(String.format("Syllables: %d", syllablesCount));
        System.out.println(String.format("Polysyllables: %d", polysyllables));

        System.out.printf("""
                Enter the score you want to calculate (ARI, FK, SMOG, CL, all): """);

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();

        System.out.println();

        double score;
        int yearToPrint;
        int scoreInt;

        switch(userInput) {
            case "ARI":
                evaluateARI(sentences, words, numberOfVisibleChars);
                break;
            case "FK":
                evaluateFK(sentences, words, syllablesCount);
                break;
            case "SMOG":
                evaluateSMOG(sentences, polysyllables);
                break;
            case "CL":
                evaluateCL(sentences, words, numberOfVisibleChars);
                break;
            case "all":
                double averageYears = 0.0;
                averageYears += evaluateARI(sentences, words, numberOfVisibleChars);
                averageYears += evaluateFK(sentences, words, syllablesCount);
                averageYears += evaluateSMOG(sentences, polysyllables);
                averageYears += evaluateCL(sentences, words, numberOfVisibleChars);

                averageYears = averageYears / 4.0;

                System.out.printf("""
                        
                        This text should be understood in average by %.2f-year-olds.
                        """, averageYears);

                break;

        }













//        switch (scoreInt) {
//            case 1:
//                yearToPrint = 6;
//                break;
//            case 2:
//                yearToPrint = 6;
//                break;
//            case 3:
//                yearToPrint = 6;
//                break;
//            case 4:
//                yearToPrint = 6;
//                break;
//            case 5:
//                textToPrint = "This text should be understood by 9-10 year-olds.";
//                break;
//            case 6:
//                textToPrint = "This text should be understood by 10-11 year-olds.";
//                break;
//            case 7:
//                textToPrint = "This text should be understood by 11-12 year-olds.";
//                break;
//            case 8:
//                textToPrint = "This text should be understood by 12-13 year-olds.";
//                break;
//            case 9:
//                textToPrint = "This text should be understood by 13-14 year-olds.";
//                break;
//            case 10:
//                textToPrint = "This text should be understood by 14-15 year-olds.";
//                break;
//            case 11:
//                textToPrint = "This text should be understood by 15-16 year-olds.";
//                break;
//            case 12:
//                textToPrint = "This text should be understood by 16-17 year-olds.";
//                break;
//            case 13:
//                textToPrint = "This text should be understood by 17-18 year-olds.";
//                break;
//            case 14:
//                textToPrint = "This text should be understood by 18-19 year-olds.";
//                break;




    }

    private static double evaluateCL(String[] sentences, String[] words, double numberOfVisibleChars) {
        int yearToPrint;
        double score;
        int scoreInt;
        score = 0.0588d * (numberOfVisibleChars / (double) words.length * 100.0d) - 0.296 * ((double) sentences.length/(double) words.length * 100.0d) - 15.8d;
        scoreInt = (int) Math.ceil(score);
        //System.out.println(String.format("Automated Readability Index: %.2f", score));
        yearToPrint = scoreInt + 5;
        System.out.println(String.format("Coleman–Liau index: %.2f (about %d-year-olds)", score, yearToPrint));
        return yearToPrint;
    }

    private static double evaluateSMOG(String[] sentences, int polysyllables) {
        double score;
        int scoreInt;
        int yearToPrint;
        score = 1.043d * Math.sqrt(polysyllables * 30.0/ (double) sentences.length) + 3.1291d;
        scoreInt = (int) Math.ceil(score);
        //System.out.println(String.format("Automated Readability Index: %.2f", score));
        yearToPrint = scoreInt + 5;
        System.out.println(String.format("Simple Measure of Gobbledygook: %.2f (about %d-year-olds)", score, yearToPrint));
        return yearToPrint;
    }

    private static double evaluateFK(String[] sentences, String[] words, double syllablesCount) {
        int scoreInt;
        double score;
        int yearToPrint;
        score = 0.39d * ((double) words.length/ (double) sentences.length) + 11.8d * (syllablesCount / (double) words.length) - 15.59d;
        scoreInt = (int) Math.ceil(score);
        //System.out.println(String.format("Automated Readability Index: %.2f", score));
        yearToPrint = scoreInt + 5;
        System.out.println(String.format("Flesch-Kincaid readability tests: %.2f (about %d-year-olds)", score, yearToPrint));
        return yearToPrint;
    }

    private static double evaluateARI(String[] sentences, String[] words, double numberOfVisibleChars) {
        double score = 4.71d * (numberOfVisibleChars /(double) words.length) + 0.5d * ((double) words.length / (double) sentences.length) - 21.43d;
        int scoreInt = (int) Math.ceil(score);
        //System.out.println(String.format("Automated Readability Index: %.2f", score));
        int yearToPrint = scoreInt + 5;
        System.out.println(String.format("Automated Readability Index: %.2f (about %d-year-olds)", score, yearToPrint));
        return yearToPrint;
    }

    public static int getSyllablesCount(String[] words) {
        int counter = 0;
        for (String word : words) {
            counter = counter + countSyllables(word);
        }
        return counter;
    }

    public static int getPolysyllablesCount(String[] words) {
        int counter = 0;
        for (String word : words) {
            int numberOfSyllables = countSyllables(word);
            if (numberOfSyllables > 2) {
                counter++;
            }
        }
        return counter;
    }

    public static int countSyllables(String targetText){
        targetText = targetText.toLowerCase();
        targetText = targetText.replaceAll("[.,!?]", "");
        if (targetText.endsWith("e")) {
            targetText = targetText.substring(0, targetText.length()-1);
        }
        targetText = targetText.replaceAll("[aeiouy]{2,}", "a");



        int syllableCounter = 0;
        if (targetText.matches(".*[aeiouy]+.*")){
            String syllablePattern = "[aeiouy]+";
            Pattern p = Pattern.compile(syllablePattern);
            Matcher m = p.matcher(targetText);
            while (m.find()) {
                syllableCounter++;
            }

            return syllableCounter;
        }
        return 1;


    }
}
