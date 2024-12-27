package com.urinetank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import com.lightstreamer.client.ItemUpdate;
import com.lightstreamer.client.Subscription;
import com.lightstreamer.client.SubscriptionListener;

public class SystemOutSubscriptionListener implements SubscriptionListener {

  @Override
  public void onClearSnapshot(String itemName, int itemPos) {
    System.out.println("Server has cleared the current status of the chat");
  }

  @Override
  public void onCommandSecondLevelItemLostUpdates(int lostUpdates, String key) {
  }

  @Override
  public void onCommandSecondLevelSubscriptionError(int code, String message, String key) {
  }

  @Override
  public void onEndOfSnapshot(String arg0, int arg1) {
    System.out.println("Snapshot is now fully received, from now on only real-time messages will be received");
  }

  @Override
  public void onItemLostUpdates(String itemName, int itemPos, int lostUpdates) {
    System.out.println(lostUpdates + " messages were lost");
  }

  @Override
  public void onItemUpdate(ItemUpdate update) {
    Iterator<Entry<String, String>> changedValues = update.getChangedFields().entrySet().iterator();
    String percent = changedValues.next().getValue();
    System.out.println("The urine tank is " + percent + "% full. "
        + "Time: " + new Date());
    try {
      Connection connection = DriverManager.getConnection(UrinaDAO.URL);
      UrinaDAO urinaDAO = new UrinaDAO(connection);
      urinaDAO.saveUrineData(percent);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
  public void onSubscription() {
  }

  @Override
  public void onSubscriptionError(int code, String message) {
    System.out.println("Cannot subscribe because of error " + code + ": " + message);
  }

  @Override
  public void onUnsubscription() {
    System.out.println("Now unsubscribed from chat item, no more messages will be received");
  }

  @Override
  public void onRealMaxFrequency(String frequency) {
  }

  @Override
  public void onListenEnd(Subscription arg0) {
    throw new UnsupportedOperationException("Unimplemented method 'onListenEnd'");
  }

  @Override
  public void onListenStart(Subscription arg0) {
    throw new UnsupportedOperationException("Unimplemented method 'onListenStart'");
  }

}