package com.urinetank;


import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class App {
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/urine", new UrineHandler());
        httpServer.start();

        LstreamerClient handler = new LstreamerClient();
        handler.start();
    }
}


