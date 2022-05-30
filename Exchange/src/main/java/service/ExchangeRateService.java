package service;

import dao.daoimpl.CurrencyDaoImpl;
import model.Currency;
import model.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateService {
    private static List<ExchangeRate> listOfExchangeRates = new ArrayList<>();

    private static ExchangeRateService ourInstance = new ExchangeRateService();

    public static ExchangeRateService getInstance() {
        return ourInstance;
    }

    static {
        try {
//            List<List<String>> dataCSV;
//            dataCSV = CSVService.getInstance().readCSVData("src/main/java/files/", "currencyInfo.csv");
//            dataCSV.remove(0);
//
//            updateCurrencies();
//            for (List<String> data: dataCSV) {
//                double rate = Double.parseDouble(data.get(4));
//                String code = data.get(1);
//                setRateToCurrencyCode(rate, code);
//            }

            CurrencyDaoImpl dbC = new CurrencyDaoImpl();

            updateCurrencies();
            for (ExchangeRate exchangeRate : listOfExchangeRates) {
                String code = exchangeRate.getCurrencyCode();
                int id = dbC.selectByCode(code);
                double rate = dbC.selectCurrencyById(id).getRate();

                setRateToCurrencyCode(rate, exchangeRate.getCurrencyCode());
            }


        } catch(Exception ex) {
            throw new Error(ex);
        }
    }

    private ExchangeRateService() {
    }

    private static void updateCurrencies() {
        CurrencyService CS = CurrencyService.getInstance();
        List <Currency> currencies = CurrencyService.getListOfCurrencies();

        for (Currency currency: currencies) {
            ExchangeRate exchangeRate = new ExchangeRate(
                    currency.getCurrencyName(),
                    currency.getCurrencyCode(),
                    currency.getCurrencySymbol(),
                    0.000000);

            listOfExchangeRates.add(exchangeRate);
        }
    }

    public static List<ExchangeRate> getListOfExchangeRates() {
        return listOfExchangeRates;
    }

    public static void setListOfExchangeRates(List<ExchangeRate> listOfExchangeRates) {
        ExchangeRateService.listOfExchangeRates = listOfExchangeRates;
    }

    public ExchangeRate getCurrencyByName(String name) {
        for (ExchangeRate exchangeRate: listOfExchangeRates) {
            if (exchangeRate.getCurrencyName().equals(name)) {
                return exchangeRate;
            }
        }

        return null;
    }

    public ExchangeRate getCurrencyByCode(String code) {
        for (ExchangeRate exchangeRate: listOfExchangeRates) {
            if (exchangeRate.getCurrencyCode().equals(code)) {
                return exchangeRate;
            }
        }

        return null;
    }

    public ExchangeRate getCurrencyBySymbol(String symbol) {
        for (ExchangeRate exchangeRate: listOfExchangeRates) {
            if (exchangeRate.getCurrencySymbol().equals(symbol)) {
                return exchangeRate;
            }
        }

        return null;
    }

    private static void setRateToCurrencyCode(double rate, String code) {
        for (ExchangeRate exchangeRate: listOfExchangeRates) {
            if (exchangeRate.getCurrencyCode().equals(code)) {
                exchangeRate.setRate(rate);
            }
        }
    }

    public double exchange(String fromCurrencyCode, String toCurrencyCode, double sum) {
        ExchangeRate from = getCurrencyByCode(fromCurrencyCode);
        ExchangeRate to = getCurrencyByCode(toCurrencyCode);

        double sumToDefault = from.getInverseRate() * sum;
        return to.getRate() * sumToDefault * (fromCurrencyCode.equals("USD") ? 0.99 : 1.00);
    }
}
