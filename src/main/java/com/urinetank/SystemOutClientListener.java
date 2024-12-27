package com.urinetank;

import com.lightstreamer.client.ClientListener;
import com.lightstreamer.client.LightstreamerClient;

public class SystemOutClientListener implements ClientListener {

  @Override
  public void onPropertyChange(String property) {
  }

  @Override
  public void onServerError(int code, String message) {
    System.out.println("Server error: " + code + ": " + message);
  }

  @Override
  public void onStatusChange(String newStatus) {
    System.out.println("Retrieving the urina tank data...");
  }

  @Override
  public void onListenEnd(LightstreamerClient arg0) {
    throw new UnsupportedOperationException("Unimplemented method 'onListenEnd'");
  }

  @Override
  public void onListenStart(LightstreamerClient arg0) {
    throw new UnsupportedOperationException("Unimplemented method 'onListenStart'");
  }

}
