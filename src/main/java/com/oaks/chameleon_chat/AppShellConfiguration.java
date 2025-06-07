package com.oaks.chameleon_chat;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;

@Push // Enable server push globally
@PWA(name = "Chameleon Chat", shortName = "Chat")
//@Theme("my-theme") // Optional
public class AppShellConfiguration implements AppShellConfigurator {
}
