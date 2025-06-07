package com.oaks.server;

import com.oaks.enums.MessageColors;

import java.time.Instant;
import java.util.Objects;

public class OneMessage {
    private Long Id;
    private String ipSender;
    private String message;
    private Instant timeReceived;
    private MessageColors messageColors;

    public OneMessage(String ipSender, String message, MessageColors hexCode) {
        this.Id = MessageIdGenerator.nextId();
        this.ipSender = ipSender.strip();
        this.message = message.strip();
        this.timeReceived = Instant.now();
        this.messageColors = hexCode;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OneMessage that)) return false;
        return Objects.equals(Id, that.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id);
    }

    public String getIpSender() {
        return ipSender;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimeReceived() {
        return timeReceived;
    }

    public Long getId() {
        return Id;
    }

    public MessageColors getHexCode() {
        return messageColors;
    }
}
