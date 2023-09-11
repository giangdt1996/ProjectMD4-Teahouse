package teahouse.projectmd4.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import teahouse.projectmd4.model.User;
import teahouse.projectmd4.util.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IGenericService<User, Integer> {
    @Override
    public List<User> findAll() {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        List<User> customerList = new ArrayList<>();
        try {
            CallableStatement callSt = conn.prepareCall("{call findAll}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                User c = new User();
                c.setId(rs.getLong("id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setUsername(rs.getString("username"));
                c.setPassword(rs.getString("password"));
                c.setAddress(rs.getString("address"));
                c.setEmail(rs.getString("email"));
                c.setAvatar(rs.getString("avatar"));
                c.setRoleId(rs.getLong("role"));
                c.setStatus(rs.getBoolean("status"));

                customerList.add(c);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return customerList;
    }

    @Override
    public User findById(Integer integer) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        User c = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call findById(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                c = new User();
                c.setId(rs.getLong("id"));
                c.setUsername(rs.getString("username"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setPassword(rs.getString("password"));
                c.setEmail(rs.getString("email"));
                c.setAddress(rs.getString("address"));
                c.setAvatar(rs.getString("avatar"));
                c.setRoleId(rs.getLong("role"));
                c.setStatus(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return c;
    }

    @Override
    public void save(User user) {
        Connection conn = ConnectDB.getConnection();
        try {
            if (user.getId() == null) {

                // register
                CallableStatement callSt = conn.prepareCall("{call REGISTER(?,?,?,?,?,?)}");
                callSt.setString(1, user.getUsername());
                callSt.setString(2, user.getEmail());
                callSt.setString(3, user.getFirstName());
                callSt.setString(4, user.getLastName());
                callSt.setString(5, user.getPassword());
                callSt.setString(6, user.getAddress());
                callSt.executeUpdate();
            }else{
                CallableStatement callSt = conn.prepareCall("{call updateUser(?,?,?,?,?,?,?)}");
                callSt.setLong(1, user.getId());
                callSt.setString(2, user.getUsername());
                callSt.setString(3, user.getEmail());
                callSt.setString(4, user.getFirstName());
                callSt.setString(5, user.getLastName());
                callSt.setString(6, user.getPassword());
                callSt.setString(7, user.getAddress());
                callSt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        try {
            // xóa ảnh phụ
            CallableStatement callSt = conn.prepareCall("{call deletebyId(?)}");
            callSt.setLong(1, integer);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public User login(String username, String password) {
        Connection conn = ConnectDB.getConnection();
        User userLogin = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call LOGIN(?,?)}");
            callSt.setString(1, username);
            callSt.setString(2, password);
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                userLogin = new User();
                userLogin.setId(rs.getLong("id"));
                userLogin.setUsername(rs.getString("username"));
                userLogin.setEmail(rs.getString("email"));
                userLogin.setFirstName(rs.getString("first_name"));
                userLogin.setLastName(rs.getString("last_name"));
                userLogin.setAddress(rs.getString("address"));
                userLogin.setAvatar(rs.getString("avatar"));
                userLogin.setStatus(rs.getBoolean("status"));
                userLogin.setRoleId(rs.getLong("role"));
                if (userLogin.isStatus()) {
                    return null;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectDB.closeConnection(conn);
        }
        return userLogin;
    }

    public void checkValidate_nameEmpty(Errors errors, String username, String password) {
        List<User> userList = findAll();

        if (username.trim().equals("") || username.length() < 6 || username.contains(" ")) {
            errors.rejectValue("username", "username.empty");

        }
        if (password.length() < 6 || password.trim().equals("") || password.contains(" ")) {
            errors.rejectValue("password", "password.invalid");

        }
        for (User u : userList) {
            if (username.equals(u.getUsername())) {
                errors.rejectValue("username", "username.check");

            }
        }
    }
    public void lock(int id){
        Connection conn = null;
        conn = ConnectDB.getConnection();
        try {
            // xóa ảnh phụ
            CallableStatement callSt = conn.prepareCall("{call lockUser(?)}");
            callSt.setLong(1, id);
            callSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }}
}
