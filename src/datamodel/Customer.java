package datamodel;

public class Customer {

	// Attribute:
	private final String id;
	private String firstName;
	private String lastName;
	private String contact;

	// Konstruktor:
	protected Customer(String id, String name, String contact) {
		this.id = id;
		this.setLastName(name);
		this.setFirstName("");
		this.setContact(contact);
	}

	// Methoden:
	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null) {
			this.firstName = "";
		} else {
		this.firstName = firstName;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null) {
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		if (contact == null) {
			this.contact = "";
		} else {
		this.contact = contact;
		}
	}
}
