package com.example.appdeezer1.utils;

public interface MyCallback {

    final static int WRONG_CODE = 0;
    final static int SUCESS_CODE = 1;

    public void notify(Object result, int statusCode);
}
