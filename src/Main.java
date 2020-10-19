import java.util.Arrays;

public class Main {

    private static void checkRC4() {
        String data = "my little pony";
        String key = "horse";

        RC4 rc4 = new RC4(key.getBytes());
        byte[] encrypt_data = rc4.encrypt(data.getBytes());
        System.out.println(new String(encrypt_data));

        rc4 = new RC4(key.getBytes());
        byte[] decrypt_data = rc4.decrypt(encrypt_data);
        System.out.println(new String(decrypt_data));

        assert (Arrays.equals(data.getBytes(), decrypt_data));
    }

    public static void main(String[] args) {
        checkRC4();
    }
}
