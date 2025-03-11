package com.example.vinyl.controllers;

public class RecordNotFoundException extends RuntimeException {

    RecordNotFoundException(Integer id) {
      super("Could not find record " + id);
    }
  }