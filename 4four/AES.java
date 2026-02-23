import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class AES {
    public static void main(String[] args) {
        try {
            // 1. Generate an AES-128 Key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); 
            SecretKey secretKey = keyGen.generateKey();

            // 2. Generate a random Initialization Vector (IV)
            // Required for CBC mode to ensure uniqueness
            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Plain Text: ");
            String input = sc.nextLine();

            // --- ENCRYPTION ---
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            String encodedString = Base64.getEncoder().encodeToString(encryptedBytes);
            
            System.out.println("Encrypted text: " + encodedString);

            // --- DECRYPTION ---
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            
            System.out.println("Decrypted Message: " + new String(decryptedBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}