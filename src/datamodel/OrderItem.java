package datamodel;

import java.util.List;

public class OrderItem {

    // Attribute:
    protected String description;
    protected final Article article;
    protected int unitsOrdered;

    // Konstruktor:
    protected OrderItem(String descr, Article article, int units){
        this.description=descr;
        this.article=article;
        this.unitsOrdered=units;
    }

    // Methoden:
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Article getArticle() {
        return article;
    }

    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    public void setUnitsOrdered(int unitsOrdered) {
        this.unitsOrdered = unitsOrdered;
    }
}
