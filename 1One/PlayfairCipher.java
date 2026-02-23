import java.util.*;

public class PlayfairCipher {
    private char[][] matrix = new char[5][5];
    public void generateMatrix(String key) {
        boolean[] used = new boolean[26];
        key = key.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        int row = 0, col = 0;
        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                matrix[row][col] = c;
                used[c - 'A'] = true;
                col++;
                if (col == 5) { col = 0; row++; }
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J') continue;
            if (!used[c - 'A']) {
                matrix[row][col] = c;
                col++;
                if (col == 5) { col = 0; row++; }
            }
        }
    }
    private List<String> prepareText(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        List<String> digraphs = new ArrayList<>();
        int i = 0;
        while (i < text.length()) {
            char first = text.charAt(i);
            char second;
            if (i + 1 < text.length()) {
                second = text.charAt(i + 1);
                if (first == second) { 
                    second = 'X';
                    i++; 
                } else {
                    i += 2;
                }
            } else { 
                second = 'X';
                i++;
            }
            digraphs.add("" + first + second);

            if (i == text.length() - 1 && i < text.length()) i++;
        }

        return digraphs;
    }
    private int[] findPosition(char c) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (matrix[i][j] == c) return new int[]{i, j};
        return null;
    }
    public String encrypt(String text) {
        List<String> digraphs = prepareText(text);
        StringBuilder cipher = new StringBuilder();

        for (String pair : digraphs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            if (p1[0] == p2[0]) { 
                cipher.append(matrix[p1[0]][(p1[1] + 1) % 5]);
                cipher.append(matrix[p2[0]][(p2[1] + 1) % 5]);
            } else if (p1[1] == p2[1]) { 
                cipher.append(matrix[(p1[0] + 1) % 5][p1[1]]);
                cipher.append(matrix[(p2[0] + 1) % 5][p2[1]]);
            } else { 
                cipher.append(matrix[p1[0]][p2[1]]);
                cipher.append(matrix[p2[0]][p1[1]]);
            }
        }

        return cipher.toString();
    }
    public String decrypt(String text) {
        List<String> digraphs = new ArrayList<>();
        for (int i = 0; i < text.length(); i += 2)
            digraphs.add(text.substring(i, i + 2));

        StringBuilder plain = new StringBuilder();

        for (String pair : digraphs) {
            char a = pair.charAt(0);
            char b = pair.charAt(1);

            int[] p1 = findPosition(a);
            int[] p2 = findPosition(b);

            if (p1[0] == p2[0]) { 
                plain.append(matrix[p1[0]][(p1[1] + 4) % 5]);
                plain.append(matrix[p2[0]][(p2[1] + 4) % 5]);
            } else if (p1[1] == p2[1]) { 
                plain.append(matrix[(p1[0] + 4) % 5][p1[1]]);
                plain.append(matrix[(p2[0] + 4) % 5][p2[1]]);
            } else {
                plain.append(matrix[p1[0]][p2[1]]);
                plain.append(matrix[p2[0]][p1[1]]);
            }
        }

        return plain.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlayfairCipher pf = new PlayfairCipher();

        System.out.print("Enter key: ");
        String key = sc.nextLine();

        System.out.print("Enter plaintext: ");
        String text = sc.nextLine();

        pf.generateMatrix(key);

        String encrypted = pf.encrypt(text);
        String decrypted = pf.decrypt(encrypted);

        System.out.println("Encrypted text: " + encrypted.toUpperCase());
        System.out.println("Decrypted text: " + decrypted.toUpperCase());

        sc.close();
    }
}
