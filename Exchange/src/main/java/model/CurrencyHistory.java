package model;

import java.util.Date;
import java.util.List;

public class CurrencyHistory {
    private Currency currency;
    private List <Date> dates;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }
}
