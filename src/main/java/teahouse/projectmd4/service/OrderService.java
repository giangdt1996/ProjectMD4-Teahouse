package teahouse.projectmd4.service;

import org.springframework.stereotype.Service;
import teahouse.projectmd4.model.Order;
import teahouse.projectmd4.model.Product;
import teahouse.projectmd4.util.ConnectDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IGenericService<Order,Integer> {
    @Override
    public List<Order> findAll() {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        List<Order> orders = new ArrayList<>();
        try {
            CallableStatement callSt = conn.prepareCall("{call getOrder}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Order p = new Order();
                p.setIdOrder(rs.getLong("order_id"));
                p.setId_customer(rs.getLong("user_id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setPhone(rs.getString("phone"));
                p.setNote(rs.getString("note"));
                p.setOrder_at(rs.getDate("order_at"));
                p.setTotal_price(rs.getFloat("total_price"));
                p.setStatus(rs.getBoolean("status"));
                orders.add(p);
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
        return orders;
    }

    @Override
    public Order findById(Integer integer) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        Order p = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call getOrderbyId(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                p = new Order();
                p.setIdOrder(rs.getLong("order_id"));
                p.setId_customer(rs.getLong("user_id"));
                p.setName(rs.getString("name"));
                p.setAddress(rs.getString("address"));
                p.setPhone(rs.getString("phone"));
                p.setNote(rs.getString("note"));
                p.setOrder_at(rs.getDate("order_at"));
                p.setTotal_price(rs.getFloat("total_price"));
                p.setStatus(rs.getBoolean("status"));
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
        return p;
    }

    @Override
    public void save(Order order) {
        Connection conn = null;
        conn = ConnectDB.getConnection();

        try {
            CallableStatement callSt = conn.prepareCall("{call changeStt(?,?)}");
            callSt.setLong(1, order.getIdOrder());
            callSt.setBoolean(2, order.isStatus());
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

    @Override
    public void delete(Integer integer) {
    }
        public List<Product> OrderDetailProduct(Integer integer) {
            Connection conn = null;
            conn = ConnectDB.getConnection();
            List<Product> productList = new ArrayList<>();
            try {
                CallableStatement callSt = conn.prepareCall("{call OrderDetailProduct(?)}");
                callSt.setInt(1, integer);
                ResultSet rs = callSt.executeQuery();
                while (rs.next()) {
                    Product p = new Product();
                    p.setProduct_id(rs.getLong("product_id"));
                    p.setProduct_name(rs.getString("name"));
                    p.setImage(rs.getString("image"));
                    p.setStock(rs.getInt("stock"));
                    p.setPrice(rs.getFloat("unit_price"));
                    p.setStock(rs.getInt("stock"));
                    p.setDescription(rs.getString("description"));

                    if (p.getStock() <= 0) {
                        p.setStatus(false);
                    }
                    productList.add(p);

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
            return productList;
        }
}
