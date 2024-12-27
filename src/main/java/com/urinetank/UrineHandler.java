package com.urinetank;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UrineHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        List<String> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(UrinaDAO.URL);
            UrinaDAO urinaDAO = new UrinaDAO(connection);
            ResultSet resultSet = urinaDAO.get();
                try {
                    while(resultSet.next()){
                        list.add(resultSet.getString("value"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        String percent = list.get(list.size()-1);
        JsonObject jsonObject = Json.createObjectBuilder().add("percent", percent).build();
        OutputStream os = exchange.getResponseBody();
        byte[] responseByte = jsonObjectToBytes(jsonObject);
        exchange.sendResponseHeaders(200, responseByte.length);
        os.write(responseByte);
        os.close();
    }

    private static byte[] jsonObjectToBytes(JsonObject jsonObject) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (JsonWriter jsonWriter = Json.createWriter(outputStream)) {
                jsonWriter.writeObject(jsonObject);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }
    }
}
