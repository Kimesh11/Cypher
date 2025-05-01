package hyperionDev.task_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Poetry {

  private static int cypherLetterCode;
  private static int shift = 15;
  private static int startOfUppercaseRange = 65; // Beginning of upper case letters char code: A.
  private static int endOfUppercaseRange = 90; // End of upper case letters char code: Z.
  private static int startOfLowercaseRange = 97; // Beginning of lower case letters char code: a.
  private static int endOfLowercaseRange = 122; // End of lower case letters char code: z.

  public static void main(String[] args) {
 // Try-with-resources to ensure automatic closing of resources.
    try (BufferedReader br = new BufferedReader(new FileReader("src/resources/capitalVowels.txt"));
        FileWriter fw = new FileWriter(new File("src/resources/reversePoem.txt"));) {

      // Read file line by line and decode each line.
      String line;
      while (Objects.nonNull(line = br.readLine())) {
        String reverseCypheredLine = getDecodedCypher(line);
        System.out.println(reverseCypheredLine); // Print for debugging
        fw.write(reverseCypheredLine + System.lineSeparator());
      }
    } catch (FileNotFoundException e) {
      System.err.println("File not found: " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      System.err.println("IOException occurred: " + e.getMessage());
      e.printStackTrace();
    }

  }

  /**
   * Encrypts an upper-case character using a shift cipher. If the shifted
   * character goes past 'Z', it wraps around to the beginning of the alphabet.
   */
  public static String getUpperCaseCypherChar(char character) {
    if (Character.isUpperCase(character)) {
      int charCode = (int) character; // Get ASCII value of the character.
      cypherLetterCode = charCode - shift; // Reverse the shift for decryption.

      // Wrap around if shift goes past 'A'.
      if (cypherLetterCode < startOfUppercaseRange) {
        cypherLetterCode = endOfUppercaseRange - (startOfUppercaseRange - cypherLetterCode - 1) % 26;
      }
      char chCode = (char) cypherLetterCode;
      return String.valueOf(chCode);
    }
    return String.valueOf(character);
  }

  /**
   * Encrypts a lower-case character using a shift cipher. If the shifted
   * character goes past 'z', it wraps around to the beginning of the lower-case
   * letters. Additionally, vowels ('a', 'e', 'i', 'o', 'u') are capitalized.
   */
  public static String getLowerCaseCypherChar(char character) {
    if (Character.isLowerCase(character)) {
      int charCode = (int) character; // Get ASCII value of the character.
      cypherLetterCode = charCode - shift; // Reverse the shift for decryption.

      // Wrap around if shift goes past 'a'.
      if (cypherLetterCode < startOfLowercaseRange) {
        cypherLetterCode = endOfLowercaseRange - (startOfLowercaseRange - cypherLetterCode - 1) % 26;
      }
      char chCode = (char) cypherLetterCode;

        return String.valueOf(chCode);
    }
    return String.valueOf(character);
  }


  /**
   * Encrypts a given sentence using the shift cipher. Each letter is replaced
   * with its shifted counterpart while non-alphabetic characters remain
   * unchanged.
   */
  public static String getDecodedCypher(String sentence) {
    StringBuilder cypher = new StringBuilder();
    if (sentence.length() > 1) {
      char[] letterArr = sentence.toCharArray();

      // Decode each letter
      for (char c : letterArr) {
        if (Character.isUpperCase(c)) {
          cypher.append(getUpperCaseCypherChar(c)); // Decode upper-case letters
        } else if (Character.isLowerCase(c)) {
          cypher.append(getLowerCaseCypherChar(c)); // Decode lower-case letters
        } else {
          cypher.append(c); // Leave non-alphabetic characters unchanged
        }
      }
    }
    // return reversed string
    return cypher.reverse().toString();
  }

}
