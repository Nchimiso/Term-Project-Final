/*
  Authors: Jacqueline Torres, Nchimi Mvula, Rahul Rajkumar, Hashiim Mohammed Sheriff Sathick Batcha
  Email addresses of group members: jtorres2020@my.fit.edu, nmvula2022@my.fit.edu, rrajkumar2020@my.fit.edu, hmohammedshe2021@my.fit.edu
  Group name: Underdogs
  Course: CSE2010
  Section: 23
  Description of the overall algorithm: The Trie class is used to store the data given and perform functions on the trie. 
*/

import java.util.LinkedList;

/** The trie data structure is used to store each character in a node.*/
public class Trie {

  // Nested node class
  public static class Node {
    char Data; 
    LinkedList<Node> children = new LinkedList<>();
    boolean end = false;
    Node Parent;

    public Node(char Data) {
      this.Data = Data;
    }

    // Gets the child of given char
    public Node getChildren(char c) {
      if (children != null) {
        for (Node child : children) {
          if (child.Data == c) {
            return child;
          }
        }
      }
      return null;
    }
  }
  // End of nested Node class

  // empty root created for Trie
  Node root = new Node(' ');

  /**
   * the insert method checks if the work is already in the trie if not it inserts
   * the word into the trie
   */
  public void insert(String word) {
    if (search(word)) {
      return;
    } else {
      Node curr = root;
      for (char c : word.toCharArray()) {
        Node child = curr.getChildren(c);
        if (child == null) {
          child = new Node(c);
          curr.children.add(child);

        }
        curr = child;
      }
      curr.end = true;
    }
  }

  /** the search method iterates through the trie checking if a word exists */
  public boolean search(String word) {
    Node curr = root;
    for (char c : word.toCharArray()) {
      Node child = curr.getChildren(c);
      if (child == null) {
        return false;
      }
      curr = child;
    }
    return curr.end;
  }

  /** autocomplete takes in the partial query and finds the node with the last char of the given
   * prefix and then calls the builder function*/
  public LinkedList<String> autocomplete(String prefix, int count) {
    Node curr = root;
    LinkedList<String> complete = new LinkedList<String>();
    for (char ch : prefix.toCharArray()) { // find end of prefix
      curr = curr.getChildren(ch);
      if (curr == null)
        return new LinkedList<String>();
    }
    builder(curr, complete, prefix, count);
    return complete;
  }

  /** the builder function uses recursion to help build the words*/
  public void builder(Node node, LinkedList<String> complete, String prefix, int count) {
    if (count == 0) {
      return;
    }
    if (node.end) {
      complete.add(prefix);
      count--;
      if (count == 0) {
        return;
      }
    }
    for (Node child : node.children) {
      builder(child, complete, prefix + child.Data, count);
      if (count == 0) {
        return;
      }
    }
  }
}
