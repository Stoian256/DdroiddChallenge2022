package domain;

public class SpecialOffer {
    private String requirementName;
    private Integer requirementCount;
    private String appliedOn;
    private Double discount;

    /**Contrucotr entytate oferta
     * @param requirementName numele produsului necesar in cos
     * @param requirementCount numarul de produse necesare in cos
     * @param appliedOn numele produsului pe care se aplica oferta
     * @param discount discount-ul
     */
    public SpecialOffer(String requirementName, Integer requirementCount, String appliedOn, Double discount) {
        this.requirementName = requirementName;
        this.requirementCount = requirementCount;
        this.appliedOn = appliedOn;
        this.discount = discount;
    }

    /**Metoda de getter pentru requirementName
     * @return numele produsului necesar in cos
     */
    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public Integer getRequirementCount() {
        return requirementCount;
    }

    public void setRequirementCount(Integer requirementCount) {
        this.requirementCount = requirementCount;
    }

    public String getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(String appliedOn) {
        this.appliedOn = appliedOn;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "SpecialOffer{" +
                "requirementName='" + requirementName + '\'' +
                ", requirementCount=" + requirementCount +
                ", appliedOn='" + appliedOn + '\'' +
                ", discount=" + discount +
                '}';
    }

}
