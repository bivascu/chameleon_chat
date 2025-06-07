package com.oaks.server;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;

public class ServerUtils {

    public static String resolveIp(VaadinRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = VaadinService.getCurrentRequest().getRemoteAddr();
        }
        return ip;
    }
}
