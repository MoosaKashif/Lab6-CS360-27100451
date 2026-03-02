package com.example.listycity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a class that defines a City.
 * @author YourName
 * @version 1.0
 */
public class City implements Comparable, Serializable {

    /** The name of the city */
    private String city;

    /** The name of the province */
    private String province;

    /**
     * Constructor for City
     * @param city The name of the city
     * @param province The name of the province
     */
    public City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    /**
     * Returns the name of the city
     * @return The city name as a String
     */
    public String getName() {
        return this.city;
    }

    /**
     * Returns the name of the city (alias for getName)
     * @return The city name as a String
     */
    public String getCityName() {
        return this.city;
    }

    /**
     * Returns the name of the province
     * @return The province name as a String
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * Returns the name of the province (alias for getProvince)
     * @return The province name as a String
     */
    public String getProvinceName() {
        return this.province;
    }

    /**
     * Sets the name of the city
     * @param name The new city name
     */
    public void setName(String name) {
        this.city = name;
    }

    /**
     * Sets the name of the province
     * @param province The new province name
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Converts this City object to a Map for Firestore storage.
     * @return A Map containing city name and province name
     */
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", this.city);
        map.put("province", this.province);
        return map;
    }

    /**
     * Compares this city to another object by city name lexicographically.
     * @param o The object to compare to
     * @return A negative integer, zero, or positive integer as this city name
     *         is less than, equal to, or greater than the specified city name
     */
    @Override
    public int compareTo(Object o) {
        City other = (City) o;
        return this.city.compareTo(other.getCityName());
    }

    /**
     * Checks equality based on city name and province name.
     * @param o The object to compare to
     * @return true if both city name and province name are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City other = (City) o;
        return this.city.equals(other.getCityName()) &&
                this.province.equals(other.getProvinceName());
    }

    /**
     * Returns a hash code based on city name and province name.
     * @return An integer hash code
     */
    @Override
    public int hashCode() {
        return city.hashCode() + province.hashCode();
    }
}