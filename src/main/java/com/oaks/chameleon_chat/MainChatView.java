package com.oaks.chameleon_chat;

import com.oaks.enums.KCommand;
import com.oaks.enums.MessageColors;
import com.oaks.server.*;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.PreDestroy;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Route("chat")
public class MainChatView extends VerticalLayout implements Broadcaster.BroadcastListener {

    HorizontalLayout inputLayout = null;
    VerticalLayout chatContainer = null;
    private final UI ui;
    private final String instanceIpAddress;
    private Long maxIdReceivedMessage = 0L;

    private boolean kgarbleActive = false;

    public MainChatView() {
        this.ui = UI.getCurrent();
        instanceIpAddress = ServerUtils.resolveIp(VaadinService.getCurrentRequest());
        System.out.println("instanceIpAddress: " + instanceIpAddress);
        // Register this view for broadcasts
        Broadcaster.register(this);

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
            handleMessageInput(messageInput);
        });

        messageInput.addKeyPressListener(Key.ENTER, keyPressEvent -> {
            handleMessageInput(messageInput);
        });

        add(chatContainer, inputLayout);
        try {
            loadChatHistory();
        }catch (Exception e){
            System.out.println(e.getMessage());
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().executeJs("window.location.reload();");
        }

        // populate session
        if(Objects.isNull(VaadinSession.getCurrent().getAttribute(KCommand.KGARBLE.name()))){
            VaadinSession.getCurrent().setAttribute(KCommand.KGARBLE.name(), false);
        }
        if(Objects.isNull(VaadinSession.getCurrent().getAttribute(MessageColors.class.getName()))){
            VaadinSession.getCurrent().setAttribute(MessageColors.class.getName(),MessageUtils.getNextUnassignedMessageColor());
        }
    }


    private void handleMessageInput(TextField messageInput) {
        String msg = messageInput.getValue().trim();

        if(isKCommand(msg)) {
            KCommand command = KCommand.valueOf(msg.toUpperCase());
            switch(command){
                case KEJECT -> processKeject();
                case KHIDE -> processKHide();
                case KGARBLE -> processKGarble();
            }
        }else{
            OneMessage localMessage = new OneMessage(instanceIpAddress, msg, (MessageColors) VaadinSession.getCurrent().getAttribute(MessageColors.class.getName()));
            ChatServer.postMessage(localMessage);
            Broadcaster.broadcast(localMessage);
        }
        messageInput.clear();
    }

    @Override
    public void receiveBroadcast(OneMessage message) {
        if (ui != null && ui.isAttached()) {
            ui.access(() -> {
                if (!message.getMessage().isEmpty()
                        //&& !isKCommand
                ) {
                Div bubble = MessageUtils.createDivForMessage(message);
                HorizontalLayout wrapper = new HorizontalLayout(bubble);
                wrapper.setWidthFull();
                    boolean isMyOwnMessage = instanceIpAddress.equals(message.getIpSender());
                wrapper.setJustifyContentMode(
                        isMyOwnMessage ? JustifyContentMode.END : JustifyContentMode.START
                );
                chatContainer.add(wrapper);
                chatContainer.getElement().executeJs(
                        "const el = this; el.scrollTop = el.scrollHeight;"
                );
            }
            });
            maxIdReceivedMessage = message.getId();
        } else {
            System.out.println("UI IS NULL or detached");
            Broadcaster.unregister(this);
        }
    }


    private void loadChatHistory() {
        List<OneMessage> messagesToPublish = ChatServer.getAllChatMessagesAfterId(maxIdReceivedMessage);
        messagesToPublish.forEach(message -> {
            if (!message.getMessage().isEmpty()
                   // && !isKCommand
            ) {
                Div bubble = MessageUtils.createDivForMessage(message);
                HorizontalLayout wrapper = new HorizontalLayout(bubble);
                wrapper.setWidthFull();
                boolean isMyOwnMessage = instanceIpAddress.equals(message.getIpSender());
                wrapper.setJustifyContentMode(
                        isMyOwnMessage ? JustifyContentMode.END : JustifyContentMode.START
                );
                chatContainer.add(wrapper);
            }
        });
        //populate where I left off
        if(messagesToPublish.size() > 0){
            maxIdReceivedMessage = messagesToPublish.get(messagesToPublish.size() - 1).getId();
        }
    }

    public boolean isKCommand(String messageCommand) {
        return Arrays.stream(KCommand.values()).anyMatch(entry -> entry.name().equalsIgnoreCase(messageCommand));
    }

    private void processKeject(){
        UI.getCurrent().navigate(LandingPage.class);
    }

    private void processKHide(){
        if(chatContainer.isVisible() == false){
            chatContainer.setVisible(true);
        }else {
            chatContainer.setVisible(false);
        }
    }

    private void processKGarble(){

        boolean garbleActive = (boolean) VaadinSession.getCurrent().getAttribute(KCommand.KGARBLE.name());
        garbleActive = !garbleActive;

        System.out.println("Garble is now: " + garbleActive);
        VaadinSession.getCurrent().setAttribute(KCommand.KGARBLE.name(), garbleActive);
        UI.getCurrent().getPage().reload();

        //another way
//        UI.getCurrent().navigate(""); // or another temporary route
//        UI.getCurrent().navigate(MainChatView.class);
    }


    @PreDestroy
    public void destroy() {
        Broadcaster.unregister(this);
        MessageColors storedValue = (MessageColors) VaadinSession.getCurrent().getAttribute(MessageColors.class.getName());
        if(storedValue.assigned){
            storedValue.assigned = false;
        }
    }
}

