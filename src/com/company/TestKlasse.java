package com.company;

import java.util.*;


public class TestKlasse {


    //Klassenkonstanten
    private static final int ANLEGEN = 1;
    private static final int QUERSUMME = 2;
    private static final int ANZAHL = 3;
    private static final int ERGEBNIS = 4;
    private static final int SORTIERT = 5;
    private static final int SORTIERT2 = 6;
    private static final int STAMPS = 7;
    private static final int ENDE = 0;
    private final Scanner input = new Scanner(System.in);
    private Collection<Integer> collection;
    private Consumer consumer;
    private int index;
    private static int queue = 2;

    /**
     * Hauptschleife des Testprogramms
     * Erst Testen ob bereits ein Lager angelegt ist, fall nicht
     * -> Lager anlegen Dialog
     * <p>
     * Abfangen der Exceptions
     */
    public void start() {
        int funktion = -1;
        consumer = new Consumer();
        collection = new ArrayList<>();
        while (funktion != ENDE) {
            try {
                funktion = einlesenFunktion();
                ausfuehrenFunktion(funktion);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            } catch (InputMismatchException e) {
                System.out.println("Falsche Eingabe.");
                System.out.println("Bitte erneut eingeben!");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Ausnahme gefangen: " + e);
            }
        }

    }

    /**
     * Main-Methode zum Erzeugen des Dialogobjektes und zum Starten des Dialogs
     *
     * @param args Argumente der Main Methode
     */
    public static void main(String[] args) {
        if (args.length > 0) queue = 1;
        new TestKlasse().start();
    }

    /**
     * Methode zum Ausgeben der Funktionen
     */
    private int einlesenFunktion() {
        System.out.print("+++Hauptmenü+++\n"
                + ANLEGEN + ": Zahl hinzufügen\n"
                + QUERSUMME + ": Quersumme berechnen\n"
                + ANZAHL + ": Anzahl der verschiedenen Ergebnisse anzeigen\n"
                + ERGEBNIS + ": Prüfen wie oft das Ergebnis rauskam\n"
                + SORTIERT + ": Ergebnisse aufsteigend sortieren\n"
                + SORTIERT2 + ": Ergebnisse absteigend sortieren\n"
                + STAMPS + ": Timestamp anzeigen\n"
                + ENDE + ": Beenden ->");
        return input.nextInt();
    }

    /**
     * Methode zum Ausfuehren des Dialogfeldes
     * Cases fuer die verschiedenen Funktionen
     *
     * @param funktion Funktion die ausgefuehrt werden soll
     */
    private void ausfuehrenFunktion(int funktion) {
        switch (funktion) {
            case ANLEGEN:
                System.out.println("Bitte geben Sie die Zahl ein:");
                int i = input.nextInt();
                input.nextLine();
                collection.add(i);
                break;
            case QUERSUMME:
                switch (queue) {
                    case 2:
                        ListIterator<Integer> iter = ((AbstractList<Integer>) collection).listIterator(index);
                        if (iter.hasNext()) {
                            int toConsume = iter.next();
                            consumer.consume(toConsume);
                            System.out.println("Die Quersumme von " + toConsume + " ist: " + consumer.getQuersumme());
                            index++;
                        } else {
                            System.out.println("Es gibt keine zu prüfende Zahlen!");
                        }
                        break;
                    case 1:
                        List<Integer> list = new ArrayList<>(collection);
                        Collections.sort(list);
                        ListIterator<Integer> iterx = list.listIterator(index);
                        if (iterx.hasNext()) {
                            int toConsume = iterx.next();
                            consumer.consume(toConsume);
                            System.out.println("Die Quersumme von " + toConsume + " ist: " + consumer.getQuersumme());
                            index++;
                        } else {
                            System.out.println("Es gibt keine zu prüfende Zahlen!");
                        }
                        break;
                    case ANZAHL:
                        System.out.println(consumer.numberOfDifferentResults());
                        break;
                    case ERGEBNIS:
                        System.out.println("Bitte geben Sie die Zahl ein die Sie suchen möchten:");
                        int ergebnis = input.nextInt();
                        input.nextLine();
                        System.out.println(consumer.numberOfOccurences(ergebnis));
                        break;
                    case SORTIERT:
                        System.out.println(consumer.getCrossTotalsAscending());
                        break;
                    case SORTIERT2:
                        System.out.println(consumer.getCrossTotalsDescending());
                        break;
                    case STAMPS:
                        System.out.println("Geben Sie die Zahl ein die Sie suchen möchten:");
                        int time = input.nextInt();
                        input.nextLine();
                        System.out.println(consumer.getTimestampsForResult(time));
                        break;
                    case ENDE:
                        System.out.println("Programmende.\n");
                        break;
                    default:
                        System.out.println("Funktion nicht vorhanden!");
                        break;
                }
        }
    }
}
