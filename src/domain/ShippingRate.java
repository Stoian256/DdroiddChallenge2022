package domain;

public class ShippingRate {
    private String country;
    private Double rate;

    /**Contructor entitatea shippingRate
     * @param country tara de livrare
     * @param rate rate-ul
     */
    public ShippingRate(String country, Double rate) {
        this.country = country;
        this.rate = rate;
    }

    /**Metoda de getter pentur tara
     * @return tara
     */
    public String getCountry() {
        return country;
    }

    /**Metoda de setter pentru tara
     * @param country noua tara
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**Metoda de getter pentur rate
     * @return rate-ul
     */
    public Double getRate() {
        return rate;
    }

    /**Metoda de setter pentur rate
     * @param rate noul rate
     */
    public void setRate(Double rate) {
        this.rate = rate;
    }
}
