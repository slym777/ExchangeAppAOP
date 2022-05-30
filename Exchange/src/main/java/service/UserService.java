package service;

import dao.daoimpl.UserDaoImpl;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static List<User> listOfUsers = new ArrayList<>();

    private static UserService ourInstance = new UserService();

    public static UserService getInstance() {
        return ourInstance;
    }

    static {
        try {
//            List<List<String>> dataCSV;
//            dataCSV = CSVService.getInstance().readCSVData("src/main/java/files/", "usersInfo.csv");
//            dataCSV.remove(0);
//            for (List<String> data: dataCSV) {
//                String firstName = data.get(0);
//                String lastName = data.get(1);
//                String username = data.get(2);
//                String passwordHash = data.get(3);
//                String country = data.get(4);
//                String address = data.get(5);
//
//                listOfUsers.add(new User(firstName, lastName, username, passwordHash, country, address));
//            }

            UserDaoImpl dbU = new UserDaoImpl();
            listOfUsers = dbU.selectAll();

        } catch (Exception ex){
            throw new Error(ex);
        }
    }

    private UserService() {
    }

    public static List<User> getListOfUsers() {
        return listOfUsers;
    }

    public User getUserByUsername(String username) {
        for (User user: listOfUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }


}
