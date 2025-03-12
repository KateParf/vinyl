package com.example.vinyl.exceptions;

public class CoverNotFoundException extends RuntimeException {

    public CoverNotFoundException(Integer id) {
      super("Could not find cover " + id);
    }
  }