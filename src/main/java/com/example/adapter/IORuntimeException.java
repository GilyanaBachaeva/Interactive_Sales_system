package com.example.adapter;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {
    public IORuntimeException(String s, IOException e) {
    }
}
