package uc.seng301.cardbattler.asg4.game;

import java.util.Random;

public class RandomSingleton {

    /**
     * only directly access for testing.
     * */
    static public Random random;

    static public Random getRandom() {
        if (random == null) {
            random = new Random();
        }
        return random;
    }

    private RandomSingleton() {

    }
}
