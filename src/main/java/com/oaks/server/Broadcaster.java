package com.oaks.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Broadcaster {

    public interface BroadcastListener {
        void receiveBroadcast(OneMessage message);
    }

    private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<>();

    public static void register(BroadcastListener listener) {
        listeners.add(listener);
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }

    public static void broadcast(OneMessage message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }
}
