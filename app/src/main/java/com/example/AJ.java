package com.example;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class AJ {
    public Map<String, String> map = new HashMap<>();

    public AJ() {
        map.put("code", "vv");
        map.put("code11", "vvv-------");
    }

    public String printMap() {
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> {
            Log.i("df", "key=" + k + ",v=" + v);
            sb.append(k).append(":").append(v).append("\n");
        });
        return sb.toString();
    }
}
