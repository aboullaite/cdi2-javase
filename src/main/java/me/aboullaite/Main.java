package me.aboullaite;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Main {
    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        /** disable discovery and register classes manually */
        try (SeContainer container = initializer.disableDiscovery().addPackages(Main.class).initialize()) {
            container.select(Server.class);
        }
    }
}
