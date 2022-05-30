package GUI;

import auth.Authenticable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Register extends JFrame {
    private JLabel message;
    private JTextField usernameText, firstnameText, lastnameText, addressText;
    private JComboBox<String> countryText;
    private JPasswordField passwordText;
    private JButton register;

    public Register(String windowName) {
        super(windowName);

        setDefaultCloseOperation(Login.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 475));
        setSize(450, 475);

        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);

        JLabel firstnameLabel, lastnameLabel, countryLabel, addressLabel, usernameLabel, passwordLabel;

        firstnameLabel = new JLabel();
        firstnameLabel.setText("First Name:");
        firstnameLabel.setBounds(70, 70, 100, 30);

        firstnameText = new JTextField();
        firstnameText.setBounds(170, 70, 200, 30);

        lastnameLabel = new JLabel();
        lastnameLabel.setText("Last Name:");
        lastnameLabel.setBounds(70, 110, 100, 30);

        lastnameText = new JTextField();
        lastnameText.setBounds(170, 110, 200, 30);

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameLabel.setBounds(70, 150, 100, 30);

        usernameText = new JTextField();
        usernameText.setBounds(170, 150, 200, 30);

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordLabel.setBounds(70, 190, 100, 30);

        passwordText = new JPasswordField();
        passwordText.setBounds(170, 190, 200, 30);

        countryLabel = new JLabel();
        countryLabel.setText("Country:");
        countryLabel.setBounds(70, 230, 100, 30);

        String[] countries = {"Australia", "King's Landing", "Republic of Moldova", "Romania", "Ukraine", "United Kingdom", "USA"};

        countryText = new JComboBox<>(countries);
        countryText.setBounds(170, 230, 200, 30);

        addressLabel = new JLabel();
        addressLabel.setText("Address:");
        addressLabel.setBounds(70, 270, 100, 30);

        addressText = new JTextField();
        addressText.setBounds(170, 270, 200, 30);

        message = new JLabel();
        // message.setText("Aici va fi mesajul!!");
        message.setBounds(120, 300, 250, 30);

        register = new JButton("Register");
        register.setBounds(150, 330, 150, 30);

        add(firstnameLabel);
        add(firstnameText);
        add(lastnameLabel);
        add(lastnameText);
        add(usernameLabel);
        add(usernameText);
        add(passwordLabel);
        add(passwordText);
        add(countryLabel);
        add(countryText);
        add(addressLabel);
        add(addressText);
        add(message);
        add(register);

        setLayout(null);
    }

    public JLabel getMessage() {
        return message;
    }

    public void setMessage(JLabel message) {
        this.message = message;
    }

    public JTextField getUsernameText() {
        return usernameText;
    }

    public void setUsernameText(JTextField usernameText) {
        this.usernameText = usernameText;
    }

    public JTextField getFirstnameText() {
        return firstnameText;
    }

    public void setFirstnameText(JTextField firstnameText) {
        this.firstnameText = firstnameText;
    }

    public JTextField getLastnameText() {
        return lastnameText;
    }

    public void setLastnameText(JTextField lastnameText) {
        this.lastnameText = lastnameText;
    }

    public JTextField getAddressText() {
        return addressText;
    }

    public void setAddressText(JTextField addressText) {
        this.addressText = addressText;
    }

    public JComboBox<String> getCountryText() {
        return countryText;
    }

    public void setCountryText(JComboBox<String> countryText) {
        this.countryText = countryText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(JPasswordField passwordText) {
        this.passwordText = passwordText;
    }

    public JButton getRegister() {
        return register;
    }

    public void setRegister(JButton register) {
        this.register = register;
    }
}
