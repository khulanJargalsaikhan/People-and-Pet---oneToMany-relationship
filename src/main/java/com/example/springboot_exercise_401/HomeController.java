package com.example.springboot_exercise_401;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;


    @RequestMapping("/")
    public String homepage(Model model){

    // Grab all the directors from the database and send them to
    // the template
        model.addAttribute("directors", directorRepository.findAll());
        return "index";
    }

    @GetMapping("/addDirector")
    public String addDirector(Model model){
        model.addAttribute("director", new Director());
        return "directorForm";
    }

    @RequestMapping("/updateDirector/{id}")
    public String updateDirector(@PathVariable("id") long id, Model model){
        model.addAttribute("director", directorRepository.findById(id).get());
        return "directorForm";
    }

    @RequestMapping("/deleteDirector/{id}")
    public String deleteDirector(@PathVariable("id") long id){
        directorRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processDirector")
    public String processDirector(@ModelAttribute Director director){
        directorRepository.save(director);
        return "redirect:/";
    }



    @GetMapping("/addMovie")
    public String addMovie(Model model){
        model.addAttribute("movie", new Movie());
        model.addAttribute("directors", directorRepository.findAll());
        return "movieForm";
    }


    @RequestMapping("/updateMovie/{id}")
    public String updateMovie(@PathVariable("id") long id, Model model){
        model.addAttribute("movie", movieRepository.findById(id).get());
        model.addAttribute("directors", directorRepository.findAll());
        return "movieForm";
    }

    @RequestMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable("id") long id){
        movieRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processMovie")
    public String processMovie(@ModelAttribute Movie movie){
        movieRepository.save(movie);
        return "redirect:/";
    }






    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/processregister")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            user.clearPassword();
            model.addAttribute("user", user);
            return "register";
        }else{
            model.addAttribute("user", user);
            model.addAttribute("message", "New user account created.");
            user.setEnabled(true);
            userRepository.save(user);

            Role role = new Role(user.getUsername(), "ROLE_USER");
            roleRepository.save(role);
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/login?logout=true";
    }

    //principal is currently logged in
    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }





}