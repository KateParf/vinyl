package com.example.vinyl.exceptions;

public class PerformerNotFoundException extends RuntimeException {

    public PerformerNotFoundException(Integer id) {
      super("Could not find performer " + id);
    }
  }