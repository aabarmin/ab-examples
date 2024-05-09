package dev.abarmin.junit.basics;

import java.util.Random;

public class ExternalCondition {
    static public boolean anotherCondition() {
        return new Random().nextBoolean();
    }
}
