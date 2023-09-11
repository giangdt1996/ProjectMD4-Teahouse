package teahouse.projectmd4.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teahouse.projectmd4.model.Product;
import teahouse.projectmd4.model.User;
import teahouse.projectmd4.util.ConnectDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService_Product implements IGenericService<Product, Integer> {
    @Override
    public List<Product> findAll() {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        List<Product> productList = new ArrayList<>();
        try {
            CallableStatement callSt = conn.prepareCall("{call FindAllProduct}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setProduct_id(rs.getLong("product_id"));
                p.setProduct_name(rs.getString("name"));
                p.setImage(rs.getString("image"));
                p.setPrice(rs.getFloat("unit_price"));
                p.setStock(rs.getInt("stock"));

                p.setDescription(rs.getString("description"));

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

    @Override
    public void save(Product product) {
        Connection conn = null;
        conn = ConnectDB.getConnection();

        try {
            CallableStatement callSt = null;
            if (product.getProduct_id() == null) {
                // thêm moi
                callSt = conn.prepareCall("{call  AddProduct(?,?,?,?,?)}");
                callSt.setString(1, product.getProduct_name());
                callSt.setString(2, product.getDescription());
                callSt.setDouble(3, product.getPrice());
                callSt.setString(4, product.getImage());
                callSt.setInt(5, product.getStock());
//                callSt.executeUpdate();

            } else {
                // cap nhat
                 callSt = conn.prepareCall("{call  UpdateProduct(?,?,?,?,?,?)}");
                callSt.setLong(1, product.getProduct_id());
                callSt.setString(2, product.getProduct_name());
                callSt.setString(3, product.getDescription());
                callSt.setDouble(4, product.getPrice());
                callSt.setString(5, product.getImage());
                callSt.setInt(6, product.getStock());

            }
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
    public Product findById(Integer integer) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        Product p = null;
        try {
            CallableStatement callSt = conn.prepareCall("{call  FindByIdProduct(?)}");
            callSt.setInt(1, integer);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                p = new Product();
                p.setProduct_id(rs.getLong("product_id"));
                p.setProduct_name(rs.getString("name"));
                p.setImage(rs.getString("image"));
                p.setPrice(rs.getDouble("unit_price"));
                p.setStock(rs.getInt("stock"));

                p.setDescription(rs.getString("description"));

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
    public void delete(Integer integer) {
        Connection conn = null;
        conn = ConnectDB.getConnection();
        try {
            // xóa ảnh phụ
            CallableStatement callSt = conn.prepareCall("{call  DeleteProduct(?)}");
            callSt.setInt(1, integer);
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
    public void changeStt(int id){
        Connection conn = null;
        conn = ConnectDB.getConnection();
        try {
            // xóa ảnh phụ
            CallableStatement callSt = conn.prepareCall("{call changeSttOrder(?)}");
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
