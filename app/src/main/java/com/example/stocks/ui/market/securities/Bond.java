package com.example.stocks.ui.market.securities;

public class Bond extends Security{
    double originalPrice;
    double couponPrice;
    double paymentPeriod;
    double bankruptcyProbability; // вероятность банкротства

    public Bond(String name, Currency currency, long id, double originalPrice, double couponPrice, double paymentPeriod, double bankruptcyProbability) {
        super(name, currency, id);
        this.originalPrice = originalPrice;
        this.couponPrice = couponPrice;
        this.paymentPeriod = paymentPeriod;
        this.bankruptcyProbability = bankruptcyProbability;
        this.currentPrice = originalPrice;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public double getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(double paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public double getBankruptcyProbability() {
        return bankruptcyProbability;
    }

    public void setBankruptcyProbability(double bankruptcyProbability) {
        this.bankruptcyProbability = bankruptcyProbability;
    }

    @Override
    public String toString() {
        return name;
    }
}
