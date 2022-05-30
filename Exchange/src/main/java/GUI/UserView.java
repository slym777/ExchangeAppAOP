package GUI;

import model.Currency;
import model.ExchangeRate;
import model.User;
import org.jdesktop.swingx.JXDatePicker;
import service.CurrencyService;
import service.ExchangeRateService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class UserView extends JFrame {
    private JLabel welcomeLabel = new JLabel();
    private JButton logout;
    private static User currentUser;
    private JComboBox<String> fromCurrency, toCurrency;
    private JTextField sumText;
    private JXDatePicker datePicker;

    private JLabel message;
    private JButton exchange;

    public UserView(String windowName) {
        super(windowName);
        currentUser = new User();

        currentUser = UserService.getInstance().getUserByUsername("godzilla");

        setDefaultCloseOperation(Login.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(700, 600));
        setSize(700, 600);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

        logout = new JButton("Log out");
        logout.setBounds(550, 50, 100, 30);

        add(logout);
        setLayout(null);

        welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD|Font.ITALIC, 25));
        welcomeLabel.setBounds(50, 40, 400, 50);
        add(welcomeLabel);

        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates = ExchangeRateService.getListOfExchangeRates();

        String[][] data = new String[exchangeRates.size()][2];

        if(exchangeRates.size() > 0) {
            for (int i = 0; i < exchangeRates.size(); i++) {
                data[i][0] = " " + exchangeRates.get(i).getCurrencyCode() + " (" + exchangeRates.get(i).getCurrencySymbol() + ")";
                data[i][1] = " " + exchangeRates.get(i).getRate();
            }
        }

        String[] columns = {"Currency", "Rate"};
        JTable table = new JTable(data, columns);

        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 160, 200, 250);

        add(scrollPane);

        JLabel infoLabel, fromCurrLabel, toCurrLabel, sumLabel, dateLabel;

        infoLabel = new JLabel("Exchange NOW!");
        infoLabel.setForeground(Color.DARK_GRAY);
        infoLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        infoLabel.setBounds( 400, 170, 150, 30);

        fromCurrLabel = new JLabel("From:");
        fromCurrLabel.setBounds(370, 195, 80, 30);

        Vector<String> currencies = CurrencyService.getListOfCurrencies().stream().map(Currency::getCurrencyCode).collect(Collectors.toCollection(Vector::new));
        fromCurrency = new JComboBox<String>(currencies);
        fromCurrency.setBounds(450, 200, 120, 20);

        toCurrLabel = new JLabel("To:");
        toCurrLabel.setBounds(370, 225, 80, 30);

        toCurrency = new JComboBox<String>(currencies);
        toCurrency.setBounds(450, 228, 120, 20);

        sumLabel = new JLabel("Sum:");
        sumLabel.setBounds(370, 255, 80, 30);

        sumText = new JTextField();
        sumText.setBounds(450, 255, 120, 25);


        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(370, 285, 80, 30);

        datePicker = new JXDatePicker();
        datePicker.setBounds(450, 285, 120, 25);

        datePicker.setDate(Calendar.getInstance().getTime());
        datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

        message = new JLabel();
        message.setBounds(400, 305, 160, 30);

        exchange = new JButton("Exchange!");
        exchange.setBounds(410, 335, 120, 25);

        add(infoLabel);
        add(fromCurrLabel);
        add(fromCurrency);
        add(toCurrLabel);
        add(toCurrency);
        add(sumLabel);
        add(sumText);
        add(dateLabel);
        add(datePicker);
        add(message);
        add(exchange);
    }

    public JButton getLogout() {
        return logout;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserView.currentUser = currentUser;
    }

    public JLabel getWelcomeLabel() {
        return welcomeLabel;
    }

    public JComboBox<String> getFromCurrency() {
        return fromCurrency;
    }

    public JComboBox<String> getToCurrency() {
        return toCurrency;
    }

    public JTextField getSumText() {
        return sumText;
    }

    public JXDatePicker getDatePicker(){
        return datePicker;
    }

    public JLabel getMessage() {
        return message;
    }

    public JButton getExchange() {
        return exchange;
    }

    public void setFromCurrency(JComboBox<String> fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setToCurrency(JComboBox<String> toCurrency) {
        this.toCurrency = toCurrency;
    }

    public void setSumText(JTextField sumText) {
        this.sumText = sumText;
    }
}
