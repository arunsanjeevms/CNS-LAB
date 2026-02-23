import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DESExample {
    public static void main(String[] args) throws Exception {

        // 1. Generate Key
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        SecretKey secretKey = keyGen.generateKey();

        // 2. Create Cipher
        Cipher cipher = Cipher.getInstance("DES");

        // 3. Encrypt
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal("HelloAswin".getBytes());

        // 4. Decrypt
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(encrypted);
        System.out.println("Encrypted: " + new String(encrypted));
        System.out.println("Decrypted: " + new String(decrypted));
    }
}
