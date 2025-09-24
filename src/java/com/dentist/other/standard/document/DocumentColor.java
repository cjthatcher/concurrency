package com.dentist.other.standard.document;

import java.util.Random;

public enum DocumentColor {
    RED, GREEN, BLUE;

    public static DocumentColor random() {
        int pick = new Random().nextInt(2);
        switch (pick) {
            case 0: return RED;
            case 1: return GREEN;
            case 2: return BLUE;
            default: return RED;
        }
    }
}