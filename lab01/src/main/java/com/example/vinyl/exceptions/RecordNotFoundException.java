package com.example.vinyl.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(Integer id) {
      super("Could not find record " + id);
    }
  }