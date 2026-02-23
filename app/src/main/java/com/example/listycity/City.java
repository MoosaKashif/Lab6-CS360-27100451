package com.example.listycity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class City implements Serializable {
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    public String getName() { return name; }
    public String getProvince() { return province; }
    public String getCityName() { return name; } // needed for Firestore doc ID

    public void setName(String name) { this.name = name; }
    public void setProvince(String province) { this.province = province; }

    // Firestore needs a Map to save the object
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("province", province);
        return map;
    }
}