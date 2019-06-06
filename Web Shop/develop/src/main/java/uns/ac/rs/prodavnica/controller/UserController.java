package uns.ac.rs.prodavnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uns.ac.rs.prodavnica.dto.LoginDTO;
import uns.ac.rs.prodavnica.entity.Logged;
import uns.ac.rs.prodavnica.entity.Role;
import uns.ac.rs.prodavnica.entity.User;
import uns.ac.rs.prodavnica.repository.LoggedRepository;
import uns.ac.rs.prodavnica.service.LoggedService;
import uns.ac.rs.prodavnica.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoggedService loggedService;

    @GetMapping("/")
    public String welcome() {
        return "home";
    }

    @GetMapping("/user")
    public String stranica() {
        return "user";
    }


    @GetMapping("/admin-profile")
    public String showAdminProfile( Model model) {
        Logged cr = loggedService.getCurrentUser();
        if (cr != null) {

            User u = userService.findOneByUsername(cr.getUsername());
            if (u.getRole().equals(Role.ADMIN)) {

                model.addAttribute("user", u);

            } else {
                return "error-page";
            }

        } else {
            return "error-page";
        }
        return "admin-profile";
    }

    @GetMapping("/deliverer-profile")
    public String showDelivererProfile( Model model) {
        Logged cr = loggedService.getCurrentUser();
        if (cr != null) {

            User u = userService.findOneByUsername(cr.getUsername());
            if (u.getRole().equals(Role.DELIVERER)) {

                model.addAttribute("user", u);

            } else {
                return "error-page";
            }

        } else {
            return "error-page";
        }
        return "deliverer-profile";
    }

    @GetMapping("/customer-profile")
    public String showCustomerProfile( Model model) {
        Logged cr = loggedService.getCurrentUser();
        if (cr != null) {

            User u = userService.findOneByUsername(cr.getUsername());
            if (u.getRole().equals(Role.CUSTOMER)) {

                model.addAttribute("user", u);

            } else {
                return "error-page";
            }

        } else {
            return "error-page";
        }
        return "customer-profile";
    }

    @GetMapping("/add-user")
    public String addUser(@ModelAttribute("check") String check, Model model) {
        boolean b = check.equals("true"); // "true" == "true" , "false" == "true"
        model.addAttribute("user", new User());
        model.addAttribute("check", b);
        System.out.println("h");
        return "add-user.html";
    }

    @GetMapping("/logout")
    public String logout() {
        loggedService.deleteAll();
        return "redirect:/login";
    }

    @GetMapping("/user-created")
    public String userCreated() {
        return "user-created.html";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user, BindingResult errors, Model model,RedirectAttributes rattrs)
            throws Exception {

        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setRole(user.getRole());
        user.setTelephone(user.getTelephone());
        User u = this.userService.registration(user);
        if (u == null)
        {
            rattrs.addFlashAttribute("check","true");  //neuspesna registracija
            return "redirect:/add-user";
        }
        //System.out.println("here:" + u.getFirstName() + " " + u.getLastName());

        return "redirect:user-created";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // logika ako je ulogovan da ne vidi ovu stranicu, ako je vec ulogovan ne moze se opet logovati
        if (loggedService.getCurrentUser() != null) {
            return "error-page";
        }

        model.addAttribute("loginDTO", new LoginDTO()); //<key, value> , loginDTO je kljuc
        model.addAttribute("check", false);

        //System.out.println("h");
        return "login.html";
    }


/*
    @GetMapping("/user")
    public String userHomePage(Model model) {
    	model.addAttribute("user", new User());
        return "user.html";
    }
*/

    @PostMapping("/checker")
    public String login(@ModelAttribute LoginDTO loginDTO, BindingResult errors, Model model){  //,RedirectAttributes rattrs) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        User user = userService.login(username, password);

        if (user != null) {
            Logged cr = new Logged(null, user.getUsername(), user.getRole());
            loggedService.save(cr);

            if (user.getRole().equals(Role.ADMIN)) {
                //rattrs.addFlashAttribute("user", user);

                return "redirect:/admin-profile";
            } else if (user.getRole().equals(Role.CUSTOMER)) {
                // rattrs.addFlashAttribute("user", user);
                return "redirect:/customer-profile";
            } else {

                // rattrs.addFlashAttribute("user", user);
                return "redirect:/deliverer-profile";
            }

        }
        model.addAttribute("check", true);
        return "login.html";
    }

    public String registration(User user) {
        userService.registration(user);

        return "redirect:/login";
    }

    @PostMapping("/changeRole")
    public String promeniRolu(@RequestParam(value = "id") int id) throws Exception {
        User user = userService.findOne((long) id);

        if(user.getRole() == Role.ADMIN)
            return "error-page";

        if(user.getRole() == Role.DELIVERER)
        {
            user.setRole(Role.CUSTOMER);
        }
        else
        {
            user.setRole(Role.DELIVERER);
        }
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("/my-profile")
    public String goToMyProfile(Model model)
    {
        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            model.addAttribute("check", true);
            return "login.html";
        }
        System.out.println(logged);
        User user = userService.findOne(logged.getUsername());

        if(user == null)
        {
            model.addAttribute("check", true);
            return "login.html";
        }

        if (user.getRole().equals(Role.ADMIN)) {
            //rattrs.addFlashAttribute("user", user);

            return "redirect:/admin-profile";
        } else if (user.getRole().equals(Role.CUSTOMER)) {
            // rattrs.addFlashAttribute("user", user);
            return "redirect:/customer-profile";
        } else if (user.getRole().equals(Role.DELIVERER)){

            // rattrs.addFlashAttribute("user", user);
            return "redirect:/deliverer-profile";
        }

        model.addAttribute("check", true);
        return "login.html";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {

        Logged logged = loggedService.findOne();

        System.out.println(logged);
        User user = userService.findOne(logged.getUsername());
        /*if(user != null)
        {
            model.addAttribute("users", userService.findOneByUsername(user.getUsername()));
            return "users";
        }*/
        if(user.getRole() == Role.ADMIN)
        {
            model.addAttribute("users", userService.findAll());
            return "users";
        }
        return "error-page";
        /*model.addAttribute("users", userService.findAll());
        return "users";*/
    }


    @GetMapping("/users/username/{username}")
    public String getUser(@PathVariable(name = "username") String username, Model model) {
        model.addAttribute("user", userService.findOneByUsername(username));

        return "admin-view-userr.html";
    }


    @GetMapping("/users/{id}")
    public String getUser(@PathVariable(name = "id") Long id,
                              Model model) {
        model.addAttribute("user", userService.findOne(id));

        return "admin-view-userr.html";
    }

}

