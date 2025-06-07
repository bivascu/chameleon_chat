package com.oaks.server;

import com.oaks.enums.KCommand;
import com.oaks.enums.MessageColors;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.server.VaadinSession;

import java.util.Arrays;
import java.util.Optional;

public class MessageUtils {

    public static  Div createDivForMessage(OneMessage message) {
        Div bubble = new Div();

        boolean isGarbleActive = (boolean)VaadinSession.getCurrent().getAttribute(KCommand.KGARBLE.name());
        if(isGarbleActive){
            bubble.setText("Message: " + CaesarCipher.caesarEncrypt(message.getMessage(), 5));
        }else {
            bubble.setText("Message: " + message.getMessage());
        }
        bubble.getStyle()
                //.set("background-color", ((MessageColors)VaadinSession.getCurrent().getAttribute(MessageColors.class.getName())).hexCode)
                .set("background-color", message.getHexCode().hexCode)
                .set("color", "white")
                .set("border-radius", "20px")
                .set("padding", "10px 15px")
                .set("margin-bottom", "10px")
                .set("max-width", "300px")
                .set("white-space", "pre-line") // allow line breaks
                .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)")
                .set("align-self", "flex-end"); // optional: align right
        return bubble;
    }

    public static MessageColors getNextUnassignedMessageColor(){
        Optional<MessageColors> newColor = Arrays.stream(MessageColors.values()).filter(it -> it.assigned == false).findFirst();
        if(newColor.isEmpty()){
            return MessageColors.IOS_BLUE; // if all assigned, simply start over
        }
        newColor.get().assigned = true;
        return newColor.get();
    }
}
