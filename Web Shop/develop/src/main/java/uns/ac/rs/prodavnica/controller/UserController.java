package uns.ac.rs.prodavnica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uns.ac.rs.prodavnica.dto.LoginDTO;
import uns.ac.rs.prodavnica.dto.SearchDTO;
import uns.ac.rs.prodavnica.entity.*;
import uns.ac.rs.prodavnica.service.*;

import java.util.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DelivererService delivererService;


    @Autowired
    private CustomerService customerService;

    /*@Autowired
    private BCryptPasswordEncoder encoder;*/

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CartService cartService;

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

    private Set<Article> articlesOnSale;

    @PostMapping("/set-on-sale")
    public String staviNaProdaju(@RequestParam(value = "id") int id) throws Exception {
        Article article = articleService.findOne((long) id);

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            return "error-page";
        }

        User user = userService.findOne((logged.getUsername()));

        //Customer customer = customerService.findOne(user.getId());

        if(user.getRole() == Role.ADMIN)
        {
            if(article.getOnSale())
            {
                article.setOnSale(false);
            }
            else
                article.setOnSale(true);

            articleService.update(article);

            //articlesOnSale.add(article);
            return "redirect:/articles";
        }
        return "error-page";
    }


    @PostMapping("/delete-article")
    public String deleteArticle(@RequestParam(value = "id") int id) {
        Article article = articleService.findOne((long) id);

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            return "error-page";
        }

        User user = userService.findOne((logged.getUsername()));

        //Customer customer = customerService.findOne(user.getId());

        if(user.getRole() == Role.ADMIN)
        {
            articleService.delete((long)id);

            //articlesOnSale.add(article);
            return "redirect:/articles";
        }
        return "error-page";
    }

    @PostMapping("/cart")
    public String pogledajKorup (@RequestParam(value = "id") int id, Model model) throws Exception {
        Cart cart = cartService.findOne((long)id);

        if(cart == null)
            return "error-page";

        model.addAttribute("cart", cart);
        model.addAttribute("articles",cart.getArticles());

        return "cart";
    }

    @GetMapping("/sale")
    public String pregledajProdaju(Model model) {
        model.addAttribute("articles", articleService.findAllOnSale());

        return "sale";
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
                model.addAttribute("carts",cartService.findAll());

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
                model.addAttribute("favArticles",customerService.findOne(u.getId()).getFavoriteArticles());
                model.addAttribute("cartArticles",customerService.findOne(u.getId()).getArticlesInCart());
                int price = 0;
                for(Article art : customerService.findOne(u.getId()).getArticlesInCart())
                {
                    price += art.getPrice();
                    if(art.getOnSale())
                        price -= art.getPrice() * 0.1;
                }
                model.addAttribute("price",price);
                model.addAttribute("carts", customerService.findOne(u.getId()).getCarts());

            } else {
                return "error-page";
            }

        } else {
            return "error-page";
        }
        return "customer-profile";
    }

    @GetMapping("/add-article")
    public String addArticle(@ModelAttribute("check") String check, Model model) {
        boolean b = check.equals("true");
        model.addAttribute("article", new Article());
        model.addAttribute("check", b);

        return "add-new-article.html";
    }

    @GetMapping("/add-user")
    public String addUser(@ModelAttribute("check") String check, Model model) {
        boolean b = check.equals("true");
        model.addAttribute("user", new User());
        model.addAttribute("check", b);

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

    @GetMapping("/article-created")
    public String articleCreated() {
        return "article-created.html";
    }

    @GetMapping("/izvestaj/{id}")
    public String izvestaj(@PathVariable(name = "id") int id, Model model)
    {
        int days = id;
        model.addAttribute("days",days);
        Logged logged = loggedService.findOne();

        User user = userService.findOne(logged.getUsername());
        if(user.getRole() == Role.ADMIN)
        {
            List<Cart> carts = cartService.findAll();
            List<Cart> deliveredCarts = new ArrayList<Cart>();
            int zarada = 0;
            int brOtkazanih = 0;

            Date date = new Date();

            for(Cart cart : carts)
            {
                if((date.getTime() - cart.getDatetime().getTime() <=  (days * 24 * 60 * 60 * 1000)))
                {
                    zarada += cart.getPrice();
                    if(cart.getStatus() == CartStatus.DELIVERED)
                    {
                        deliveredCarts.add(cart);
                    }
                    if(cart.getStatus() == CartStatus.CANCELED)
                    {
                        brOtkazanih++;
                    }
                }
            }
            model.addAttribute("deliveredCarts", deliveredCarts);
            model.addAttribute("brOtkazanih", brOtkazanih);
            model.addAttribute("zarada", zarada);

            return "izvestaj";
        }
        return "error-page";
    }

    @PostMapping("/searchArticles")
    public String searchArticles(@ModelAttribute SearchDTO searchDTO, Model model)
    {
        List<Article> articles = articleService.findAll();

        List<Article> searchArticles = new ArrayList<>();

        if(searchDTO.getParam1() == "" && searchDTO.getParam2() == "")
        {
            searchArticles.addAll(articles);
        }
        else
        {
            for(Article article : articles)
            {
                if((article.getName().toUpperCase().contains(searchDTO.getParam1().toUpperCase()) && searchDTO.getParam1() != "")  || (article.getName().toUpperCase().contains(searchDTO.getParam2().toUpperCase()) && searchDTO.getParam2() != "")){
                    searchArticles.add(article);
                    continue;
                }
                if((article.getDescription().toUpperCase().contains(searchDTO.getParam1().toUpperCase()) && searchDTO.getParam1() != "")  || (article.getDescription().toUpperCase().contains(searchDTO.getParam2().toUpperCase()) && searchDTO.getParam2() != "")){
                    searchArticles.add(article);
                    continue;
                }
                if((article.getCategory().toString().toUpperCase().contains(searchDTO.getParam1().toUpperCase()) && searchDTO.getParam1() != "")  || (article.getCategory().toString().toUpperCase().contains(searchDTO.getParam2().toUpperCase()) && searchDTO.getParam2() != "")){
                    searchArticles.add(article);
                    continue;
                }
                if((article.getPrice().toString().toUpperCase().contains(searchDTO.getParam1().toUpperCase()) && searchDTO.getParam1() != "")  || (article.getPrice().toString().toUpperCase().contains(searchDTO.getParam2().toUpperCase()) && searchDTO.getParam2() != "")){
                    searchArticles.add(article);
                    continue;
                }
            }
        }


        if(searchDTO.getSortByPrice() == 1)
        {
            for(int i = 0; i < searchArticles.size(); i++)
            {
                for(int j = i + 1; j < searchArticles.size(); j++)
                {
                    if(searchArticles.get(i).getPrice() < searchArticles.get(j).getPrice())
                    {
                        long id = searchArticles.get(i).getId();
                        searchArticles.set(i, searchArticles.get(j));
                        searchArticles.set(j, articleService.findOne(id));
                    }
                }

            }
        }
        else if(searchDTO.getSortByPrice() == -1)
        {
            for(int i = 0; i < searchArticles.size(); i++)
            {
                for(int j = i + 1; j < searchArticles.size(); j++)
                {
                    if(searchArticles.get(i).getPrice() > searchArticles.get(j).getPrice())
                    {
                        long id = searchArticles.get(i).getId();
                        searchArticles.set(i, searchArticles.get(j));
                        searchArticles.set(j, articleService.findOne(id));
                    }
                }

            }
        }

        model.addAttribute("articles", searchArticles);

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            model.addAttribute("user", null);
        }
        else
        {
            User user = userService.findOne(logged.getUsername());

            model.addAttribute("user", user);
        }

        return "articles";
    }

    @PostMapping("/save-article")
    public String saveArticle(@ModelAttribute Article article, BindingResult errors, Model model, RedirectAttributes rattrs)
            throws Exception {

        article.setName(article.getName());
        article.setDescription(article.getDescription());
        article.setPrice(article.getPrice());
        article.setCategory(article.getCategory());

        Article a = this.articleService.registration(article);
        if (a == null)
        {
            rattrs.addFlashAttribute("check","true");  //neuspesna registracija
            return "redirect:/add-article";
        }

        return "redirect:article-created";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user, BindingResult errors, Model model, RedirectAttributes rattrs)
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

        userService.delete(u.getId());

        if(user.getRole().toString() == "DELIVERER")
        {
            delivererService.addNew(user);
        }
        else
        {
            customerService.addNew(user);
        }


        return "redirect:user-created";
    }

    /*@GetMapping("/addDel")
    public String bla()
    {
        delivererService.addNew((long)5);
        return "error-page";
    }*/

    @GetMapping("/login")
    public String login(Model model) {
        if (loggedService.getCurrentUser() != null) {
            return "error-page";
        }

        model.addAttribute("loginDTO", new LoginDTO());
        model.addAttribute("check", false);

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
        /*StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret");
        String result = encoder.encode("myPassword");
        assertTrue(encoder.matches("myPassword", result));*/
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

    @PostMapping("/add-to-favorite")
    public String dodajUOmiljene(@RequestParam(value = "id") int id) throws Exception {
        Article article = articleService.findOne((long) id);

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            return "error-page";
        }

        User user = userService.findOne((logged.getUsername()));

        //Customer customer = customerService.findOne(user.getId());

        if(user.getRole() == Role.CUSTOMER)
        {
            Customer customer = customerService.findOne(user.getId());

            Set<Article> favArticles = customer.getFavoriteArticles();

            favArticles.add(article);

            List<User> favUser = article.getFavUser();

            favUser.add(user);

            article.setFavUser(favUser);

            customer.setFavoriteArticles(favArticles);

            articleService.update(article);
            customerService.update(customer);
            //model.addAttribute("users", userService.findAll());
            return "redirect:/articles";
        }
        return "error-page";

    }

    @PostMapping("/add-to-cart")
    public String dodajUKorpu(@RequestParam(value = "id") int id) throws Exception {
        Article article = articleService.findOne((long) id);

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            return "error-page";
        }

        User user = userService.findOne(logged.getUsername());

        if(user.getRole() == Role.CUSTOMER)
        {
            Customer customer = customerService.findOne(user.getId());

            List<Article> cartArticles = customer.getArticlesInCart();

            cartArticles.add(article);

            List<User> users = article.getCart();

            users.add(user);

            article.setCart(users);

            customer.setArticlesInCart(cartArticles);

            articleService.update(article);
            customerService.update(customer);

            /*List<Cart> customerCarts = customer.getCarts(); //jako ruzan deo koda

            Cart customerCart;

            if(customerCarts.size() == 0)
            {
                customerCart = new Cart();
            }
            else
            {
                customerCart = customerCarts.get((customer.getCarts().size() - 1));
            }

            List<Article> articlesInCart = customerCart.getArticles();
            articlesInCart.add(article);
            customerCart.setArticles(articlesInCart);

            if(customerCarts.size() > 0)
            {
                customerCarts.remove(customer.getCarts().size() - 1);
            }
            customerCarts.add(customerCart);

            articleService.update(article);
            cartService.update(customerCarts.get((customer.getCarts().size() - 1)));
            customerService.update(customer);*/

            //model.addAttribute("users", userService.findAll());
            return "redirect:/articles";
        }
        return "error-page";

    }

    @PostMapping("/delete-from-favorite")
    public String izbaciIzOmiljenih(@RequestParam(value = "id") int id) throws Exception {

        Logged logged = loggedService.findOne();

        User user = userService.findOne(logged.getUsername());

        Customer customer = customerService.findOne(user.getId());

        Set<Article> favArticles = customer.getFavoriteArticles();
        favArticles.remove(articleService.findOne((long)id));
        customer.setFavoriteArticles(favArticles);

        Article article = articleService.findOne((long)id);

        List<User> favUsers = article.getFavUser();
        favUsers.remove(user);
        article.setFavUser(favUsers);

        articleService.update(article);
        customerService.update(customer);

        return "redirect:/my-profile";
    }

    @GetMapping("/login-logout")
    public String loginLogout(Model model) {

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            model.addAttribute("check", true);
            return "redirect:login";
        }
        return "redirect:logout";
    }

    @PostMapping("/buy-cart")
    public String kupiKorpu(@RequestParam(value = "id") int id) throws Exception {
        Logged logged = loggedService.findOne();

        User user = userService.findOne(logged.getUsername());

        Customer customer = customerService.findOne(user.getId());

        List<Article> cartArticles = customer.getArticlesInCart();
        List<Article> copy2 = new ArrayList<>();
        copy2.addAll(cartArticles);

        for(Article article : copy2)
        {
            List<User> cart2 = article.getCart();
            cart2.remove(user);
            article.setCart(cart2);

            articleService.update(article);
        }

        List<Article>  copy = new ArrayList<>();
        copy.addAll(cartArticles);

        customer.deleteArticlesInCart();

        customerService.update(customer);

        //

        Cart cart = new Cart();
        cart.setArticles(copy);
        cart.setDatetime(new Date());
        cart.setStatus(CartStatus.BOUGHT);
        cart.setCustomer(customer);
        customer.addCarts(cart);

        cartService.addNew(cart);


        List<Article> articleList = new ArrayList<>();
        articleList.addAll(cart.getArticles());

        for(Article article : articleList)
        {
            article.addCart(cart);

            articleService.update(article);
            cartService.update(cart);
        }

        cart.generatePrice();

        customerService.update(customer);
        cartService.update(cart);
/*

        cartArticles.remove(articleService.findOne((long)id));
        customer.setArticlesInCart(cartArticles);

        List<User> cart2 = article.getCart();
        cart2.remove(user);
        article.setCart(cart2);

        articleService.update(article);
        customerService.update(customer);

        //TREBA SVE PONOVO


        List<Article> articlesInCart = customer.getArticlesInCart();
        Cart cart = new Cart();
        List<Article>  copy = new ArrayList<>();
        copy.addAll(articlesInCart);

        cart.setArticles(copy);
        cart.setCustomer(customer);
        cart.setStatus(CartStatus.BOUGHT);
        cart.setDatetime(new Date());


        List<Cart> carts = customer.getCarts();
        carts.add(cart);
        customer.setCarts(carts);

        //List<Article> s = new ArrayList<>();
        //articlesInCart.clear();
        //customer.setArticlesInCart(articlesInCart);
       //customer.setArticlesInCart(s);
        customer.deleteArticlesInCart();

        //cartService.addNew(cart);
        //cartService.update(cart);

        for(Article article : articlesInCart)
        {
            List<Article> favArticles = customer.getArticlesInCart();
            favArticles.remove(articleService.findOne((long)id));
            customer.setArticlesInCart(favArticles);

            List<User> cart2 = article.getCart();
            cart2.remove(user);
            article.setCart(cart2);

            articleService.update(article);
            customerService.update(customer);

            List<User> users = article.getCart();
            users.remove(user.getId());
            article.setCart(users);


            carts = article.getCarts();
            carts.add(cart);
            article.setCarts(carts);
            articleService.update(article);
        }

        cartService.addNew(cart);
        customerService.update(customer);*/

        return "redirect:/my-profile";
    }

    @PostMapping("/delete-from-cart")
    public String izbaciIzKorpe(@RequestParam(value = "id") int id) throws Exception {

        Logged logged = loggedService.findOne();

        User user = userService.findOne(logged.getUsername());

        Customer customer = customerService.findOne(user.getId());

        List<Article> favArticles = customer.getArticlesInCart();
        favArticles.remove(articleService.findOne((long)id));
        customer.setArticlesInCart(favArticles);

        Article article = articleService.findOne((long)id);

        List<User> cart = article.getCart();
        cart.remove(user);
        article.setCart(cart);

        articleService.update(article);
        customerService.update(customer);

        return "redirect:/my-profile";
    }

    @PostMapping("/changeRole")
    public String promeniRolu(@RequestParam(value = "id") int id) throws Exception {
        User user = userService.findOne((long) id);

        if(user.getRole() == Role.ADMIN)
            return "error-page";

        userService.delete((long)id);

        if(user.getRole() == Role.DELIVERER)
        {
            user.setRole(Role.CUSTOMER);
            customerService.addNew(user);
        }
        else
        {
            user.setRole(Role.DELIVERER);
            delivererService.addNew(user);
        }

        //userService.update(user);
        return "redirect:/users";
    }

    @PostMapping("/changeStatusToShipping")
    public String promeniStatusShipping(@RequestParam(value = "id") int id) throws Exception {
        /*User user = userService.findOne((long) id);

        if(user.getRole() == Role.ADMIN || user.getRole() == Role.CUSTOMER)
            return "error-page";*/

        Cart cart = cartService.findOne((long)id);

        cart.setStatus(CartStatus.SHIPPING);

        cartService.update(cart);

        //userService.update(user);
        return "redirect:/my-profile";
    }

    @PostMapping("/changeStatusToCanceled")
    public String promeniStatusCanceled(@RequestParam(value = "id") int id) throws Exception {
       /* User user = userService.findOne((long) id);

        if(user.getRole() == Role.ADMIN || user.getRole() == Role.DELIVERER)
            return "error-page";*/

        Cart cart = cartService.findOne((long)id);

        cart.setStatus(CartStatus.CANCELED);

        cartService.update(cart);

        //userService.update(user);
        return "redirect:/my-profile";
    }

    @PostMapping("/changeStatusToDelivered")
    public String promeniStatusDelivered(@RequestParam(value = "id") int id) throws Exception {
        /*User user = userService.findOne((long) id);

        if(user.getRole() == Role.ADMIN || user.getRole() == Role.CUSTOMER)
            return "error-page";*/

        Cart cart = cartService.findOne((long)id);

        cart.setStatus(CartStatus.DELIVERED);

        cartService.update(cart);

        //userService.update(user);
        return "redirect:/my-profile";
    }

    @GetMapping("/back")
    public String goBack()
    {
        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            return "redirect:/login";
        }
        return "redirect:/my-profile";
    }

    @GetMapping("/my-profile")
    public String goToMyProfile(Model model)
    {
        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            model.addAttribute("check", true);
            return "redirect:login";
        }

        //System.out.println(logged);
        User user = userService.findOne(logged.getUsername());

        if(user == null)
        {
            model.addAttribute("check", true);
            return "redirect:login";
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

    @GetMapping("/articles")
    public String getArticles(Model model) {

        List<Article> articles = articleService.findAll();
        model.addAttribute("articles", articles);
        model.addAttribute("searchDTO", new SearchDTO());

        Logged logged = loggedService.findOne();

        if(logged == null)
        {
            model.addAttribute("user", null);
        }
        else
        {
            User user = userService.findOne(logged.getUsername());

            model.addAttribute("user", user);
        }

        return "articles";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {

        Logged logged = loggedService.findOne();

       //System.out.println(logged);
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

