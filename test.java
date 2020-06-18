import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws NoSuchAlgorithmException, InputMismatchException {
        if(checkInput(args) == -1) {
            return;
        }
        final int bitSize = 64;
        HashMap<BigInteger, Integer> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();
        byte[] bytes = new byte[bitSize / 4];
        rand.nextBytes(bytes);
        BigInteger key = new BigInteger(bytes);
        map.put(key, rand.nextInt(args.length));
        String originalString = Integer.toString(map.get(key));
        System.out.println("HMAC:");
        System.out.println(output(key.toString()));
        int move;
        while (true) {
            System.out.println("Enter your move:");
            getMenu(args);
            move = scanner.nextInt();
            if (move < args.length && move > 0) {
                break;
            }
            System.out.println("Incorrect number!");
        }
        if (move == 0) {
            return;
        }
        move--;
        getWinner(map.get(key), move, args);
        System.out.println("Your move: " + args[move]);
        System.out.println("Computer move: " + args[map.get(key)]);
        System.out.println("HMAC Key:");
        System.out.println(bytesToHex(bytes));
    }


    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String output(String hash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(
                hash.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    private static int findIndex(String[] arr, String word) {
        for (int i = 0; i < arr.length; i++) {
            if (word.equals(arr[i])) {
                return i;
            }
        }
        System.out.println("Incorrect move");
        return -1;
    }

    private static void getWinner(int cMove, int move, String[] arr) {
        if (move - cMove <= 2 && move - cMove > 0 ) {
            System.out.println("You win!");
            return;
        } else if (move - cMove == 0) {
            System.out.println("Tie!");
            return;
        } else {
            System.out.println("You lose!");
            return;
        }
    }

    private static void getMenu(String[] arr) {
        int i = 1;
        for (String move:arr) {
            System.out.println(i + " - " + move);
            i++;
        }
        System.out.println("0 - exit");
    }

    private static int checkInput (String[] arr) {
        if (arr.length % 2 ==0 || arr.length == 1) {
            System.out.println("Incorrect number of arguments");
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i].equals(arr[j])) {
                    System.out.println("Cannot resolve similar objects");
                    return -1;
                }
            }
        }
        return 0;
    }
}