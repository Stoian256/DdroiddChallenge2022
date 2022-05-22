package repository;


import domain.Product;
import exceptions.RepoException;
import java.util.*;

public class CartInMemoryRepo {
    Map<Product, Integer> productsInCart;

    /**
     * Contructor repository pentru produsele din cos
     */
    public CartInMemoryRepo() {
        this.productsInCart = new HashMap<>();
    }

    /**
     * @param product
     * @param quantity
     */
    public void add(Product product,Integer quantity){
        if(!productsInCart.containsKey(product))
            productsInCart.put(product,quantity);
        else
            productsInCart.put(product, productsInCart.get(product)+quantity);
    }

    /**
     * Metoda ce sterge un utilizator din memorie si fisier
     * @param product id-ul utilizatorului de sters
     */
    public void remove(Product product){
        productsInCart.remove(product);
    }

    public Map<Product,Integer> getAll() {
        return productsInCart;
    }

}
