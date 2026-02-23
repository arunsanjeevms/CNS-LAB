import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AESExample {

    public static void main(String[] args) throws Exception {

        // 1️⃣ Generate AES Key (128-bit)
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        // 2️⃣ Create Cipher
        Cipher cipher = Cipher.getInstance("AES");

        String originalText = "Hello Aswin";

        // 3️⃣ Encrypt
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(originalText.getBytes());

        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("Encrypted: " + encryptedText);

        // 4️⃣ Decrypt
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

        String decryptedText = new String(decryptedBytes);
        System.out.println("Decrypted: " + decryptedText);
    }
}
