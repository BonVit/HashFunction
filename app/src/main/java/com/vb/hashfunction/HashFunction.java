package com.vb.hashfunction;

import java.nio.charset.Charset;

/**
 * Created by bonar on 4/13/2017.
 */

public class HashFunction {
    private static final String TAG = "HashFunction";

    private static final int BLOCK_SIZE = 8;
    private static final long H0 = 437854839843L;

    public static long getHash(String text) {

        long prevH = H0;

        long hash = 0;

        if(text.length() <= BLOCK_SIZE)
        {
            byte[] m = text.getBytes(Charset.forName("UTF-8"));
            hash = f(m, prevH);
            return hash;
        }

        for(int i = 0; i + BLOCK_SIZE - 1 < text.length(); i += BLOCK_SIZE) {
            String sub = text.substring(i, i + BLOCK_SIZE);
            byte[] m = sub.getBytes(Charset.forName("UTF-8"));
            hash = f(m, prevH);
            prevH = hash;
        }

        return hash;
    }

    private static long f(byte[] m, long prevH) {
        FeistelNet feistelNet = new FeistelNet();

        String encrypted = feistelNet.crypt(new String(m, Charset.forName("UTF-8")), prevH, true);

        byte[] encBytes = encrypted.getBytes(Charset.forName("UTF-8"));

        long e = ArrayUtills.byteArrayToLong(encBytes);
        long ml = ArrayUtills.byteArrayToLong(m);

        long h = e ^ ml ^ prevH;
        return h;
    }
}
