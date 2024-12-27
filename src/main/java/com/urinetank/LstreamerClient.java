package com.urinetank;

import java.sql.Connection;
import java.sql.DriverManager;

import com.lightstreamer.client.ClientListener;
import com.lightstreamer.client.LightstreamerClient;
import com.lightstreamer.client.Subscription;
import com.lightstreamer.client.SubscriptionListener;

public class LstreamerClient {
    private final static String[] item = { "NODE3000005" };
    private final static String[] fields = { "Value", "TimeStamp" };
    
    void start() {
        try (Connection connection = DriverManager.getConnection(UrinaDAO.URL)) {
            UrinaDAO urinaDAO = new UrinaDAO(connection);
            urinaDAO.createDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        LightstreamerClient client = new LightstreamerClient("https://push.lightstreamer.com", "ISSLIVE");
        ClientListener clientListener = new SystemOutClientListener();
        client.addListener(clientListener);

        Subscription sub = new Subscription("MERGE", item, fields);
        sub.setRequestedSnapshot("yes");
        sub.setDataAdapter("DEFAULT");

        SubscriptionListener subListener = new SystemOutSubscriptionListener();
        sub.addListener(subListener);
        client.subscribe(sub);
        client.connect();
    }

}
