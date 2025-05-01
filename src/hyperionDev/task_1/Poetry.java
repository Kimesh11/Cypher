package hyperionDev.task_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Poetry {

  private static int cypherLetterCode;
  private static int shift = 15;
  private static int startOfUppercaseRange = 65; // Beginning of upper case letters char code: A.
  private static int endOfUppercaseRange = 90; // End of upper case letters char code: Z.
  private static int startOfLowercaseRange = 97; // Beginning of lower case letters char code: a.
  private static int endOfLowercaseRange = 122; // End of lower case letters char code: z.

  public static void main(String[] args) {
    // Try-with-resources to ensure automatic closing of resources.
    try (Scanner scanner = new Scanner(new File("src/resources/poem.txt"));
        FileWriter fw = new FileWriter(new File("src/resources/encodedPoem.txt"));) {

      // Read file line by line and encode each line.
      while (scanner.hasNextLine()) {
        String cypheredLine = getCypher(scanner.nextLine());
        System.out.println(cypheredLine);
        fw.write(cypheredLine + System.lineSeparator());
      }
    } catch (FileNotFoundException e) { // Handle exception where file is missing.
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
      cypherLetterCode = charCode + shift;

      // Wrap around if shift goes past "Z".
      if (cypherLetterCode > endOfUppercaseRange) {
        cypherLetterCode = startOfUppercaseRange + (cypherLetterCode - startOfUppercaseRange) % 26;
      }
      char chCode = (char) cypherLetterCode; // Convert ASCII back to character.
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
      cypherLetterCode = charCode + shift;

      // Wrap around if shift goes past "Z".
      if (cypherLetterCode > endOfLowercaseRange) {
        cypherLetterCode = startOfLowercaseRange + (cypherLetterCode - startOfLowercaseRange) % 26;
      }
      char chCode = (char) cypherLetterCode; // Convert ASCII back to character.
      return String.valueOf(chCode);
    }
    return String.valueOf(character);
  }

  /**
   * Encrypts a given sentence using the shift cipher. Each letter is replaced
   * with its shifted counterpart while non-alphabetic characters remain
   * unchanged.
   */
  public static String getCypher(String sentence) {
    StringBuilder cypher = new StringBuilder();
    if (sentence.length() > 1) {
      // Converting to an array.
      char[] letterArr = sentence.toCharArray();

      // Convert sentence to a character array for processing.
      for (char c : letterArr) {
        if (Character.isUpperCase(c)) {
          cypher.append(getUpperCaseCypherChar(c)); // Encode upper-case letters.
        } else if (Character.isLowerCase(c)) {
          cypher.append(getLowerCaseCypherChar(c)); // Encode lower-case letters.
        } else {
          cypher.append(c); // Leave non-alphabetic characters unchanged.
        }

      }
    }
    return cypher.toString();
  }

}
