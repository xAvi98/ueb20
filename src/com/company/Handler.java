package com.company;

import java.util.*;

public class Handler {

    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayList<>();
        Consumer consumer = new Consumer();
        Random ran = new Random();
        int index = 0;

        for (int i = 0; i < 10000; i++) {
            if (ran.nextInt(2) > 0) {
                collection.add(Producer.produce());
            } else {
                if (collection.size() > index) {
                    ListIterator<Integer> iter = ((AbstractList<Integer>) collection).listIterator(index);
                    index++;
                    if (iter.hasNext()) {
                        int toConsume = iter.next();
                        consumer.consume(toConsume);
                    }
                }
            }
        }
        System.out.println(consumer.numberOfDifferentResults());
        System.out.println(consumer.numberOfOccurences(10));
        System.out.println(consumer.getCrossTotalsAscending());
        System.out.println(consumer.getCrossTotalsDescending());
        System.out.println(consumer.getTimestampsForResult(10));
    }
}
