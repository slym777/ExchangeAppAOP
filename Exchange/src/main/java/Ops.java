import dao.daoimpl.CurrencyDaoImpl;
import dao.daoimpl.UserDaoImpl;
import GUI.Login;
import GUI.MainView;
import GUI.Register;
import GUI.UserView;
import model.*;
import model.Currency;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Ops {
    static private String validateLogin(String username, String password) {
        String result = null;

        UserDaoImpl dbU = new UserDaoImpl();
        int id = dbU.findUsername(username);
        if (id == -1) {
            result = "Wrong credentials!";
            return result;
        }

        if (!dbU.selectPasswordByUsername(username).equals(password)) {
            result = "Wrong credentials!";
            return result;
        }

        User user = dbU.selectUserById(id);
        UserView.setCurrentUser(user);
        result = "Successful login!";
        return result;
    }

    static private String validateRegister(User user) {
        String result = null;

        if (user.getFirstName().length() == 0 || user.getLastName().length() == 0 || user.getUsername().length() == 0 ||
                user.getPassword().length() == 0 || user.getAddress().length() == 0) {
            result = "Please complete all the fields!";
            return result;
        }

        if (user.getPassword().length() < 8) {
            result = "Password too short (min 8 chars)!";
            return result;
        }

        UserDaoImpl dbU = new UserDaoImpl();
        if (dbU.findUsername(user.getUsername()) != -1) {
            result = "This username is already in use!";
            return result;
        }

        result = "Register completed! Please log in!";

        return result;
    }

    static private String validateTransaction(Transaction transaction) {
        String result = null;

        if (transaction.getFromSum() <= 0) {
            result = "Invalid sum input!";

            return result;
        }

        boolean done = true;
        ExchangeRateService ERS = ExchangeRateService.getInstance();
        double toSum = ERS.exchange(transaction.getFromCurrency().getCurrencyCode(),
                transaction.getToCurrency().getCurrencyCode(), transaction.getFromSum());
        ExchangeOffice exchangeOffice = ExchangeOffice.getInstance();

        double quantity = exchangeOffice.getQuantity().get(transaction.getToCurrency());
        if (quantity < toSum) {
            done = false;
        }

        if (!done) {
            result = "Transaction failed!";

            return result;
        }

        result = "Transaction accepted!";

        return result;
    }

    static private void makeTransaction(Transaction transaction) {
        ExchangeRateService ERS = ExchangeRateService.getInstance();
        TransactionService TS = TransactionService.getInstance();
        CurrencyDaoImpl dbC = new CurrencyDaoImpl();

        Currency fromCurrency = transaction.getFromCurrency();
        Currency toCurrency = transaction.getToCurrency();
        dbC.selectByCode(fromCurrency.getCurrencyCode());
        double fromSum = transaction.getFromSum();
        double toSum = ERS.exchange(fromCurrency.getCurrencyCode(), toCurrency.getCurrencyCode(), fromSum);
        ExchangeOffice exchangeOffice = ExchangeOffice.getInstance();

        exchangeOffice.addCurrencyQuantity(fromCurrency, fromSum);
        exchangeOffice.subtractCurrencyQuantity(toCurrency, toSum);

//        dbC.update(fromCurrency, exchangeOffice.getQuantity().get(fromCurrency));
//        dbC.update(toCurrency, exchangeOffice.getQuantity().get(toCurrency));

        Transaction transact = new Transaction(transaction.getUser(), fromCurrency, toCurrency, fromSum, toSum, transaction.getDate(), true);
        TS.addNewTransaction(transact);

        transact.getUser().addTransaction(transact);
    }

    public static void main(String[] args) throws Exception {
        // --------------------- access to services ----------------------
        UserService US = UserService.getInstance();
        CurrencyService CS = CurrencyService.getInstance();
        TransactionService TS = TransactionService.getInstance();
        CurrencyHistoryService CHS = CurrencyHistoryService.getInstance();
        ExchangeRateService ERS = ExchangeRateService.getInstance();
        ExchangeOffice EO = ExchangeOffice.getInstance();
        // ---------------------------------------------------------------

        // --------------------- create tables if not created ----------------------
        CurrencyDaoImpl dbC = new CurrencyDaoImpl();
        UserDaoImpl dbU = new UserDaoImpl();

        List<ExchangeRate> exchangeRates = ExchangeRateService.getListOfExchangeRates();
        Map<Currency, Double> quantity = EO.getQuantity();
        List<User> users = UserService.getListOfUsers();


        Login login = new Login("Login");
        Register register = new Register("Register");
        MainView mainView = new MainView("Main View");
        UserView userView = new UserView("User View");

        login.pack();
        register.pack();
        mainView.pack();
        userView.pack();

        mainView.setVisible(true);

        login.getSubmit().addActionListener(actionEvent -> {
            String username = login.getUsernameText().getText();
            String password = String.valueOf(login.getPasswordText().getPassword());

            String validate = validateLogin(username, password);

            login.getMessage().setText(validate);

            if (validate.charAt(1) == 'r') {
                login.getMessage().setForeground(Color.RED);
            } else {
                login.getMessage().setForeground(Color.BLUE);
            }

            TimerTask clear = new TimerTask() {
                @Override
                public void run() {
                    login.getMessage().setText("");

                    if (validate.charAt(1) != 'r') {
                        login.setVisible(false);
                        userView.setVisible(true);
                        userView.getWelcomeLabel().setText("Welcome, " + UserView.getCurrentUser().getFirstName()
                                + " " + UserView.getCurrentUser().getLastName() + "!");
                    }
                }
            };

            Timer timer = new Timer();
            timer.schedule(clear, 1500);
        });

        login.getRegister().addActionListener(actionEvent -> {
            login.setVisible(false);
            register.setVisible(true);
        });

        register.getRegister().addActionListener(actionEvent -> {
            String firstname = register.getFirstnameText().getText();
            String lastname = register.getLastnameText().getText();
            String username = register.getUsernameText().getText();
            String password = String.valueOf(register.getPasswordText().getPassword());
            String country = register.getCountryText().getItemAt(register.getCountryText().getSelectedIndex());
            String address = register.getAddressText().getText();

            User user = new User(firstname, lastname, username, password, country, address);

            String validate = validateRegister(user);
            register.getMessage().setText(validate);

            if (validate.contains("Register")) {
                register.getMessage().setForeground(Color.BLUE);
                dbU.insert(user);
            } else {
                register.getMessage().setForeground(Color.RED);
            }

            TimerTask clear = new TimerTask() {
                @Override
                public void run() {
                    register.getMessage().setText("");

                    if (validate.contains("Register")) {
                        login.getUsernameText().setText("");
                        login.getPasswordText().setText("");

                        login.setVisible(true);
                        register.setVisible(false);
                    }
                }
            };

            Timer timer = new Timer();
            timer.schedule(clear, 1500);
        });

        mainView.getLogin().addActionListener(actionEvent -> {
            login.getUsernameText().setText("");
            login.getPasswordText().setText("");

            mainView.setVisible(false);
            login.setVisible(true);
        });

        userView.getLogout().addActionListener(actionEvent -> {
            userView.setVisible(false);

            mainView.updateFields();
            mainView.setVisible(true);
        });

        userView.getExchange().addActionListener(actionEvent -> {
            String fromC = userView.getFromCurrency().getItemAt(userView.getFromCurrency().getSelectedIndex());
            String toC = userView.getToCurrency().getItemAt(userView.getToCurrency().getSelectedIndex());

            Currency fromCurrency = CurrencyService.getInstance().getCurrencyByCode(fromC);
            Currency toCurrency = CurrencyService.getInstance().getCurrencyByCode(toC);

            String sumStr = userView.getSumText().getText();
            double sum = -1;
            if(sumStr.matches("[0-9]+")) {
                sum = (sumStr.length() == 0 ? -1 : Double.parseDouble(sumStr));
            }

            JFormattedTextField editor = userView.getDatePicker().getEditor();
            Date dateInPicker = (Date) editor.getValue();

            int day = dateInPicker.getDate();
            int month = dateInPicker.getMonth();
            int year = dateInPicker.getYear() + 1900;

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            Date date = cal.getTime();

            Transaction transaction = new Transaction(UserView.getCurrentUser(), fromCurrency, toCurrency, sum, date);

            String validate = validateTransaction(transaction);

            userView.getMessage().setText(validate);

            if (validate.contains("accepted")) {
                userView.getMessage().setForeground(Color.BLUE);
                makeTransaction(transaction);
            } else {
                userView.getMessage().setForeground(Color.RED);

            }

            TimerTask clear = new TimerTask() {
                @Override
                public void run() {
                    userView.getMessage().setText("");

                    if (validate.contains("accepted")) {
                        userView.getFromCurrency().setSelectedIndex(0);
                        userView.getToCurrency().setSelectedIndex(0);
                        userView.getSumText().setText("");
                    }
                }
            };

            Timer timer = new Timer();
            timer.schedule(clear, 2000);
        });


    }
}
