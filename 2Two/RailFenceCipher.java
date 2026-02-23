import java.util.Scanner;

public class RailFenceCipher {
    public static String encrypt(String text, int rails) {
        text = text.replaceAll("\\s", "").toUpperCase();
        char[][] rail = new char[rails][text.length()];
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < text.length(); j++)
                rail[i][j] = '\n';
        boolean down = false;
        int row = 0, col = 0;
        for (int i = 0; i < text.length(); i++) {
            if (row == 0 || row == rails - 1)
                down = !down;

            rail[row][col++] = text.charAt(i);
            row += down ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < text.length(); j++)
                if (rail[i][j] != '\n')
                    result.append(rail[i][j]);

        return result.toString();
    }
    public static String decrypt(String cipher, int rails) {
        char[][] rail = new char[rails][cipher.length()];

        for (int i = 0; i < rails; i++)
            for (int j = 0; j < cipher.length(); j++)
                rail[i][j] = '\n';

        boolean down = false;
        int row = 0, col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == rails - 1)
                down = !down;

            rail[row][col++] = '*';
            row += down ? 1 : -1;
        }
        int index = 0;
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*' && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);

        StringBuilder result = new StringBuilder();
        down = false;
        row = 0;
        col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0 || row == rails - 1)
                down = !down;

            result.append(rail[row][col++]);
            row += down ? 1 : -1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        System.out.print("Enter number of rails: ");
        int rails = sc.nextInt();

        String encrypted = encrypt(text, rails);
        String decrypted = decrypt(encrypted, rails);

        System.out.println("Encrypted text: " + encrypted);
        System.out.println("Decrypted text: " + decrypted);

        sc.close();
    }
}
