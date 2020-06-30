package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lager {
    private final Map <Integer, Artikel> lager;
    private int key;

    private static final String MSG_ARTIKELNR = "Die Artikelnummer muss 4-stellig sein!";
    private static final String MSG_NICHTGEFUNDEN = "Die Artikelnummer wurde nicht gefunden.";
    private static final String MSG_MENGE = "Die Menge muss positiv sein!";
    private static final String MSG_PREIS = "Der Preis darf nicht kleiner als 0 sein!";
    private static final String MSG_INDEX = "Der Index darf nicht kleiner als 0 sein!";
    private static final String MSG_NICHTVORHANDEN = "Diese Stelle ist nicht vorhanden!";
    private static final String MSG_VORHANDEN = "Diese Artikelnummer existiert bereits!";



    /**
     * Konstruktor
     */
    public Lager(){
        lager = new HashMap<>();
    }

    /**
     * Methode zum Anlegen eines Artikels im Lager
     *
     * Lager darf nicht voll sein
     * Artikelnummer darf nicht vorhanden sein
     *
     * @param artikel   Artikelobjekt das angelegt wird
     */
    public void legeAnArtikel(Artikel artikel){
        Validator.check(lager.containsKey(artikel.getArtikelNr()), MSG_VORHANDEN);
        lager.put(artikel.getArtikelNr(), artikel);
        key++;
    }

    /**
     * Methode zum Entfernen eines Artikels mittels der Artikelnr
     * @param artikelNr    Muss 4-stellig sein
     */
    public void entferneArtikel(int artikelNr){
        Validator.check(artikelNr < 1000 || artikelNr > 9999, MSG_ARTIKELNR);
        lager.entrySet().removeIf(entry -> (artikelNr == entry.getKey()));
    }

    /**
     * Methode zum Buchen von Zugang
     *
     * @param artikelNr     Muss 4-stellig sein
     * @param menge         Muss positiv sein
     */
    public void bucheZugang(int artikelNr, int menge){
        int stelle = bestandOperationen(artikelNr, menge);
        getArtikel(stelle).bucheZugang(menge);
    }

    /**
     * Hilfsmethode zum Auslagern der Operationen die <br>
     *     den Artikel suchen um den Bestand zu aendern
     *
     * @param artikelNr     Muss 4-stellig sein
     * @param menge         Darf nicht negativ sein
     *
     * @return      int stelle des Artikels
     */
    private int bestandOperationen(int artikelNr, int menge) {
        Validator.check(artikelNr < 1000 || artikelNr > 9999, MSG_ARTIKELNR);
        Validator.check(menge < 0, MSG_MENGE);
        int stelle = -1;
        for (int i = 0; i < key; i++){
            if(getArtikel(i).getArtikelNr() == artikelNr){
                stelle = i;
            }
        }
        Validator.check(stelle == -1, MSG_NICHTGEFUNDEN);
        return stelle;
    }

    /**
     * Methode zum Buchen von Abgang
     *
     * @param artikelNr     Muss 4-stellig sein
     * @param menge         Muss positiv sein
     */
    public void bucheAbgang(int artikelNr, int menge){
        int stelle = bestandOperationen(artikelNr, menge);
        getArtikel(stelle).bucheAbgang(menge);
    }

    /**
     * Methode zum Aendern des Preises aller Artikel
     *
     * @param prozent   Darf nicht kleiner als -100 sein
     */
    public void aenderePreisAllerArtikel(double prozent){
        Validator.check(prozent < -100, MSG_PREIS);
        for (Map.Entry<Integer, Artikel> integerArtikelEntry : lager.entrySet()) {
            integerArtikelEntry.getValue().aenderePreis(prozent);
        }
    }

    /**
     * Methode zum Ausgeben eines bestimmten Artikels mittels Index
     *
     * @param index     Darf nicht negativ sein
     */
    public Artikel getArtikel(int index){
        Validator.check(index < 0, MSG_INDEX);
        Validator.check(index >= key, MSG_NICHTVORHANDEN);
        int i = 0;
        Iterator<Map.Entry<Integer, Artikel>> iter = lager.entrySet().iterator();
        Artikel temp = iter.next().getValue();
        while (iter.hasNext() && i < index){
            temp = iter.next().getValue();
            i++;
        }
        return temp;
    }

    /**
     * Methode zur Ausgabe des Lagers
     *
     * @return String   komplettes Lager
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key; i++) {
            sb.append(getArtikel(i));
        }
        return sb.toString();
    }

    /**
     * Methode zum Ausgeben der Artikelanzahl
     *
     * @return  int pointer
     */
    public int getArtikelAnzahl(){
        return key;
    }

    /**
     * Methode zum Ausgeben der Bestandsliste
     *
     * @return String   StringBuilder sb.toString()
     */
    public String ausgebenBestandsListe(){
        final String nr = "ArtNr";
        final String desc = "Beschreibung";
        final String preis = "Preis";
        final String bestand = "Bestand";
        final String total = "Gesamt";
        final String type = "Typ";
        final String gesamtwert = "Gesamtwert:";

        StringBuilder sb = new StringBuilder();
        sb.append("\nLagerort: Alt-Saarbrücken\n\n");
        sb.append(String.format("%-8s", nr))
                .append(String.format("%-32s", desc))
                .append(String.format("%-8s", preis))
                .append(String.format("%-8s", bestand))
                .append(String.format("%-10s", total))
                .append(type + "\n")
                .append("-------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < key; i++){
            Artikel artikel = getArtikel(i);
            sb.append(String.format("%-8d", artikel.getArtikelNr()));
                    if(getArtikel(i).getClass().getSimpleName().equals("Artikel")) {
                        sb.append(String.format("%-32s", artikel.getBezeichnung()));
                    }else{
                        sb.append(String.format("%-32s", artikel.getBeschreibung()));
                    }
                    sb.append(String.format("%-8.2f", artikel.getPreis()))
                    .append(String.format("%-8d", artikel.getBestand()))
                    .append(String.format("%-10.2f", artikel.getGesamt()))
                    .append(artikel.getClass().getSimpleName()).append("\n");
        }
        sb.append("\n-------------------------------------------------------------------------------------------------------")
                .append(String.format("\n%-56s", gesamtwert)).append(getGesamtwert());
        return sb.toString();
    }

    /**
     * Methode zum Finden eines Artikels mittels der Artikelnr
     *
     * @param artikelnr     muss 4-stellig sein
     * @return      int stelle oder -1 wenn nicht gefunden
     */
    public int findeArtikel(int artikelnr){
        for (int i = 0; i < key; i++){
            if (lager.get(i).getArtikelNr() == artikelnr){
                return i;
            }
        }
        return -1;
    }

    /**
     * get-Methode zum Ermitteln des Gesamtwertes aller Artikel im Lager
     *
     * @return      double Gesamtwert
     */
    public double getGesamtwert(){
        double total = 0;
        for (int i = 0; i < key; i++){
            total += getArtikel(i).getGesamt();
        }
        return total;
    }

    /**
     * Methode die Das Lager nach einem bestimmten Kriterium sortiert
     *
     * @param biPredicate       Predicate das Angibt wie das Lager sortiert wird
     * @return      Artikel [] sortiertes Lager
     */
    public Artikel[] getSorted(BiPredicate<Artikel, Artikel> biPredicate){
        Artikel [] liste = new Artikel[key];
        int i  = 0;
        for (Map.Entry<Integer, Artikel> integerArtikelEntry : lager.entrySet()) {
            liste[i] = integerArtikelEntry.getValue();
            i++;
        }

        //BubbleSort
        boolean sorted = false;
        Artikel temp;
        while (!sorted){
            sorted = true;
            for (i = 0; i < liste.length - 1; i++){
                if(biPredicate.test(liste[i], liste[i+1])){
                    temp = liste[i];
                    liste[i] = liste[i+1];
                    liste[i+1] = temp;
                    sorted = false;
                }
            }
        }
        return liste;
    }

    /**
     * Methode die das Lager nach einem bestimmten Kriterium filtert
     *
     * @param predicate     Filterkriterium
     * @return      Artikel [] liste
     *
     * Artikel [] filteredList nur als tempArray
     */
    public Artikel [] filter(Predicate<Artikel> predicate) {
        Artikel[] filteredList = new Artikel[key];
        Artikel [] liste = new Artikel[key];
        int i  = 0;
        for (Map.Entry<Integer, Artikel> integerArtikelEntry : lager.entrySet()) {
            liste[i] = integerArtikelEntry.getValue();
            i++;
        }
        int p = 0;
        for (i = 0; i < key; i++) {
            if (predicate.test(liste[i])) {
            filteredList[p] = liste [i];
            p++;
            }
        }
        liste = filteredList;
        return liste;
    }

    /**
     * Methode um eine gegebene Funktion an allen Artikeln auszufuehren
     * @param func      Function die ausgefuehrt wird
     */
    public void applyToArticles(Function<Artikel, Artikel> func){
        for (Map.Entry<Integer, Artikel> integerArtikelEntry : lager.entrySet()) {
            func.apply(integerArtikelEntry.getValue());
        }
    }

    /**
     * Methode die den Preis aller Artikel aendert
     * @param prozent   Prozentsatz der verarbeitet wird
     *                      Darf nicht kleiner als -100 sein
     * @return      Artikel [] mit den geaenderten Werten
     */
    public Artikel [] aenderePreisAllerArtikelmitReturn(double prozent){
        Validator.check(prozent < -100, MSG_PREIS);
        for (int i = 0; i < key; i++){
            Artikel artikel = getArtikel(i);
            double preis  = artikel.getPreis();
            preis += preis * prozent / 100;
            artikel.setPreis(preis);
        }
        return (Artikel[]) lager.values().toArray();
    }

    /**
     * Methode die eine gegebene Funktion an bestimmten Artikeln ausfuehrt
     * @param predicate     Filterkriterium
     * @param func          Funktion
     */
    public void applyToSomeArticles(Predicate<Artikel> predicate, Function<Artikel, Artikel> func){
        for (int i = 0; i < key; i++) {
            if (predicate.test(getArtikel(i))){
                func.apply(getArtikel(i));
            }
        }
    }

    /**
     * Methode die das Lager erst filtert und danach sortiert
     * @param predicate         Filterkriterium
     * @param biPredicate       Sortiertkriterium
     * @return      Artikel [] das gefiltert und sortiert ist
     */
    public Artikel [] getArticles (Predicate<Artikel> predicate, BiPredicate<Artikel, Artikel> biPredicate){
       Artikel [] sortierteListe = new Artikel[getArtikelAnzahl()];
       int p = 0;
        for (int i = 0; i < key; i++) {
            if (predicate.test(getArtikel(i))) {
                sortierteListe[p] = getArtikel(i);
                p++;
            }
        }

        if(p > 1) {
            sortierteListe = sortieren(biPredicate, sortierteListe, p);
        }
        return sortierteListe;
    }

    /**
     * Hilfsmethode zum Sortierten eines gegebenen Arrays mittels Sortiertkriterium
     * @param biPredicate       Sortierkriterium
     * @param sortedLager       Das Array das sortiert wird
     * @param p                 Hilfsint
     * @return      Das sortierte Array
     */
    public Artikel[] sortieren(BiPredicate<Artikel, Artikel> biPredicate, Artikel[] sortedLager, int p){

        boolean sorted = false;
        Artikel temp;
        while (!sorted){
            sorted = true;
            for (int i = 0; i < p - 1; i++){
                if(biPredicate.test(sortedLager[i], sortedLager[i+1])){
                    temp = sortedLager[i];
                    sortedLager[i] = sortedLager[i+1];
                    sortedLager[i+1] = temp;
                    sorted = false;
                }
            }
        }
        return sortedLager;
    }

    /**
     * Methode zum Ausfuehren mehrerer Filtermethoden
     * @param predicates    Array mit Kriterien
     * @return      Das sortierte Array
     */
    public Artikel[] filterAll (Predicate<Artikel> ... predicates){
        Artikel [] temp = new Artikel[key];
        for (Predicate<Artikel> predicate : predicates) {
            temp = filter(predicate);
        }
        return temp;
    }
}
