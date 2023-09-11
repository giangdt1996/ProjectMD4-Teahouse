package teahouse.projectmd4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teahouse.projectmd4.model.Order;
import teahouse.projectmd4.model.Product;
import teahouse.projectmd4.model.ProductDto;
import teahouse.projectmd4.model.User;
import teahouse.projectmd4.service.AdminService_Product;
import teahouse.projectmd4.service.CartService;
import teahouse.projectmd4.service.OrderService;
import teahouse.projectmd4.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"/admin"})
@PropertySource("classpath:upload.properties")
public class AdminController {


@Autowired
private OrderService orderService;
    @Autowired
    private AdminService_Product adminService;
    @Autowired
    private UserService userService;

    @Autowired
    ServletContext context;

    @Value("/Users/shmily/Desktop/SpringMVC/projectMD4/src/main/webapp/WEB-INF/upload/")
    private String pathUpload;

    //    Điều hướng về home
        @GetMapping("/dashboard")
    public String admin() {
        return "/admin/Dashboard";
    }

    // lấy tất cả products
    @GetMapping("/products")
    public String tableproduct(Model model) {
        model.addAttribute("list", adminService.findAll());
        return "/admin/TableProduct";
    }

    // Thêm products
    @GetMapping("/addProduct")
    public ModelAndView addProduct() {
        return new ModelAndView("/admin/addProducts", "product", new Product());
    }

    @PostMapping("/addProduct")
    public String save(@ModelAttribute("product") ProductDto productDto) {
        File file = new File(pathUpload);
        if (!file.exists()) {
            file.mkdir();
        }
        String urlimage = productDto.getImage().getOriginalFilename();
        if (urlimage.isEmpty()) {
            return "/admin/addProducts";
        }
        try {
            FileCopyUtils.copy(productDto.getImage().getBytes(), new File(pathUpload + urlimage));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Product p = new Product();
        p.setProduct_id(productDto.getProduct_id());
        p.setProduct_name(productDto.getProduct_name());
        p.setImage(urlimage);
        p.setPrice(productDto.getPrice());
        p.setStock(productDto.getStock());
        p.setDescription(productDto.getDescription());
        adminService.save(p);
        return "redirect:/admin/products";
    }

    // END thêm products
//    Xóa product
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        adminService.delete(id);
        return "redirect:/admin/products";
    }

    //    Cập nhật product
    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable("id") int id, HttpSession session) {
            Product p = adminService.findById(id);

            String oldImg = p.getImage();
            session.setAttribute("oldImg",oldImg);
        return new ModelAndView("/admin/updateProduct", "productUpdate", adminService.findById(id));
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("productUpdate") ProductDto productDto,HttpSession session) {
        File file = new File(pathUpload);
        if (!file.exists()) {
            file.mkdir();
        }
        String oldImg= (String) session.getAttribute("oldImg");
        String urlimage = productDto.getImage().getOriginalFilename();
        if(urlimage.isEmpty()){
            urlimage = oldImg;
        }else{
            try {
                FileCopyUtils.copy(productDto.getImage().getBytes(), new File(pathUpload + urlimage));

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        Product p = new Product();
        p.setProduct_id(productDto.getProduct_id());
        p.setProduct_name(productDto.getProduct_name());
        p.setImage(urlimage);

        p.setPrice(productDto.getPrice());
        p.setStock(productDto.getStock());
        p.setDescription(productDto.getDescription());

        adminService.save(p);
        return "redirect:/admin/products";
    }

// lấy tất cả User
    @GetMapping("/users")
    public String tableUser(Model model) {
        model.addAttribute("list", userService.findAll());
        return "/admin/TableUser";
    }

//    // Thêm User
    @GetMapping("/addUser")
    public ModelAndView addUser() {
        return new ModelAndView("/admin/addUser", "users", new User());
    }

    @PostMapping("/addUser")
    public String save(@ModelAttribute("users") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

//    // END thêm User
////    Xóa user
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
//
//    //    Cập nhật User
    @GetMapping("/updateUser/{id}")
    public ModelAndView updateUser(@PathVariable("id") int id,Model model) {
        List<String> options = new ArrayList<>();
        options.add("Active");
        options.add("Inactive");
        model.addAttribute("options", options);
        return new ModelAndView("/admin/updateUser", "UserUpdate", userService.findById(id));
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("UserUpdate") User user) {
//        File file = new File(pathUpload);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        String urlimage = customerDto.getAvatar().getOriginalFilename();
//
//        try {
//            FileCopyUtils.copy(customerDto.getAvatar().getBytes(), new File(pathUpload + urlimage));
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
//        Customer c = new Customer();
//        c.setId(customerDto.getId());
//        c.setFullname(customerDto.getFullname());
//        c.setUsername(customerDto.getPassword());
//        c.setCountry(customerDto.getCountry());
//        c.setCity(customerDto.getCity());
//        c.setPhone(customerDto.getPhone());
//        c.setEmail(customerDto.getEmail());
//        c.setBirthdate(customerDto.getBirthdate());
//        c.setAvatar(urlimage);
//        c.setRole(customerDto.getRole());
//        c.setStatus(customerDto.isStatus());
        userService.save(user);
        return "redirect:/admin/users";
    }
// Lock/Unlock User
@GetMapping("/lock/{id}")
public String lockUser(@PathVariable("id") int id) {
    userService.lock(id);
    return "redirect:/admin/users";
}
// Order

    @GetMapping("/order")
    public ModelAndView getOrder() {
        return new ModelAndView("/admin/TableOrder", "order", orderService.findAll());

    }
    @GetMapping("/change/{id}")
    public String changeSttOrder(@PathVariable("id") int id) {
        adminService.changeStt(id);
        return "redirect:/admin/order";}

    @GetMapping("/editorder/{id}")
    public ModelAndView getOrder(@PathVariable int id) {
        return new ModelAndView("/admin/UpdateOrder", "orderedit", orderService.findById(id));
    }

    @PostMapping("/editorder")
    public String updateOrder(@ModelAttribute("orderedit") Order order) {

        orderService.save(order);
        return "redirect:/admin/order";
    }

    @GetMapping("/getproductdetail/{id}")
    public ModelAndView getProduct(@PathVariable("id") int idOrder) {

        return new ModelAndView("/admin/ProductDetails", "productDetails", orderService.OrderDetailProduct(idOrder));
    }
}
