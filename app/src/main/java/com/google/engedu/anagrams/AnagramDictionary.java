/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*Anagram Game
* Edited By: Pradnya Valsangkar*/
package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random=new Random();
    private ArrayList wordList;
    private String sortedWord="";
    private HashSet wordSet;
    private HashMap lettersToWord;
    private HashMap sizeToWords;
    private String key;
    private int wordLength=DEFAULT_WORD_LENGTH;
    private ArrayList sameSizeWords=null;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordSet = new HashSet();
        wordList = new ArrayList();
        lettersToWord = new HashMap();
        sizeToWords=new HashMap();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            int len=word.length();
            sameSizeWords=new ArrayList();
            if(sizeToWords.get(len)==null) {
                sameSizeWords.add(word);
                sizeToWords.put(len, sameSizeWords);
            }
            else {

                sameSizeWords = (ArrayList) sizeToWords.get(len);
                sameSizeWords.add(word);
                sizeToWords.put(len, sameSizeWords);

            }
            ArrayList arr=new ArrayList();
            key=sortLetters(word);
            if(lettersToWord.get(key)==null){
                arr.add(word);
                lettersToWord.put(key,arr);
            }
            else {
                arr= (ArrayList) lettersToWord.get(key);
                arr.add(word);
                lettersToWord.put(key,arr);
            }
        }
    }


    public String sortLetters(String word)
    {
        char[] letters=word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }


    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }


    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> ret;
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String newStr = word + alphabet;
            String newStrKey = sortLetters(newStr);
            if (lettersToWord.containsKey(newStrKey)) {
                ret = new ArrayList();
                ret = (ArrayList) lettersToWord.get(newStrKey);
                for (int i = 0; i < ret.size(); i++) {
                    if(isGoodWord(String.valueOf(ret.get(i)),word))
                        result.add(String.valueOf(ret.get(i)));
                }
            }

        }
        return result;
    }


    public String pickGoodStarterWord() {
        String randomWord=null;
        ArrayList starter= (ArrayList) sizeToWords.get(wordLength);
        if(wordLength<MAX_WORD_LENGTH)
            wordLength++;
            while(true)
            {
                randomWord= (String) starter.get(random.nextInt(starter.size()));
                if( getAnagramsWithOneMoreLetter(randomWord).size()>=MIN_NUM_ANAGRAMS)
                    return randomWord;
            }
    }

}




   /* public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        sortedWord=sortLetters(targetWord);
        ListIterator<String> iterator = wordList.listIterator();
        while(iterator.hasNext())
        {
            String listWord=iterator.next();
            String sortedListWord=sortLetters(listWord);
            if(sortedWord.length()==sortedListWord.length() && sortedWord.equals(sortedListWord))
                result.add(targetWord);

        }
        return result;
    }*/




