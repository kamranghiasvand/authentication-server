package com.bluebox.security.authenticationserver.common.util;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * @author by kamran ghiasvand
 */
@Component
public class RandomStringGen {

    @SuppressWarnings("SpellCheckingInspection")
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private Random random;
    private char[] symbols;

    public RandomStringGen() {
        this.random = new SecureRandom();
        this.symbols = alphanum.toCharArray();
    }

    public String nextString(int length) {
        char[] buf = new char[length];
        for (int i = 0; i < length; ++i)
            buf[i] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

}
