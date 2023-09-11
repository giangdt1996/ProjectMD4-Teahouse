package teahouse.projectmd4.service;

import org.springframework.stereotype.Service;
import teahouse.projectmd4.model.CartItem;
import teahouse.projectmd4.model.Order;
import teahouse.projectmd4.model.Product;
import teahouse.projectmd4.util.ConnectDB;

import javax.servlet.http.HttpSession;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements IGenericService<CartItem,Integer> {
    private List<CartItem> cart = new ArrayList<>();



    @Override
    public List<CartItem> findAll() {
        return cart;
    }

    @Override
    public CartItem findById(Integer id) {
        for (CartItem c : cart
             ) {
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    @Override
    public void save(CartItem cartItem) {
        if(findById(cartItem.getId())==null){
            //them moi
cart.add(cartItem);
        }else{
            cart.set(cart.indexOf(findById(cartItem.getId())),cartItem);
        }
    }

    @Override
    public void delete(Integer id) {
        cart.remove(findById(id));
    }
    public int getNewId(){
        int max = 0;
        for (CartItem ci:cart
        ) {
            if(ci.getId()>max){
                max= ci.getId();
            }
        }
        return max+1;
    }
    public void addOrder(List<CartItem> cartItems, Order order) {
        try (Connection conn = ConnectDB.getConnection()) {
            conn.setAutoCommit(false);

            try (CallableStatement callSt = conn.prepareCall("{call addOrder(?,?,?,?,?,?,?,?)}")) {
                callSt.setLong(1, order.getId_customer());
                callSt.setDouble(2, order.getTotal_price());
                callSt.setString(3, order.getName());
                callSt.setString(4, order.getEmail());
                callSt.setString(5, order.getAddress());
                callSt.setString(6, order.getPhone());
                callSt.setString(7, order.getNote());
                callSt.registerOutParameter(8, Types.INTEGER);
                callSt.executeUpdate();

                int orderId = callSt.getInt(8);

                for (CartItem c : cartItems) {
                    try (CallableStatement call2 = conn.prepareCall("{call addOrderDetails(?,?,?,?)}")) {
                        call2.setInt(1, orderId);
                        call2.setLong(2, c.getP().getProduct_id());
                        call2.setDouble(3, c.getP().getProduct_id());
                        call2.setInt(4, c.getQuantity());
                        call2.executeUpdate();
                    }

                }



                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error while adding order and details", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting database connection", e);
        }
    }



}
