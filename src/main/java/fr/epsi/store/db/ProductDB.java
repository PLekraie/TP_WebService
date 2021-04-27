package fr.epsi.store.db;

import fr.epsi.store.model.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductDB {
    private List<Product> products;

    public ProductDB() {
        this.products = new ArrayList<>();
        this.products.add(new Product(1, "Tasse à café", "Tasse à café blanche simple", 399, 5, "Elle est blanche", "https://images.unsplash.com/photo-1595434091143-b375ced5fe5c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80"));
        this.products.add(new Product(2, "Verre à pied", "verre à pied, aussi appelé verre à vin", 25, 155, "On peut boire dedans", "https://images.unsplash.com/photo-1514651029128-173d2e6ea851?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=632&q=80"));
        this.products.add(new Product(3, "Rose rouge", "Une rose rouge préservée pour toutes occasions", 10, 500, "Ne sent rien", "https://images.unsplash.com/photo-1595212592138-369161d1ac88?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=675&q=80"));
        this.products.add(new Product(4, "Verre à bière", "Pour toutes vos envies de rafraichissement", 50, 89, "Vendu par lot de 5, parce que les bières c'est mieux à plusieurs #Covid", "https://images.unsplash.com/photo-1553880414-5fe13d83ddb6?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"));
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
    }

    public List<Product> getProductsBySearch(String search) {
        return products.stream().filter(product -> product.getName().toLowerCase().startsWith(search.toLowerCase())).collect(Collectors.toList());
    }

    public Product createProduct(Product product) {
        try {
            //simule auto-increment
            products.sort(Comparator.comparingInt(Product::getId));
            int id = products.get(products.size() - 1).getId() + 1;
            product.setId(id);

            products.add(product);
        } catch (Exception e) {
            return null;
        }
        return product;
    }

    public List<Product> createProducts(List<Product> productsToAdd) {
        try {
            //prévoir de pouvoir rollback avec une database
            products.sort(Comparator.comparingInt(Product::getId));
            for (Product product : productsToAdd) {
                int id = products.get(products.size() - 1).getId() + 1;
                product.setId(id);

                this.products.add(product);
            }
        } catch (Exception e) {
            return null;
        }
        return productsToAdd;
    }

    public Product deleteProduct(int id) {
        Product productToDelete = products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
        if (productToDelete != null)
            products.remove(productToDelete);
        return productToDelete;
    }

    public String buyProduct(int id, int quantity) {
        Product productToBuy = products.stream().filter(product -> product.getId() == id).findFirst().orElse(null);
        String message = "Product not found";
        if (productToBuy != null) {
            if (productToBuy.getQuantity() < quantity) {
                message = "Not enough products is stock";
            } else {
                productToBuy.setQuantity(productToBuy.getQuantity() - quantity);
                message = quantity + "*" + productToBuy.getName() + " bought, stock : " + productToBuy.getQuantity();
            }
        }
        return message;
    }

    public Product updateProduct(int id, Product product) {
        Product productToUpdate = products.stream().filter(prd -> prd.getId() == id).findFirst().orElse(null);
        if (productToUpdate != null) {
            productToUpdate.setName(product.getName());
            productToUpdate.setDetail(product.getDetail());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.setQuantity(product.getQuantity());
            productToUpdate.setInfo(product.getInfo());
            productToUpdate.setImage(product.getImage());
        }
        return productToUpdate;
    }
}
