package com.oaks.server;

import java.util.concurrent.atomic.AtomicLong;

public class MessageIdGenerator {
    private static final AtomicLong counter = new AtomicLong(1);

    public static long nextId() {
        return counter.getAndIncrement();
    }
}
