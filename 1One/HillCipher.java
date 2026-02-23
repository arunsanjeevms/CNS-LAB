// 

import java.util.Scanner;

public class HillCipher {
    static int[][] key;
    static int size;

    static String encrypt(String text) {
        text = text.toUpperCase().replaceAll("[^A-Z]", "");
        while (text.length() % size != 0) {
            text += "X";
        }
        return process(text, key);
    }

    // Decrypt function
    static String decrypt(String cipher) {
        int[][] inverseKey = getInverseMatrix(key, size);
        return process(cipher, inverseKey);
    }

    // Helper to process matrix multiplication for both enc/dec
    static String process(String text, int[][] currentKey) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += size) {
            int[] block = new int[size];
            for (int j = 0; j < size; j++) {
                block[j] = text.charAt(i + j) - 'A';
            }
            for (int col = 0; col < size; col++) {
                int sum = 0;
                for (int row = 0; row < size; row++) {
                    sum += block[row] * currentKey[row][col];
                }
                result.append((char) (((sum % 26) + 26) % 26 + 'A'));
            }
        }
        return result.toString();
    }

    // Finds the modular inverse matrix (K^-1 mod 26)
    static int[][] getInverseMatrix(int[][] m, int n) {
        int det = determinant(m, n);
        det = ((det % 26) + 26) % 26;
        int invDet = -1;

        // Find modular multiplicative inverse of determinant
        for (int x = 1; x < 26; x++) {
            if ((det * x) % 26 == 1) {
                invDet = x;
                break;
            }
        }

        if (invDet == -1) throw new RuntimeException("Key is not invertible!");

        int[][] adj = adjoint(m);
        int[][] inv = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = ((adj[i][j] * invDet) % 26 + 26) % 26;
            }
        }
        return inv;
    }

    // Helper to find Adjoint (for inverse calculation)
    static int[][] adjoint(int[][] m) {
        int[][] adj = new int[size][size];
        if (size == 1) { adj[0][0] = 1; return adj; }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int[][] temp = getCofactor(m, i, j);
                int sign = ((i + j) % 2 == 0) ? 1 : -1;  // FIXED: Changed from % 22 to % 2
                // Transpose while calculating cofactor to get adjoint
                adj[j][i] = (sign * determinant(temp, size - 1));
            }
        }
        return adj;
    }

    static int[][] getCofactor(int[][] m, int p, int q) {
        int[][] temp = new int[size][size];
        int i = 0, j = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = m[row][col];
                    if (j == size - 1) { j = 0; i++; }
                }
            }
        }
        return temp;
    }

    static int determinant(int[][] m, int n) {
        if (n == 1) return m[0][0];
        int D = 0, sign = 1;
        for (int f = 0; f < n; f++) {
            int[][] temp = getCofactor(m, 0, f);
            D += sign * m[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }
        return D;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of matrix: ");
        size = sc.nextInt();
        key = new int[size][size];
        System.out.println("Enter key matrix:");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                key[i][j] = sc.nextInt();

        sc.nextLine(); 
        System.out.print("Enter message: ");
        String msg = sc.nextLine();

        String encrypted = encrypt(msg);
        System.out.println("Encrypted: " + encrypted);
        
        // Calling decrypt and printing finally
        String decrypted = decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);

        sc.close();
    }
}