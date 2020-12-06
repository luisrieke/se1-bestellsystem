package datamodel;

import java.util.*;

public class Order {

    // Attribute:
    protected final long id;
    protected Date date;
    protected Customer customer;
    protected List<OrderItem> items = new ArrayList<OrderItem>();

    // Konstruktor:
    protected Order(long id, Date date, Customer customer){
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    // Methoden:
    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Iterable<OrderItem> getItems() {
        return items;
    }

    public Order addItem(OrderItem item){
        this.items.add(item);
        return this;
    }

    public Order removeItem(OrderItem item){
        this.items.remove(item);
        return this;
    }

    public Order clearItems(){
        this.items.clear();
        return this;
    }

    public int count() {
        int count = 0;
        count++;
        return count;
    }

}
