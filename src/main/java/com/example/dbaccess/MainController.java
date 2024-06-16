package com.example.dbaccess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    private final Logger LOGGER = LogManager.getLogger();
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    //test
    @GetMapping("/userlist")
    public String listaUser(Model model) {
        model.addAttribute("Users", userRepository.findAll());
        return "userlist";
    }
    @GetMapping("/deleteuser")
    public String usunUser(@RequestParam(name="id", required=true) int id,
                                         Model model) {
        userRepository.deleteById(id);
        return "deladd";
    }
  @PutMapping("/edytujuser")
  public String editUser(@RequestParam(name="uid", required=true) int id,
                         @RequestParam(name="uname", required=true) String name,
                         @RequestParam(name="uemail", required=true) String email,
                         Model model) {
      Optional<User> user =  userRepository.findById(id);
      if(!user.isEmpty()) {
          user.get().setName(name);
          user.get().setEmail(email);
          userRepository.save(user.get());
      }else{
          LOGGER.error("Brak id");
      }
      return "deladd";
  }
    @PostMapping(path="/adduser") // Map ONLY POST Requests
    public String addNewUserTest (@RequestParam String username
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        System.out.println("dodaj");
        User n = new User();
        n.setName(username);
        n.setEmail(email);
        userRepository.save(n);
        return "deladd";
    }
}