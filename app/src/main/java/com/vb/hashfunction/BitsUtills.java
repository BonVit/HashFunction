package com.vb.hashfunction;

import java.util.BitSet;

/**
 * Created by bonar on 4/11/2017.
 */

public class BitsUtills {
    public static BitSet bitSetFromInt(int n)
    {
        String bits = Integer.toBinaryString(n);
        BitSet result = new BitSet(bits.length());
        for(int i = bits.length() - 1; i >= 0; i--)
        {
            if(bits.charAt(i) == '1')
                result.set(bits.length() - i - 1);
            else
                result.clear(bits.length() - i - 1);
        }
        return result;
    }

    public static int bitSetToInt(BitSet n)
    {
        int result = 0;
        for(int i = 0 ; i < 32; i++)
            if(n.get(i))
                result |= (1 << i);
        return result;
    }
}
