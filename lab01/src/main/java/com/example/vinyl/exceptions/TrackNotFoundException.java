package com.example.vinyl.exceptions;

public class TrackNotFoundException extends RuntimeException {

    public TrackNotFoundException(Integer id) {
      super("Could not find track " + id);
    }
  }