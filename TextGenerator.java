import java.util.HashMap;
import java.util.Map;
// import java.util.Random;
import java.util.Scanner;

public class TextGenerator {

    public static void main(String[] args) {
        // High K  = more context, more distinct keys, more complexity
        // High M = more text generated, higher chance of repetition
        try {
            // Read the order of the markov model K and the number of characters M to generate 
            int K = Integer.parseInt(args[0]);
            int M = Integer.parseInt(args[1]);

            // Read the input text from standard input
            Scanner scanner = new Scanner(System.in);
            StringBuilder input = new StringBuilder();
            while (scanner.hasNextLine()) {
                input.append(scanner.nextLine()).append(" ");
            }
            scanner.close();

            // Replace whitespace characters with spaces or underscores
            String inputText = input.toString().replaceAll("\\s+", " ").replace(" ", "_");

            // Symbol table to store K-character substrings and their counts
            Map<String, Markov> symbolTable = new HashMap<>();

            // Process the input text
            for (int i = 0; i <= inputText.length() - K; i++) {
                String substring = inputText.substring(i, i + K);

                // Add a new Markov object if the substring is not in the table
                if (!symbolTable.containsKey(substring)) {
                    symbolTable.put(substring, new Markov(substring));
                }
                symbolTable.get(substring).add();
                if (i + K < inputText.length()) {
                    char nextChar = inputText.charAt(i + K);
                    symbolTable.get(substring).add(nextChar);
                }
            }

            // Print the language model if it contains less than 100 K-tuples
            if (symbolTable.size() < 100) {
                System.out.println(symbolTable.size() + " distinct keys");
                for (Map.Entry<String, Markov> entry : symbolTable.entrySet()) {
                    System.out.println(entry.getValue().count + " " + entry.getKey() + ": " + entry.getValue().suffixCountsToString());
                }
            }

            // Start the text generation with the first K characters of the input text
            StringBuilder generatedText = new StringBuilder(inputText.substring(0, K));
            String currentSubstring = inputText.substring(0, K);

            // Generate M characters according to the Markov model
            for (int i = 0; i < M - K; i++) {
                if (symbolTable.containsKey(currentSubstring)) {
                    char nextChar = symbolTable.get(currentSubstring).random();
                    generatedText.append(nextChar);
                    currentSubstring = generatedText.substring(generatedText.length() - K);
                } else {
                    break; // If no such substring exists, break the loop
                }
            }

            // Print the generated text
            System.out.println(generatedText.toString().replace("_", " "));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}



/*
 * I wrote this project to try out Natural Language Modeling/Text Processing.
 * I built a K order Markov model from an input text. 
 * Markov models are widely used in speech recognition, handwriting recognition, 
 * information retrieval, and data compression.
 * For this project, I focused on a unique application—generating 
 * stylized pseudo-random text using the developed Markov model.
 */






