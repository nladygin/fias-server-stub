package com.hrsinternational.fiasserverstub;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.regex.PatternSyntaxException;

public class StubMaps extends LinkedList<StubRecord> {




    public void load() {
        LogManager.getLogger().info("Stub maps loading...");
        this.clear();

        try {
            File file = new File("mappings.txt");
            Properties properties = new Properties();
            properties.load(new FileReader(file));

            for (String key : properties.stringPropertyNames()) {
                String request = key;
                String response = String.valueOf(properties.get(key));
                try {
                    StubRecord map = new StubRecord(request, response);
                        this.add(map);
                    LogManager.getLogger().info(request + " => " + response + " [OK]");
                } catch (PatternSyntaxException pe) {
                    LogManager.getLogger().error(request + " => " + response + " [ERROR]");
                }
            }
        } catch (IOException e) {
            LogManager.getLogger().error("Error when stub maps loading");
        }
    }


}
