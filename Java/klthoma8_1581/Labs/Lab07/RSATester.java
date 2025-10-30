public class RSATester{
    public static void main(String[] args){
        RSA rsa = new RSA("17", "13", "61");
        String message = "Hello Gurl";
        String encrypted = rsa.encrypt(message);
        String decrypted = rsa.decrypt(encrypted);

        System.out.println("Original: " + message);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}