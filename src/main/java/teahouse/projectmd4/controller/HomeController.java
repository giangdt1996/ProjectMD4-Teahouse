package teahouse.projectmd4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teahouse.projectmd4.model.User;
import teahouse.projectmd4.service.AdminService_Product;
import teahouse.projectmd4.service.OrderService;
import teahouse.projectmd4.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping({"/", "/home"})
public class HomeController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    private AdminService_Product adminService;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("index","products",adminService.findAll());
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/products")
    public String product() {
        return "product";
    }

    @GetMapping("/store")
    public ModelAndView store() {

        return new ModelAndView("store","products",adminService.findAll());
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }

    @GetMapping("/feature")
    public String feature() {
        return "feature";
    }

    @GetMapping("/testimonial")
    public String testimonial() {
        return "testimonial";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("userLogin", user);
            System.out.println(user.getRoleId());
            if (user.getRoleId() == 1) {
                return "/admin/Dashboard";
            } else {
                return "redirect:/";
            }
        }
            session.setAttribute("error", "Invalid username/password or your account is blocked");
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register","user",new User());
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, BindingResult errors) {
        userService.checkValidate_nameEmpty(errors,user.getUsername(), user.getPassword());
        if(errors.hasErrors()){
            return "register";
        } else if(errors.hasErrors()){
            return "register";
        }else if(errors.hasErrors()){
            return "register";
        }
        else{
            userService.save(user);
            return "login";}
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
       session.removeAttribute("userLogin");
        return "login";
    }

    @GetMapping("/change/{id}")
    public ModelAndView editInfor(@PathVariable("id") int id) {
        return new ModelAndView("editInfor","userLogin",userService.findById(id)) ;
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("userLogin") User user,HttpSession session) {
        session.removeAttribute("userLogin");
       userService.save(user);
        return "redirect:/login";
    }
    @GetMapping("/history/{id}")
    public ModelAndView getHistory(@PathVariable("id") int id){
        return new ModelAndView("history","history",orderService.findById(id));
    }
    @GetMapping("/getproductdetails/{id}")
    public ModelAndView getProduct(@PathVariable("id") int idOrder) {

        return new ModelAndView("/ProductDetails", "productDetails", orderService.OrderDetailProduct(idOrder));
    }
}


