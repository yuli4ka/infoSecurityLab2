import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final String filePath1 = "input1.txt";
    private static final String filePath2 = "input2.txt";
    private static final String filePath3 = "input3.txt";
    public static final String key = "This is my test key";

    private static byte[] getBytesFromFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        StringBuilder data = new StringBuilder();
        while (scanner.hasNextLine()) {
            data.append(scanner.nextLine());
        }
        return data.toString().getBytes();
    }

    private static void checkRC4OnTime(String filePath) throws FileNotFoundException {
        byte[] dataBytes = getBytesFromFile(filePath);

        long startTime = System.nanoTime();

        int n = 1000;
        for (int i = 0; i < n; i++) {
            RC4 rc4 = new RC4(key.getBytes());
            rc4.crypt(dataBytes);
        }

        long endTime = System.nanoTime();
        System.out.println("RC4: " + ((endTime - startTime) / 1000000.0) / n + " milliseconds");
    }

    private static void checkSalsa20OnTime(String filePath) throws FileNotFoundException {
        byte[] dataBytes = getBytesFromFile(filePath);

        int symIVSize = 8;
        byte[] iv = new byte[symIVSize];

        long startTime = System.nanoTime();

        int n = 1000;
        for (int i = 0; i < n; i++) {
            Salsa20 salsa20 = new Salsa20();
            salsa20.init(key.getBytes(), iv);
            salsa20.crypt(dataBytes, 0, dataBytes.length);
        }

        long endTime = System.nanoTime();
        System.out.println("Salsa20: " + ((endTime - startTime) / 1000000.0) / n + " milliseconds");
    }

    private static void checkRC4() {
        String data = "my little pony";

        RC4 rc4 = new RC4(key.getBytes());
        byte[] encrypt_data = rc4.crypt(data.getBytes());
        System.out.println(new String(encrypt_data));

        rc4 = new RC4(key.getBytes());
        byte[] decrypt_data = rc4.crypt(encrypt_data);
        System.out.println(new String(decrypt_data));

        assert (Arrays.equals(data.getBytes(), decrypt_data));
    }

    private static void checkSalsa20() {
        int symIVSize = 8;
        byte[] iv = new byte[symIVSize];
        byte[] inputBytes = "saaaalsa and tea".getBytes();

        Salsa20 salsa20 = new Salsa20();
        salsa20.init(key.getBytes(), iv);
        byte[] encrypt_data = salsa20.crypt(inputBytes, 0, inputBytes.length);
        System.out.println(new String(encrypt_data));

        salsa20 = new Salsa20();
        salsa20.init(key.getBytes(), iv);
        byte[] decrypt_data = salsa20.crypt(encrypt_data, 0, encrypt_data.length);
        System.out.println(new String(decrypt_data));

        assert (Arrays.equals(inputBytes, decrypt_data));
    }

    private static void checkOnTime() throws FileNotFoundException {
        checkRC4OnTime(filePath1);
        checkSalsa20OnTime(filePath1);

        System.out.println();

        checkRC4OnTime(filePath2);
        checkSalsa20OnTime(filePath2);

        System.out.println();

        checkRC4OnTime(filePath3);
        checkSalsa20OnTime(filePath3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        checkRC4();
        checkSalsa20();

        System.out.println();

        checkOnTime();
    }

}
