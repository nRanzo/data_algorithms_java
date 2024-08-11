/*  The CaesarCipher class implements the Caesar cipher encryption and decryption techniques. It initializes 
    encoding and decoding arrays based on a specified rotation value, which must be between 0 and 25. The encrypt 
    method shifts characters in the input message forward by the rotation amount, while decrypt shifts them backward.
    The transform method handles both uppercase and lowercase letters, preserving non-alphabetic characters.
    The class ensures proper rotation range validation and encapsulates its internal encoding and decoding arrays to 
    maintain data integrity. */

public class CaesarCipher {
    private char[] encoder = new char[26];
    private char[] decoder = new char[26];

    // Constructor that initializes the encoding and decoding arrays
    public CaesarCipher(int rotation) {
        // Ensure rotation is within the valid range
        if (rotation < 0 || rotation >= 26) {
            throw new IllegalArgumentException("Rotation must be between 0 and 25.");
        }
        
        for (int k = 0; k < 26; k++) {
            encoder[k] = (char) ('A' + (k + rotation) % 26);
            decoder[k] = (char) ('A' + (k - rotation + 26) % 26);
        }
    }
    
    // Encrypts the message
    public String encrypt(String message) {
        return transform(message, encoder);
    }

    // Decrypts the message
    public String decrypt(String message) {
        return transform(message, decoder);
    }

    // Transforms the message using the provided code
    private String transform(String original, char[] code) {
        StringBuilder result = new StringBuilder();
        for (int k = 0; k < original.length(); k++) {
            char ch = original.charAt(k);
            if (Character.isUpperCase(ch)) {
                int j = ch - 'A';   // value between 0 and 25
                result.append(code[j]);
            } else if (Character.isLowerCase(ch)) {
                int j = ch - 'a';   // value between 0 and 25
                result.append(Character.toLowerCase(code[j]));
            } else {
                // Preserve non-alphabetic characters
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        CaesarCipher cipher = new CaesarCipher(3);

        // Example usage
        String message = "Hope this is Useful, always Learn Something New!";
        String coded = cipher.encrypt(message);
        System.out.println("\n  Coded message: " + coded);
        String decoded = cipher.decrypt(coded);
        System.out.println("Decoded message: " + decoded);

        if (message.equals(decoded)) {
            System.out.println("\nThis works fine.");
        } else {
            System.out.println("\nThere is something wrong.");
        }
    }
}
