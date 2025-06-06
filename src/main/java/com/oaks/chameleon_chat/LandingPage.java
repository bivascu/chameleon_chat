package com.oaks.chameleon_chat;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class LandingPage extends VerticalLayout {

    public LandingPage() {
        setSizeFull();
        setPadding(false);
        setMargin(false);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Image fullImage = new Image(
                "https://raw.githubusercontent.com/the-seven-creator/the-seven-creator.github.io/refs/heads/main/assets/images/learning_math_and_science.png",
                "Learning Math and Science"
        );
        fullImage.setSizeFull(); // Full width and height
        fullImage.getStyle().set("object-fit", "contain");

        // 🔗 Invisible link to /chat
        Anchor hiddenLink = new Anchor("/chat", "");
        hiddenLink.setTarget("_self"); // Open in same tab
        hiddenLink.getStyle()
                .set("display", "block")
                .set("height", "50px")
                .set("width", "100%")
                .set("opacity", "0")
                .set("position", "absolute")
                .set("bottom", "0px");

        // Optional: wrap everything in a container with relative positioning
        Div container = new Div(fullImage, hiddenLink);
        container.setSizeFull();
        container.getStyle().set("position", "relative");

        add(container);;
    }
}