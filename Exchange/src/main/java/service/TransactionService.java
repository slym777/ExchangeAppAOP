package service;

import model.*;
import model.Currency;

import java.util.*;

public class TransactionService {
    private static List <Transaction> listOfTransactions = new ArrayList <>();

    private static TransactionService ourInstance = new TransactionService();

    public static TransactionService getInstance() {
        return ourInstance;
    }

    private TransactionService() {

    }

    public static List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public void addNewTransaction(Transaction transaction) {
        listOfTransactions.add(transaction);
    }

    public void makeNewTransaction() {
        ExchangeRateService ERS = ExchangeRateService.getInstance();
        Scanner obj = new Scanner(System.in);

        System.out.println("New transaction (nr. " + (listOfTransactions.size() + 1) + ") to be made...");

        System.out.print("username - ");
        String username = obj.nextLine();

        System.out.print("From Currency (CODE) - ");
        String fCurrency = obj.nextLine();

        System.out.print("To Currency (CODE) - ");
        String tCurrency = obj.nextLine();

        System.out.print("Sum - ");
        double sum = obj.nextDouble();

        System.out.print("Day (DD) - ");
        int day = obj.nextInt();

        System.out.print("Month (MM) - ");
        int month = obj.nextInt();

        System.out.print("Year (YYYY) - ");
        int year = obj.nextInt();


        UserService US = UserService.getInstance();
        User user = US.getUserByUsername(username);

        CurrencyService CS = CurrencyService.getInstance();
        Currency fromCurrency = CS.getCurrencyByCode(fCurrency);
        Currency toCurrency = CS.getCurrencyByCode(tCurrency);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 4 - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();

        boolean done = true;
        double toSum = ERS.exchange(fCurrency, tCurrency, sum);
        ExchangeOffice exchangeOffice = ExchangeOffice.getInstance();

        double quantity = exchangeOffice.getQuantity().get(toCurrency);
        if (quantity < toSum) {
            done = false;
            toSum = 0;
        }

        if (done) {
            exchangeOffice.addCurrencyQuantity(fromCurrency, sum);
            exchangeOffice.subtractCurrencyQuantity(toCurrency, toSum);
        }

        Transaction transaction = new Transaction(user, fromCurrency, toCurrency, sum, toSum, date, done);
        listOfTransactions.add(transaction);

        user.addTransaction(transaction);

        System.out.print("Transaction nr." + listOfTransactions.size());
        if (done) {
            System.out.println(" ended successfully!");
        }
        else {
            System.out.println(" failed!");
        }
    }

    public Map <Currency, Double> getMappedCurrency() {
        Map < Currency, Double > infoMap = new HashMap<>();

        List <Currency> currencies = CurrencyService.getListOfCurrencies();
        for (Currency currency: currencies) {
            infoMap.put(currency, 0.0);
        }

        return infoMap;
    }

    public Map <Currency, Double> getCurrencySaleInfo() {
        Map < Currency, Double > infoMap = new HashMap<>();

        List <Currency> currencies = CurrencyService.getListOfCurrencies();
        for (Currency currency: currencies) {
            infoMap.put(currency, 0.0);
        }

        for (Transaction transaction: listOfTransactions) {
            if (transaction == null || !transaction.getDone()) continue;
            double current = infoMap.get(transaction.getFromCurrency());
            current += transaction.getFromSum();
            infoMap.put(transaction.getFromCurrency(), current);
        }

        return infoMap;
    }

    public Map <Currency, Double> getCurrencyBuyInfo() {
        Map < Currency, Double > infoMap = new HashMap<>();

        List <Currency> currencies = CurrencyService.getListOfCurrencies();
        for (Currency currency: currencies) {
            infoMap.put(currency, 0.0);
        }

        for (Transaction transaction: listOfTransactions) {
            if (transaction == null || !transaction.getDone()) continue;
            double current = infoMap.get(transaction.getToCurrency());
            current += transaction.getToSum();
            infoMap.put(transaction.getToCurrency(), current);
        }

        return infoMap;
    }
}
