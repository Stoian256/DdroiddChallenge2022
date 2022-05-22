package business;
import domain.Product;
import domain.SpecialOffer;
import exceptions.RepoException;
import repository.CartInMemoryRepo;
import repository.OffersFileRepo;
import repository.ProductFileRepo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CartService {
    private final ProductFileRepo productRepo;
    private final CartInMemoryRepo cartRepo;
    private final OffersFileRepo offersRepo;


    /**CartService - constructorul clasei de service pentru produsele din cos
     * @param productRepository repo-ul produselor
     * @param cartRepo repo-ul produselor din cos
     * @param offersRepo repo-ul ofertelor
     */
    public CartService(ProductFileRepo productRepository, CartInMemoryRepo cartRepo, OffersFileRepo offersRepo) {
        this.productRepo = productRepository;
        this.offersRepo = offersRepo;
        this.cartRepo = cartRepo;
    }


    /**
     * Metoda ce returneaza produsle din cos si cantitate alor
     * @return produsle din cos
     */
    public Map<Product, Integer> getProductsInCart() {
        return cartRepo.getAll();
    }

    /**Metoda ce adauga un produs in cos
     * @param productID id-ul produsului de adaugat in cos
     * @param quantity cantitatea de adaugat in cos
     * @throws RepoException daca nu exista in produse unul cu id-ul dat
     */
    public void addProductToCart(Integer productID, Integer quantity) throws RepoException {
        cartRepo.add(productRepo.getOne(productID), quantity);
    }

    /**Metoda ce elimina un produs din cos
     * @param productID id-ul produsului de eliminat
     * @throws RepoException cu mesajul Produs inexistenta! daca nu exista un produs cu id-ul dat
     */
    public void removeProductFromCart(Integer productID) throws RepoException {
        cartRepo.remove(productRepo.getOne(productID));
    }

    /**Metoda ce calculeaza factura pentru produsle din cos
     * @param discount parametru boolean pe baza caruia se aplica sau nu discount-ul
     * @param VAT parametru boolean pe baza caruia se aplica sau nu VAT-ul
     * @return o list cu subtotal0ul,shipping-ul si total-ul de plata
     */
    public List<Double> computeInvoice(boolean discount,boolean VAT) {
        Map<Product, Integer> cart = cartRepo.getAll();
        double subtotal = 0;
        double shipping = 0;
        for (Product product : cart.keySet()) {
            subtotal += product.getPrice() * cart.get(product);
            shipping += cart.get(product) * product.getWeight() / 0.1 * product.getShippingRate().getRate();
        }
        if(VAT)subtotal+=subtotal*0.19;
        if(discount)
            return computeDiscount(subtotal,shipping);
        double total = shipping + subtotal;
        return Arrays.asList(subtotal, shipping, total);
    }

    /**Metoda ce aplica discount-ul pe factura
     * @param subtotal subtotaul de plata
     * @param shipping shipping-ul de plata
     * @return o list cu subtotal0ul,shipping-ul si total-ul de plata dupa aplicarea discount-ului
     */
    public List<Double> computeDiscount(Double subtotal, Double shipping) {
        List<SpecialOffer> offers = offersRepo.getAll().stream().toList();
        Map<Product, Integer> cart = cartRepo.getAll();
        for (Product product : cart.keySet()) {
            for (SpecialOffer specialOffer : offers) {
                if (!specialOffer.getRequirementName().equals("Any")) {
                    Product requirementProduct = productRepo.getByName(specialOffer.getRequirementName());
                    if (cart.containsKey(requirementProduct) && cart.get(requirementProduct) > specialOffer.getRequirementCount() && product.getName().equals(specialOffer.getAppliedOn())) {
                        subtotal -= product.getPrice() * cart.get(product) * specialOffer.getDiscount();
                    }
                }

            }
        }

        SpecialOffer specialOffer = offers.stream().filter(specialOfferFilter -> specialOfferFilter.getRequirementName().equals("Any")).toList().get(0);
        if (specialOffer != null) {
            if (cart.values().size() >= 2 || cart.get(cart.keySet().toArray()[0]) >= 2)
                if (shipping <= specialOffer.getDiscount()) shipping = 0.0;
                else shipping -= specialOffer.getDiscount();
        }

        double total = shipping + subtotal;
        return Arrays.asList(subtotal, shipping, total);

    }
}
