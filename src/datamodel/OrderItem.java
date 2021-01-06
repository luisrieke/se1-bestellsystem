package datamodel;

import java.util.List;

public class OrderItem {

	// Attribute:
	private String description;
	private final Article article;
	private int unitsOrdered;

	// Konstruktor:
	protected OrderItem(String descr, Article article, int units) {
		this.setDescription(description);
        this.article = article;
        this.setUnitsOrdered(units);
	}

	// Methoden:
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {

		if (description == null) {
			this.description = "";
		} else {
			this.description = description;
		}

	}

	public Article getArticle() {
		return article;
	}

	public int getUnitsOrdered() {
		return unitsOrdered;
	}

	public void setUnitsOrdered(int unitsOrdered) {

		if (unitsOrdered < 0) {
			this.unitsOrdered = 0;
		} else {
			this.unitsOrdered = unitsOrdered;
		}

	}
}
