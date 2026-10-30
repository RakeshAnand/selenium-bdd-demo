package com.framework.shared.practice;

public class StringDemo {
    public static void main(String[] args) {
        String s = "i love Java Programming";
        String longestWord = "";

        String words[] = s.split(" ");

        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        System.out.println(longestWord);
    }

}
