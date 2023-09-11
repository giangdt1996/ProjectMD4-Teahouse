package teahouse.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teahouse.projectmd4.model.*;
import teahouse.projectmd4.service.AdminService_Product;
import teahouse.projectmd4.service.CartService;
import teahouse.projectmd4.service.UserService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/cart"})
public class CartController {
    @Autowired
    private AdminService_Product adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @GetMapping
    public String getCart(HttpSession session, Model model){

        // đã có người đăng nhập rồi

//        tính tổng tiền
        double total = cartService.findAll().stream().map(ci -> ci.getQuantity() * ci.getP().getPrice()).reduce((double) 0,Double::sum);
        model.addAttribute("total",total);
        return "cart";
    }
    @GetMapping("/addToCart/{idPro}")
    public String addToCart(@PathVariable("idPro") int product_id, HttpSession session){
        User user= (User) session.getAttribute("userLogin");
        if (user!=null) {

            List<Product> products = adminService.findAll();
            Product p = findProductById(product_id,products);

            // kiểm tra sản phẩm dã có trong giỏ hàng chưa
            CartItem cartItem = cartService.findById(product_id);

            if(cartItem == null){
                // tạo mới item
                cartItem = new CartItem(cartService.getNewId(), p,1);
                cartService.save(cartItem);
            }else {
                // cập nhật số lượng thêm 1 đơn vị
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartService.save(cartItem);
            }
            double total = cartService.findAll().stream().map(ci -> ci.getQuantity() * ci.getP().getPrice()).reduce((double) 0,Double::sum);
            session.setAttribute("total",total);
            // lưu vào session
            session.setAttribute("cart",cartService.findAll());
            return "redirect:/";
        }
        return "redirect:/login";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id , HttpSession session){
        cartService.delete(id);
        session.setAttribute("cart",cartService.findAll());
        return "redirect:/";
    }
    @PostMapping("/update/{id}")
    public  String handleUpdate(HttpSession session,@PathVariable("id") int id, @RequestParam("quantity") int quantity){
        CartItem cartItem = cartService.findById(id);
        cartItem.setQuantity(quantity);
        cartService.save(cartItem);
        // lưu vào session
        session.setAttribute("cart",cartService.findAll());
        return "redirect:/";
    }
    @GetMapping("/order")
    public ModelAndView infoUser() {
        return new ModelAndView("/cart", "order", new Bill());

    }
    @PostMapping("/order")
    public String order(@ModelAttribute("order") Bill bill, HttpSession session) {

        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");

        User user = (User) session.getAttribute("userLogin");


        double price = (double) session.getAttribute("total");


        Order order = new Order();
        order.setId_customer(user.getId());
        order.setName(bill.getName());
        order.setEmail(bill.getEmail());
        order.setAddress(bill.getAddress());
        order.setPhone(bill.getPhone());
        order.setNote(bill.getNote());
        order.setTotal_price(price);
        cartService.addOrder(cartItems, order);
        session.removeAttribute("cart");
        session.removeAttribute("total");
        return "redirect:/";
    }

    public Product findProductById(int id, List<Product> list){
        for (Product p :list) {
            if(p.getProduct_id()==id){
                return p;
            }
        }
        return null;
    }

}
