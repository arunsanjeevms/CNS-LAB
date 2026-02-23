import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.util.*;

public class DES {
    public static void main(String[] argv) {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey myDesKey = keygenerator.generateKey();
            
            Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter Plain Text: ");
            String w = sc.nextLine();
            byte[] text = w.getBytes();

            // --- ENCRYPTION ---
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(text);
            
            // Use Base64 to make the encrypted bytes readable
            String encryptedString = Base64.getEncoder().encodeToString(textEncrypted);
            System.out.println("Encrypted Message : " + encryptedString);

            // --- DECRYPTION ---
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            byte[] textDecrypted = desCipher.doFinal(textEncrypted);
            
            System.out.println("Decrypted Message: " + new String(textDecrypted));

        } catch (Exception e) {
            // Simplified catch for brevity
            e.printStackTrace();
        }
    }
}