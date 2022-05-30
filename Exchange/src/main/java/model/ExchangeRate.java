package model;

// the Exchange Rates are made for US Dollars
public class ExchangeRate extends Currency {
    double rate;

    public ExchangeRate() { }

    public ExchangeRate(String currencyName, String currencyCode, String currencySymbol, double rate) {
        super(currencyName, currencyCode, currencySymbol);
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getInverseRate() {
        return 1.0 / rate;
    }

    @Override
    public String toString() {
        return getCurrencyCode() + " <- " + rate;
    }
}
