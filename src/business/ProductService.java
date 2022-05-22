package business;
import domain.Product;
import repository.ProductFileRepo;
import java.util.Collection;


public class ProductService {

    private final ProductFileRepo productRepo;


    /**ProductService - constructorul clasei de service pentru produse
     * @param productRepository repo-ul pentru produse
     */
    public ProductService(ProductFileRepo productRepository) {
        this.productRepo =productRepository;
    }

    /**Metoda ce returneaza toate produsele
     * @return o colectie de produse
     */
    public Collection<Product> getAllProducts() {
        return productRepo.getAll();
    }
}
