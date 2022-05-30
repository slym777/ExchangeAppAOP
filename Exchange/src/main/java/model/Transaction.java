package model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    private User user;
    private Currency fromCurrency;
    private Currency toCurrency;
    private double fromSum;
    private double toSum;
    private Date date;
    private Boolean done;

    public Transaction(User user, Currency fromCurrency, Currency toCurrency, double fromSum, double toSum, Date date, Boolean done) {
        this.user = user;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromSum = fromSum;
        this.toSum = toSum;
        this.date = date;
        this.done = done;
    }

    public Transaction(User user, Currency fromCurrency, Currency toCurrency, double fromSum, Date date) {
        this.user = user;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromSum = fromSum;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getFromSum() {
        return fromSum;
    }

    public void setFromSum(double fromSum) {
        this.fromSum = fromSum;
    }

    public double getToSum() {
        return toSum;
    }

    public void setToSum(double toSum) {
        this.toSum = toSum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return "Transaction {" +
                "\n   user = " + user.getUsername() +
                "\n   fromCurrency = " + fromCurrency.getCurrencyCode() +
                "\n   toCurrency = " + toCurrency.getCurrencyCode() +
                "\n   fromSum = " + new DecimalFormat("##############.##").format(fromSum) +
                "\n   toSum = " + new DecimalFormat("##############.##").format(toSum) +
                "\n   date = " + sdf.format(date) +
                "\n   done = " + done +
                "\n  }";
    }
}
