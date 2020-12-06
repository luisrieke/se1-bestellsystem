package system;

import datamodel.Article;
import java.util.Optional;

final class InventoryManager implements Components.InventoryManager {

    // Konstruktor in den Komponenten‐Klasse InventoryManager:
    public InventoryManager() {
    }

    @Override
    public boolean containsArticle(String id) {
        return false;
    }

    @Override
    public Iterable<Article> getInventory() {
        return null;
    }

    @Override
    public Optional<Article> get(String id) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Components.InventoryManager add(Article article) {
        return null;
    }

    @Override
    public Components.InventoryManager remove(Article article) {
        return null;
    }

    @Override
    public void clear() {

    }
}
