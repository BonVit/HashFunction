package com.vb.hashfunction;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.BitSet;

/**
 * Created by bonar on 3/29/2017.
 */

public class FeistelNet {
    private static final String TAG = "FeistelNet";

    private static final int MAX_ROUNDS = 16;

    private static final int BLOCK_SIZE = 8;

    public String crypt(String s, long key, boolean encrypt) {

        String result = "";

        while(s.length() % BLOCK_SIZE != 0)
            s += " ";

        for(int i = 0; i + BLOCK_SIZE - 1 < s.length(); i += BLOCK_SIZE) {
            String sub = s.substring(i, i + BLOCK_SIZE);
            byte[] block = sub.getBytes(Charset.forName("UTF-8"));
            result += new String(getBlock(block, key, encrypt), Charset.forName("UTF-8"));
        }

        return result;
    }

    public byte[] getBlock(final byte[] block, long key, boolean encrypt) {
        if (block.length != BLOCK_SIZE)
            return null;

        BitSet l = BitsUtills.bitSetFromInt(ArrayUtills.byteArrayToInt(ArrayUtills.subByteArray(block, 0, BLOCK_SIZE / 2)));
        BitSet r = BitsUtills.bitSetFromInt(ArrayUtills.byteArrayToInt(ArrayUtills.subByteArray(block, BLOCK_SIZE / 2 + BLOCK_SIZE % 2, BLOCK_SIZE / 2)));
        Log.d(TAG, l.toString() + " " + l.length());
        Log.d(TAG, r.toString() + " " + r.length());

        int round = (encrypt) ? 1 : MAX_ROUNDS;

        for(int i = 0; i < MAX_ROUNDS; i++)
        {
            Log.d(TAG, "l: " + BitsUtills.bitSetToInt(l));
            Log.d(TAG, "r: " + BitsUtills.bitSetToInt(r));

            if (i < MAX_ROUNDS - 1)
            {
                BitSet t = (BitSet) l.clone();
//                l = r ^ f(l, round);
                l = (BitSet) r.clone();
                l.xor(f(t, key));
                r = t;
            } else
                r.xor(f(l, key));

            Log.d(TAG, "l: " + BitsUtills.bitSetToInt(l));
            Log.d(TAG, "r: " + BitsUtills.bitSetToInt(r));


            Log.d(TAG, "Round: " + round);
            round += (encrypt) ? 1 : -1;
        }

        byte[] la = new byte[BLOCK_SIZE / 2];
        byte[] ra = new byte[BLOCK_SIZE / 2];
        byte[] il = l.toByteArray();
        byte[] ir = r.toByteArray();
        for(int i = la.length - 1, al = 0, ar = 0; i >= 0; i--)
        {
            if(al >= il.length)
                la[i] = 0;
            else {
                la[i] = il[al];
                al++;
            }
            if(ar >= ir.length)
                ra[i] = 0;
            else {
                ra[i] = ir[ar];
                ar++;
            }
        }

        return ArrayUtills.mergeByteArrays(la, ra);
    }

    private BitSet f(BitSet a, long k)
    {
        k %= 16;
        long number = BitsUtills.bitSetToInt(a) % k;

        int[] arr = ArrayUtills.intTo4BitsArray(number);
        int[] i = mixArray(arr);

        return BitsUtills.bitSetFromInt(ArrayUtills.byteArrayToInt(i));
}

    private int[] mixArray(int[] arr)
    {
        int[] tmp = new int[arr.length];

        final int[][] CODE_TABLE = new int[][] {
                {0, 7, 3, 4, 6, 2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11},
                {7, 3, 4, 6, 2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0},
                {3, 4, 6, 2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7},
                {4, 6, 2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7, 3},
                {6, 2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7, 3, 4},
                {2, 1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7, 3, 4, 6},
                {1, 8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7, 3, 4, 6, 2},
                {8, 10, 13, 15, 14, 9, 12, 5, 11, 0, 7, 3, 4, 6, 2, 1},
        };

        for(int i = 0; i < arr.length; i++) {
            int t = arr[i];
            t = (t <= 0) ? (int) -t: t;
            tmp[i] = CODE_TABLE[i][t];
        }
        int[] result = new int[tmp.length / 2];

        for(int i = 0; i < result.length; i++)
            result[i] = ArrayUtills.merge2Bytes(tmp[i * 2], tmp[i * 2 + 1]);

        return result;
    }
}
