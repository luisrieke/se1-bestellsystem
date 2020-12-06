package datamodel;

public class Article {

    // Attribute:
    protected String id;
    protected String description;
    protected long unitPrice;
    protected int unitsInStore;

    // Konstruktor:
    protected Article(String id, String descr, long price, int units) {
        this.id = id;
        this.description = descr;
        this.unitPrice = price;
        this.unitsInStore = units;
    }

    // Methoden:
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStore() {
        return unitsInStore;
    }

    public void setUnitsInStore(int unitsInStore) {
        this.unitsInStore = unitsInStore;
    }
}
