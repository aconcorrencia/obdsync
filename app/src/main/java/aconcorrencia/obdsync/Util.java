package aconcorrencia.obdsync;

import java.math.BigInteger;

/**
 * Created by Sillas on 06/10/2016.
 */
public class Util {
    static String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }
    static int hexToDec(String s) {
        return Integer.valueOf(String.valueOf(s), 16);
    }
}
