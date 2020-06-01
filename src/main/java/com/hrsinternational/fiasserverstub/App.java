package com.hrsinternational.fiasserverstub;


import org.apache.logging.log4j.LogManager;

public class App {

    public static void main(String[] args) {

        StubMaps stubMaps = new StubMaps();
        stubMaps.load();

        int port = 5001;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LogManager.getLogger().warn("Wrong port specified. Server will be running on default port " + port);
            }
        }

        Server server = new Server(port, stubMaps);
        server.start();

    }
}
