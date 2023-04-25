/*
  Authors: Jacqueline Torres, Nchimi Mvula, Rahul Rajkumar, Hashiim Mohammed Sheriff Sathick Batcha
  Email addresses of group members: jtorres2020@my.fit.edu, nmvula2022@my.fit.edu, rrajkumar2020@my.fit.edu, hmohammedshe2021@my.fit.edu
  Group name: Underdogs
  Course: CSE2010
  Section: 23
  Description of the overall algorithm: QuerySideKick is directly used by EvalQuerySideKick. This file will take in old queries and process 
  them into a trie data structure. Guesses are made using recursion to traverse through the trie to build the complete word. 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class QuerySidekick {
  
  String[] guesses = new String[5];; // 5 guesses from QuerySidekick
  Trie history; // Creating the trie 
  String line; 
  LinkedList<String> removed; // List to track the removed words for trie

  public QuerySidekick() {
    history = new Trie();
    line = "";
    removed = new LinkedList<>();
  }

  // processes the oldQuery files
  public void processOldQueries(String oldQueryFile) throws FileNotFoundException {
    
    String replace = oldQueryFile.replaceAll("\\s+", " "); 
    File file = new File(replace);
    Scanner scan = new Scanner(file);
    
    // insert each char into Trie
    while (scan.hasNextLine()) {
      history.insert(scan.nextLine());
    }
    scan.close();
  }

  // based on a character typed in by the user, return 5 query guesses in an array
  // currChar: current character typed in by the user
  // currCharPosition: position of the current character in the query, starts from
  public String[] guess(char currChar, int currCharPosition) {
    
    // build the string off each character input
    line += Character.valueOf(currChar);
    
    // Store the guesses in a linkedList
    LinkedList<String> attempts = history.autocomplete(line, 5);
 
    if (attempts.size() > 5) {
      for (int i = 0; i < 5; i++) {
        guesses[i] = attempts.get(i);
      }
    } else {
      for (int i = 0; i < attempts.size(); i++) {
        guesses[i] = attempts.get(i);
      }
    }
    return guesses;
  }

  // feedback on the 5 guesses from the user
  // isCorrectGuess: true if one of the guesses is correct
  // correctQuery: 3 cases:
  // a. correct query if one of the guesses is correct
  // b. null if none of the guesses is correct, before the user has typed in
  // the last character
  // c. correct query if none of the guesses is correct, and the user has
  // typed in the last character
  // That is:
  // Case isCorrectGuess correctQuery
  // a. true correct query
  // b. false null
  // c. false correct query
  public void feedback(boolean isCorrectGuess, String correctQuery) {

    // If our guesses have been incorrect and the last character had been given then
    // put the whole string in the Trie
    if (isCorrectGuess == false && correctQuery != null) {
      history.insert(correctQuery);
      line = "";
    } else if (isCorrectGuess == false && correctQuery == null) {
      for (int i = 0; i < 5; i++) {
        removed.add(guesses[i]);
      }
    } else {
      for (int i = 0; i < removed.size(); i++) {
        history.insert(removed.get(i));
      }
      removed.clear();
      line = "";
    }
  }
}
