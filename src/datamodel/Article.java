package datamodel;

public class Article {

    // Attribute:
    private String id;
    private String description;
    private long unitPrice;
    private int unitsInStore;

    // Konstruktor:
    protected Article(String id, String descr, long price, int units) {
    	this.id = id;
        this.setDescription(description);
        this.setUnitPrice(price);
        this.setUnitsInStore(units);
    }

    // Methoden:
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
    	
    	if(description == null) {
            this.description = "";
        } else {
            this.description = description;
        }
    	
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
    	
        if(unitPrice < 0) {
            this.unitPrice = 0;
        } else if (unitPrice == Long.MAX_VALUE) {
            this.unitPrice = 0;
        } else {
            this.unitPrice = unitPrice;
        }
        
    }

    public int getUnitsInStore() {
        return unitsInStore;
    }

    public void setUnitsInStore(int unitsInStore) {
    	
    	if(unitsInStore < 0 || unitsInStore == Integer.MAX_VALUE) {
            this.unitsInStore = 0;
        } else {
            this.unitsInStore = unitsInStore;
        }
    	
    }
}
