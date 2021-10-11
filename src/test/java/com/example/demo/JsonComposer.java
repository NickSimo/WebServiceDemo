package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonComposer {
    public static String getInputJson(Object json) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonRequest = gson.toJson(json);
        return jsonRequest;
    }
}
