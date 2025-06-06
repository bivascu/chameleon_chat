package com.oaks.chameleon_chat;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("chat")
public class MainChatView extends VerticalLayout {

    HorizontalLayout inputLayout = null;
    VerticalLayout chatContainer = null;

    public MainChatView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "#f5f5f5");

        // 🗨️ Chat container that will hold all messages
        chatContainer = new VerticalLayout();
        chatContainer.setWidth("400px");
        chatContainer.setPadding(false);
        chatContainer.setSpacing(false);
        chatContainer.getStyle().set("max-height", "400px").set("overflow-y", "auto");
        chatContainer.setId("chatContainer");

        // 💬 Input bar
        TextField messageInput = new TextField();
        messageInput.setPlaceholder("Type your message...");
        messageInput.setWidth("300px");
        messageInput.getStyle()
                .set("border-radius", "20px")
                //.set("background-color", "#e6e6e6");
                .set("background-color", "transparent");

//        messageInput.addValueChangeListener(event -> {
//            System.out.println("Running hide listener");
//            String value = event.getValue();
//            if (value.startsWith("k")) {
//                messageInput.getStyle()
//                        .set("color", "transparent")
//                        .set("background-color", "transparent")
//                        .set("caret-color", "black"); // keep cursor visible
//            } else {
//                messageInput.getStyle()
//                        .remove("color")
//                        .remove("background-color")
//                        .remove("caret-color");
//            }
//        });


        Button postButton = new Button("✏️ Post");
        postButton.getStyle()
                .set("border-radius", "20px")
                .set("color", "white")
                .set("background", "#007bff")
                .set("margin-left", "5px");

        inputLayout = new HorizontalLayout(messageInput, postButton);
        inputLayout.getStyle()
                .set("background-color", "#ffffff")
                .set("border-radius", "20px")
                .set("padding", "5px 10px")
                .set("box-shadow", "0 2px 4px rgba(0,0,0,0.1)");

        // 📤 Add message on click
        postButton.addClickListener(e -> {
            handleMessageInput(messageInput, chatContainer);
        });

        messageInput.addKeyPressListener(Key.ENTER, keyPressEvent -> {
            handleMessageInput(messageInput, chatContainer);
        });

        add(chatContainer, inputLayout);
    }


    private void handleMessageInput(TextField messageInput, VerticalLayout chatContainer) {
        String msg = messageInput.getValue().trim();
        boolean isKCommand = KCommands.command(msg, chatContainer);
        if (!msg.isEmpty() && !isKCommand) {
            Div bubble = new Div();
            bubble.setText("Chat Message: " + msg);
            bubble.getStyle()
                    .set("background-color", "#007aff") // iOS blue
                    .set("color", "white")
                    .set("border-radius", "20px")
                    .set("padding", "10px 15px")
                    .set("margin-bottom", "10px")
                    .set("max-width", "300px")
                    .set("white-space", "pre-line") // allow line breaks
                    .set("box-shadow", "0 2px 6px rgba(0,0,0,0.1)")
                    .set("align-self", "flex-end"); // optional: align right


            boolean isSentByUser = false;

            HorizontalLayout wrapper = new HorizontalLayout(bubble);
            wrapper.setWidthFull();
            wrapper.setJustifyContentMode(
                    isSentByUser ? JustifyContentMode.END : JustifyContentMode.START
            );


            chatContainer.add(wrapper);
        }
        messageInput.clear();
        chatContainer.getElement().executeJs(
                "const el = this; el.scrollTop = el.scrollHeight;"
        );
    }

}

