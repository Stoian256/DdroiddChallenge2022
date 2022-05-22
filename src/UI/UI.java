package UI;
import business.CartService;
import business.ProductService;
import domain.Product;
import exceptions.RepoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class UI {
    private final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    private final ProductService productService;
    private final CartService cartService;
    private static final DecimalFormat df = new DecimalFormat("0.00");


    /**Contructor UI-de tip consola
     * @param productService service-ul pentru produse
     * @param cartService service-ul pentur produsele din cos
     */
    public UI(ProductService productService, CartService cartService) {
        this.productService=productService;
        this.cartService =cartService;
    }

    public void run() {
        int cmd;
        while (true) {
            System.out.println("MENIU:");
            System.out.println(" 0. Exit");
            System.out.println(" 1. DisplayProducts      2. AddToCart            3. RemoveFromCart ");
            System.out.println(" 4. DisplayCart        5. ProceedToCheckout     6.ApplayVAT&DISCOUNT");
            System.out.print("Enter your command: ");

            try {
                cmd = Integer.parseInt(console.readLine());
                System.out.println();

                if (cmd == 0) {
                    System.out.println("Program ended!");
                    break;
                }
                if (cmd < 0 || cmd > 7)
                    System.out.println("Invalid command!");
                if (cmd == 1)
                    displayProdcuts();
                if (cmd == 2)
                    addProduct();
                if (cmd == 3)
                    removeProduct();
                if (cmd == 4)
                    displayCart();
                if (cmd == 5)
                    proceedToCheckout();
                if (cmd == 6)
                    applayVatDiscount();
            } catch (NumberFormatException | IOException | RepoException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * Metoda ce afiseaza produsele din stoc
     */
    public void displayProdcuts() {
        productService.getAllProducts().forEach(product -> System.out.println(product.getProductID()+". "+ product.getName()+" - $"+product.getPrice()));
    }

    /**Metoda care citesete id-ul unui produs si cantitaeta si il adauga in cos dupa care afiseaza continutul cosului
     * @throws IOException
     * @throws RepoException
     */
    public void addProduct() throws IOException, RepoException {
        int productID, quantity;
        System.out.print("Enter product ID :");
        productID = Integer.parseInt(console.readLine());
        System.out.print("Enter quantity: ");
        quantity = Integer.parseInt(console.readLine());
        cartService.addProductToCart(productID,quantity);
        displayCart();
    }

    /**Metoda care citesete id-ul unui produs si il eliminadin cos
     * @throws IOException
     * @throws RepoException
     */
    public void removeProduct() throws IOException, RepoException {
        int ID;
        System.out.print("Enter product ID: ");
        ID = Integer.parseInt(console.readLine());
        cartService.removeProductFromCart(ID);
    }

    /**
     * Metoda care afiseaza produsele din cos nume X cantitate
     */
    public void displayCart(){
        Map<Product,Integer> cart= cartService.getProductsInCart();
        if(cart.size()==0)
            System.out.println("The cart is empty!");
        else{
            System.out.println("Cart:");
            for(Product product:cart.keySet())
                System.out.println(product.getName()+" x "+cart.get(product));
        }
    }

    /**
     * Metoda care afiseaza factura pentru produsele din cos
     */
    public void proceedToCheckout(){
        displayCart();
        List<Double> invoice= cartService.computeInvoice(false,false);
        System.out.println("  INVOICE\nSubtotal: $"+df.format(invoice.get(0))+"\n" +
                "Shipping: $"+df.format(invoice.get(1))+"\n" +
                "Total: $"+df.format(invoice.get(2)));

    }
    /**
     * Metoda care afiseaza factura pentru produsele din cos dupa aplicarea discount-ului si VAT-ului
     */
    public void applayVatDiscount(){
        List<Double> invoice= cartService.computeInvoice(true,true);
        System.out.println("  INVOICE with VAT and DISOCUNT\nSubtotal: $"+df.format(invoice.get(0))+"\n" +
                "Shipping: $"+df.format(invoice.get(1))+"\n" +
                "Total: $"+df.format(invoice.get(2)));
    }

}
