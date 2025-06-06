package com.oaks.chameleon_chat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Objects;

public class KCommands {

    public static boolean command(String command, VerticalLayout target) {
        if(Objects.nonNull(command) && !command.isBlank()) {
            if(command.equals("khidden")){
                target.setVisible(false);
                return true;
            }
            if(command.equals("keject")){
                UI.getCurrent().navigate(LandingPage.class);
                return true;
            }
        }
        return false;
    }
}
