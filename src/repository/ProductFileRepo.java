package repository;
import domain.Product;
import domain.ShippingRate;
import exceptions.RepoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ProductFileRepo{
    private final String fileName;
    Map<Integer, Product> products;

    /**Constructor file repository pentru produse
     * @param fileName numele fisierului in care se afla scrise produsele
     */
    public ProductFileRepo(String fileName) {
        this.fileName = fileName;
        this.products = new HashMap<>();
        loadData();
    }

    /**
     * Metoda ce citeste produsele din fisier si le adauga in memorie
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                List<String> attributes = Arrays.asList(line.split(","));
                Product entity = extractEntity(attributes);
                try {
                    if (products.containsKey(entity.getProductID()))
                        throw new RepoException("Produs existent!\n");

                    products.put(entity.getProductID(), entity);

                } catch (RepoException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Metoda ce transofrma o list de string-uri intr-un produs
     * @param attributes lista de atribute in format string pe baza caruia se creeaza produsul
     * @return produsul contruit pe baza arugumentelor
     */
    public Product extractEntity(List<String> attributes) {
        return new Product(Integer.parseInt(attributes.get(0)),attributes.get(1), Double.parseDouble(attributes.get(2)), new ShippingRate(attributes.get(3),Double.parseDouble(attributes.get(4))), Double.parseDouble(attributes.get(5)));
    }

    /**Metoda ce returneaza un produs pe baza id-ului
     * @param id id-ul produsului cautat
     * @return produsul cu id-ul dat
     * @throws RepoException cu mesajul Produs inexistenta! daca nu exista un produs cu id-ul dat
     */
    public Product getOne(int id) throws RepoException {
        if (!products.containsKey(id))
            throw new RepoException("Produs inexistenta!\n");
        return products.get(id);
    }

    /**Metoda ce returneaza un produs pe baza numelui
     * @param name numele produsului cautat
     * @return produsul cu id-ul dat
     */
    public Product getByName(String name){
        return products.values().stream().filter(product -> product.getName().equals(name)).toList().get(0);
    }

    /**Metoda ce returneaza toate produsele
     * @return produsele
     */
    public Collection<Product> getAll() {
        return products.values();
    }
}
