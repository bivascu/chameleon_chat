package com.oaks.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatServer {
    private static List<OneMessage> allChatMessages = new ArrayList<>();

    public static void postMessage(OneMessage message) {
        if(Objects.nonNull(message)) {
            allChatMessages.add(message);
        }
    }

    public static List<OneMessage> getAllChatMessagesAfterId(Long lastSeenId) {
        return allChatMessages.stream().filter(m -> m.getId() > lastSeenId).toList();
    }
}
