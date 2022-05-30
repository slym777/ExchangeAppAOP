package dao.daoimpl;

import dao.UserDao;
import model.User;
import utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public void insert(User user) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "INSERT INTO users (u_firstname, u_lastname, u_username, u_password, u_country, u_address)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, user.getFirstName());
            pStmt.setString(2, user.getLastName());
            pStmt.setString(3, user.getUsername());
            pStmt.setString(4, user.getPassword());
            pStmt.setString(5, user.getCountry());
            pStmt.setString(6, user.getAddress());

            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(int id, User user) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "UPDATE users " +
                                "SET u_firstname = ?, u_lastname = ?, u_username = ?, u_password = ?, u_country = ?, u_address = ? " +
                                "WHERE u_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, user.getFirstName());
            pStmt.setString(2, user.getLastName());
            pStmt.setString(3, user.getUsername());
            pStmt.setString(4, user.getPassword());
            pStmt.setString(5, user.getCountry());
            pStmt.setString(6, user.getAddress());
            pStmt.setInt(7, id);

            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "DELETE FROM users WHERE u_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setInt(1, id);

            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User selectUserById(int id) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT u_firstname, u_lastname, u_username, u_password, u_country, u_address " +
                                "FROM users " +
                                "WHERE u_id = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setInt(1, id);

            resultSet = pStmt.executeQuery();

            User result = new User();
            while (resultSet.next()) {
                result.setFirstName(resultSet.getString("u_firstname"));
                result.setLastName(resultSet.getString("u_lastname"));
                result.setUsername(resultSet.getString("u_username"));
                result.setPassword(resultSet.getString("u_password"));
                result.setCountry(resultSet.getString("u_country"));
                result.setAddress(resultSet.getString("u_address"));
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    public int findUsername(String username) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        int result = -1;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT u_id " +
                                "FROM users " +
                                "WHERE u_username = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, username);

            resultSet = pStmt.executeQuery();

            if (resultSet == null) {
                return -1;
            }

            while (resultSet.next()) {
                result = resultSet.getInt("u_id");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }

    @Override
    public String selectPasswordByUsername(String username) {
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;
        String result = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT u_password " +
                                "FROM users " +
                                "WHERE u_username = ?";

            pStmt = conn.prepareStatement(sqlCommand);
            pStmt.setString(1, username);

            resultSet = pStmt.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getString("u_password");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    @Override
    public void insertAll(List<User> userList) {
        for (User user : userList) {
            insert(user);
        }
    }

    @Override
    public List<User> selectAll() {
        List<User> result = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet resultSet = null;

        try {
            conn = ConnectionUtils.getConnection();

            String sqlCommand = "SELECT u_firstname, u_lastname, u_username, u_password, u_country, u_address " +
                                "FROM users ";

            pStmt = conn.prepareStatement(sqlCommand);

            resultSet = pStmt.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("u_firstname"));
                user.setLastName(resultSet.getString("u_lastname"));
                user.setUsername(resultSet.getString("u_username"));
                user.setPassword(resultSet.getString("u_password"));
                user.setCountry(resultSet.getString("u_country"));
                user.setAddress(resultSet.getString("u_address"));

                result.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pStmt != null) {
                try {
                    pStmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
