package com.example.grace.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by thomas on 3/09/2017.
 */
public class DBAccessTest {
    @Test
    public void connect() throws Exception {
        DBAccess instance = new DBAccess();

        instance.connect();
    }

}