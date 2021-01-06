package datamodel;

import java.util.*;

public class Order {

	// Attribute:
	private final long id;
	private Date date;
	private Customer customer;
	private List<OrderItem> items = new ArrayList<OrderItem>();

	// Konstruktor:
	protected Order(long id, Date date, Customer customer) {
		this.id = id;
		if (date == null) {
			this.date = new Date();
		} else {
			this.date = date;
		}
		this.customer = customer;
		this.items = new ArrayList<>();
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

	public Order addItem(OrderItem item) {
		
		if (item == null || items.contains(item)) {
			return this;
		}
		this.items.add(item);
		return this;
		
	}

	public Order removeItem(OrderItem item) {
		this.items.remove(item);
		return this;
	}

	public Order clearItems() {
		this.items.clear();
		return this;
	}

	public int count() {
		return items.size();
	}
}
