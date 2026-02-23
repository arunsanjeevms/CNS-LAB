import java.util.Scanner;

public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        String result = "";
        shift = shift % 26;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isUpperCase(ch)) {
                result += (char) ((ch - 'A' + shift) % 26 + 'A');
            } 
            else if (Character.isLowerCase(ch)) {
                result += (char) ((ch - 'a' + shift) % 26 + 'a');
            } 
            else {
                result += ch;
            }
        }
        return result;
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift).toUpperCase();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = sc.nextLine();

        System.out.print("Enter shift value: ");
        int shift = sc.nextInt();
        if (shift != 3) {
            System.out.println("Invalid key! Caesar Cipher key must be 3.");
            sc.close();
            return;
        }

        String encryptedText = encrypt(text, shift);
        String decryptedText = decrypt(encryptedText, shift);

        System.out.println("Encrypted text: " + encryptedText);
        System.out.println("Decrypted text: " + decryptedText);

        sc.close();
    }
}
