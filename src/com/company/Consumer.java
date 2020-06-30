package com.company;

import java.util.*;

public class Consumer {

    private final Collection<Integer> collection;
    private final HashMap<Integer, ArrayList<Date>> time;
    private int quersumme;

    public Consumer()
    {
        collection = new ArrayList<>();
        time = new HashMap<>();
    }

    public void consume(int i){
        int temp = 0;
        ArrayList<Date> list = new ArrayList<>();

        long start = System.currentTimeMillis();

        while (0!=i){
            temp = temp + (i % 10);
            i = i / 10;
        }

        long end = System.currentTimeMillis();

        collection.add(temp);
        Date startDate = new Date(start);
        Date endDate = new Date(end);
        list.add(startDate);
        list.add(endDate);

        if (time.containsKey(temp)){
            time.get(temp).addAll(list);
        } else {
            time.put(temp, list);
        }


        quersumme = temp;
    }

    public int numberOfDifferentResults(){
        ArrayList<Integer> done = new ArrayList<>();
        for (int temp : collection) {
            if (!done.contains(temp)) {
                done.add(temp);
            }
        }
        return done.size();
    }

    public int numberOfOccurences (int i){
        int counter = 0;
        for (int temp : collection) {
            if (temp == i) {
                counter++;
            }
        }
        return counter;
    }

    public Collection<Integer> getCrossTotalsAscending(){
        List<Integer> list = new ArrayList<>(collection);
        Collections.sort(list);
        return list;
    }

    public Collection<Integer> getCrossTotalsDescending(){
        List<Integer> list = new ArrayList<>(collection);
        Comparator<Integer> comp = (o1, o2) -> o2 - o1;
        list.sort(comp);
        return list;
    }

    public Collection<Long> getTimestampsForResult(int i){
        Collection col = time.get(i);
        return col;
    }

    public int getQuersumme(){
        return quersumme;
    }
}