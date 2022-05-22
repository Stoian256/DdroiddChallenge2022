import UI.UI;
import business.CartService;
import business.ProductService;
import repository.CartInMemoryRepo;
import repository.OffersFileRepo;
import repository.ProductFileRepo;

public class Main {
    public static void main(String[] args) {
        ProductFileRepo productFileRepository = new ProductFileRepo("data/products.csv");
        OffersFileRepo offersFileRepo = new OffersFileRepo("data/offers.csv");
        CartInMemoryRepo cartInMemoryRepo = new CartInMemoryRepo();

        ProductService productService=new ProductService(productFileRepository);
        CartService cartService=new CartService(productFileRepository,cartInMemoryRepo,offersFileRepo);
        UI UI = new UI(productService,cartService);
        UI.run();
    }
}
