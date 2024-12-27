package com.urinetank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/urine")
public class Controller {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUrineData() {
        try (Connection connection = DriverManager.getConnection(UrinaDAO.URL)) {
            UrinaDAO urinaDAO = new UrinaDAO(connection);
            ResultSet resultSet = urinaDAO.get();
            try {
                if(resultSet.last()){
                    return resultSet.getString("value");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "0";
    }
}
