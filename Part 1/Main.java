import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Horse horse1 = new Horse('A', "Horse 1", 0.4);
        Horse horse2 = new Horse('B', "Horse 2", 0.4);
        Horse horse3 = new Horse('C', "Horse 3", 0.4);
        Horse horse4 = new Horse('D', "Horse 4", 0.4);

        Race race = new Race(30, 4);
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        race.addHorse(horse4, 4);

        race.startRace();
    }
}
