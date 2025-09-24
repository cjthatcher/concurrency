package com.dentist.other.standard.client;

/** Centralized place for simulating delay in getting results back from external services. */
public class Sleeper {

    public static void sleep() throws InterruptedException {
        // check config.
        Thread.sleep(1_000);
    }
}
