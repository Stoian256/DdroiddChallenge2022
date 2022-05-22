package domain;

public class Product {
    private int productID;
    private String name;
    private double price;
    private ShippingRate shippingRate;
    private double weight;

    /**Contructor entitatea produs
     * @param productID id-ul produsului
     * @param name numele produsului
     * @param price pretul produsului
     * @param shippingRate shippingRate-ul produsului
     * @param weight greutatea produsului
     */
    public Product(int productID, String name, double price, ShippingRate shippingRate, double weight) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.shippingRate =shippingRate;
        this.weight = weight;
    }

    /**Metoda getter pentru Id-ul produsului
     * @return id-ul produsului
     */
    public int getProductID() {
        return productID;
    }


    /**Metoda setter pentru Id-ul produsului
     * @param productID noul id al produsului
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**Metoda getter pentru pretul produsului
     * @return pretul produsului
     */
    public double getPrice() {
        return price;
    }

    /**Metoda setter pentru pretul produsului
     * @param price noul pret al produsului
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**Metoda getter pentru shippingRate-ul produsului
     * @return shippingRate-ul produsului
     */
    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    /**Metoda setter pentru shippingRate-ul produsului
     * @param shippingRate noul shippingRate al produsului
     */
    public void setShippingRate(ShippingRate shippingRate) {
        this.shippingRate = shippingRate;
    }

    /**Metoda getter pentru greutatea produsului
     * @return greutatea produsului
     */
    public double getWeight() {
        return weight;
    }

    /**Metoda setter pentru greutate produsului
     * @param weight noua greutatea al produsului
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    /**Metoda setter pentru numele produsului
     * @param name noul nume al produsului
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Matoda ce constuieste un string pe baza atributelor unui produs
     * @return string-ul atributelor produsului
     */
    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", shippedFrom=" + shippingRate.getCountry() +
                ", weight=" + weight +
                '}';
    }
}
