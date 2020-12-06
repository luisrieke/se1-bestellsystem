package datamodel;

public class Customer {

    // Attribute:
    protected final String id;
    protected String firstName;
    protected String lastName;
    protected String contact;

    // Konstruktor:
    protected Customer(String id, String name, String contact) {
        this.id = id;
        this.setLastName(name);
        this.setFirstName("");
        this.contact = contact;
    }

    // Methoden:
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
