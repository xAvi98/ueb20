package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Producer {
    private static final int min = 0;
    private static final int max = 999;

    public static int produce(){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
