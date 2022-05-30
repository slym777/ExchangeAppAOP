package model;

import dao.daoimpl.CurrencyDaoImpl;
import service.CSVService;
import service.TransactionService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExchangeOffice {
    private static String name;
    private static String country;
    private static String address;
    private static String phoneNumber;
    private static Map<Currency, Double> quantity;

    private static ExchangeOffice ourInstance = new ExchangeOffice();

    public static ExchangeOffice getInstance() {
        return ourInstance;
    }
    {
        try {
//            List<List<String>> dataCSVCurrency;
            // dataCSVCurrency = CSVService.getInstance().readCSVData("src/main/java/files/", "currencyInfo.csv");
            // dataCSVCurrency.remove(0);
            // CurrencyService CS = CurrencyService.getInstance();
//            for (List<String> data: dataCSVCurrency) {
//                double quan = Double.parseDouble(data.get(3));
//                String code = data.get(1);
//
//                quantity.put(CS.getCurrencyByCode(code), quan);
//            }

            List<List<String>> dataCSVOffice;

            dataCSVOffice = CSVService.getInstance().readCSVData("src/main/java/files/", "exchangeOfficeInfo.csv");
            dataCSVOffice.remove(0);

            ExchangeOffice.name = dataCSVOffice.get(0).get(0);
            ExchangeOffice.country = dataCSVOffice.get(0).get(1);
            ExchangeOffice.address  = dataCSVOffice.get(0).get(2);
            ExchangeOffice.phoneNumber = dataCSVOffice.get(0).get(3);

            TransactionService TS = TransactionService.getInstance();
            quantity = TS.getMappedCurrency();

            CurrencyDaoImpl dbC = new CurrencyDaoImpl();
            for(Map.Entry<Currency, Double> entry : quantity.entrySet()) {
                String code = entry.getKey().getCurrencyCode();
                int id = dbC.selectByCode(code);
                double quan = dbC.selectQuantityById(id);

                entry.setValue(quan);
            }

        } catch(IOException ex) {
            throw new Error(ex);
        }
    }

    private ExchangeOffice() {
    }

    public double addCurrencyQuantity(Currency currency, double quan) {
        double prev = quantity.get(currency);
        prev += quan;
        quantity.put(currency, prev);
        return prev;
    }

    public double subtractCurrencyQuantity(Currency currency, double quan) {
        double prev = quantity.get(currency);
        prev -= quan;
        quantity.put(currency, prev);
        return prev;
    }

    public String getName() {
        return name;
    }

    public static void setName(String name) {
        ExchangeOffice.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        ExchangeOffice.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        ExchangeOffice.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        ExchangeOffice.phoneNumber = phoneNumber;
    }

    public Map<Currency, Double> getQuantity() {
        return quantity;
    }

    public void setQuantity(Map<Currency, Double> quantity) {
        ExchangeOffice.quantity = quantity;
    }
}
