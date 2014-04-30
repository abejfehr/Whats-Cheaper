package com.abefehr.whatscheaper;

/**
 * Created by abe on 1/2/2014.
 */
public class Item implements Comparable<Item> {

    Item(double price, double numUnits){
        this.price = price;
        this.numUnits = numUnits;
    }

    private double price;
    private double numUnits;

    public double getPrice() { return price; }

    public double getNumUnits() { return numUnits; }

    @Override
    public int compareTo(Item item) {
        return (int) (1000 * price / numUnits - 1000 * item.getPrice() / item.getNumUnits());
    }
}