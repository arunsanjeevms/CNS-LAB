import java.util.Scanner;

public class VigenereCipher {
    public static String encrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder cipher = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            int p = text.charAt(i) - 'A';
            int k = key.charAt(keyIndex % key.length()) - 'A';
            int c = (p + k) % 26;
            cipher.append((char) (c + 'A'));
            keyIndex++;
        }
        return cipher.toString();
    }
    public static String decrypt(String text, String key) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder plain = new StringBuilder();
        int keyIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            int c = text.charAt(i) - 'A';
            int k = key.charAt(keyIndex % key.length()) - 'A';
            int p = (c - k + 26) % 26;
            plain.append((char) (p + 'A'));
            keyIndex++;
        }
        return plain.toString();
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();

        System.out.print("Enter key: ");
        String key = sc.nextLine();

        String encrypted = encrypt(plaintext, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("Encrypted text: " + encrypted);
        System.out.println("Decrypted text: " + decrypted);

        sc.close();
    }
}

