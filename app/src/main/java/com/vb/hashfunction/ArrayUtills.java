package com.vb.hashfunction;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by bonar on 3/29/2017.
 */

public class ArrayUtills {
    public static int byteArrayToInt(final byte[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static int byteArrayToInt(final int[] b)
    {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static long byteArrayToLong(final byte[] b)
    {
        byte[] a = new byte[8];
        int k = 0;
        for(int i = 7; i >= 0; i--)
            if(k < b.length) {
                a[i] = b[k];
                k++;
            }
            else
                a[i] = 0;
        ByteBuffer buffer = ByteBuffer.wrap(a);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getLong();
    }

    public static byte getByte(int a, int pos)
    {
        return (byte) (a >>> (pos * 8));
    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    public static final byte merge2Bytes(byte a, byte b)
    {
        a = (byte) (a << 4);
        return (byte) (a | b);
    }

    public static final int merge2Bytes(int a, int b)
    {
        a = (int) (a << 4);
        a &= 255;
        b &= 255;
        return (int) (a | b);
    }

    public static final byte[] intTo4BitsArray(int value) {
        return new byte[] {
                (byte)((value >>> 28) % 16),
                (byte)((value >>> 24) % 16),
                (byte)((value >>> 20) % 16),
                (byte)((value >>> 16) % 16),
                (byte)((value >>> 12) % 16),
                (byte)((value >>> 8) % 16),
                (byte)((value >>> 4) % 16),
                (byte) (value % 16)};
    }

    public static final int[] intTo4BitsArray(long value) {
        return new int[] {
                (byte)((value >>> 28) % 16),
                (byte)((value >>> 24) % 16),
                (byte)((value >>> 20) % 16),
                (byte)((value >>> 16) % 16),
                (byte)((value >>> 12) % 16),
                (byte)((value >>> 8) % 16),
                (byte)((value >>> 4) % 16),
                (byte) (value % 16)};
    }

    public static byte[] mergeByteArrays(byte[] a, byte[] b)
    {
        byte[] result = new byte[a.length + b.length];
        int i = 0;
        for(int j = 0; j < a.length; j++, i++)
            result[i] += a[j];
        for(int j = 0; j < b.length; j++, i++)
            result[i] += b[j];

        return result;
    }

    public static byte[] subByteArray(final byte[] src, int srcPos, int dstSize)
    {
        if(src.length < dstSize || src.length == 0)
            return null;

        byte[] dst = new byte[dstSize];

        for(int i = 0; i < dst.length; i++)
            dst[i] = src[srcPos + i];

        return dst;
    }
}
