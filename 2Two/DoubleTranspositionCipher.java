import java.util.Scanner;

public class DoubleTranspositionCipher {
    
    // Row Transposition - Create matrix by filling row-wise
    public static char[][] rowTransposition(String text, int cols) {
        text = text.replaceAll("\\s", "").toUpperCase();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] matrix = new char[rows][cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (index < text.length()) ? text.charAt(index++) : 'X';
            }
        }
        return matrix;
    }
    
    // Columnar Transposition - Read columns according to key order
    public static String columnarTransposition(char[][] matrix, int[] key) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        StringBuilder cipher = new StringBuilder();
        
        // Read columns in the order specified by key
        for (int k = 1; k <= key.length; k++) {
            for (int j = 0; j < cols; j++) {
                if (key[j] == k) { 
                    for (int i = 0; i < rows; i++) {
                        cipher.append(matrix[i][j]);
                    }
                    break;
                }
            }
        }
        return cipher.toString();
    }
    
    // Reverse Columnar Transposition - Fill matrix column-wise according to key order
    public static char[][] reverseColumnarTransposition(String ciphertext, int[] key, int rows) {
        int cols = key.length;
        char[][] matrix = new char[rows][cols];
        int index = 0;
        
        // Fill columns in the order specified by key
        for (int k = 1; k <= key.length; k++) {
            for (int j = 0; j < cols; j++) {
                if (key[j] == k) {
                    for (int i = 0; i < rows; i++) {
                        matrix[i][j] = ciphertext.charAt(index++);
                    }
                    break;
                }
            }
        }
        return matrix;
    }
    
    // Reverse Row Transposition - Read matrix row-wise to get plaintext
    public static String reverseRowTransposition(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        StringBuilder plaintext = new StringBuilder();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plaintext.append(matrix[i][j]);
            }
        }
        return plaintext.toString();
    }
    
    // Encrypt function
    public static String encrypt(String plaintext, int cols, int[] key) {
        // Step 1: Row transposition
        char[][] matrix = rowTransposition(plaintext, cols);
        
        // Step 2: Columnar transposition
        String encrypted = columnarTransposition(matrix, key);
        
        return encrypted;
    }
    
    // Decrypt function
    public static String decrypt(String ciphertext, int cols, int[] key) {
        // Calculate number of rows
        int rows = ciphertext.length() / key.length;
        
        // Step 1: Reverse columnar transposition
        char[][] matrix = reverseColumnarTransposition(ciphertext, key, rows);
        
        // Step 2: Reverse row transposition
        String decrypted = reverseRowTransposition(matrix);
        
        return decrypted;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter plaintext: ");
        String plaintext = sc.nextLine();
        
        System.out.print("Enter number of columns (for row transposition): ");
        int cols = sc.nextInt();
        sc.nextLine(); 
        
        System.out.print("Enter columnar key (space separated, e.g., 3 1 2): ");
        String[] keyInput = sc.nextLine().split(" ");
        int[] key = new int[keyInput.length];
        for (int i = 0; i < keyInput.length; i++) {
            key[i] = Integer.parseInt(keyInput[i]);
        }
        
        // Encrypt
        String encrypted = encrypt(plaintext, cols, key);
        System.out.println("Encrypted text: " + encrypted);
        
        // Decrypt
        String decrypted = decrypt(encrypted, cols, key);
        System.out.println("Decrypted text: " + decrypted);
        
        sc.close();
    }
}