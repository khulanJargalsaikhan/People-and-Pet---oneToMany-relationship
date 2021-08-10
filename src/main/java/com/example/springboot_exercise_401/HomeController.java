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
    OwnerRepository ownerRepository;

    @Autowired
    PetRepository petRepository;


    @RequestMapping("/")
    public String homepage(Model model){
        // Grab all the owners from the database and send them to the template
        model.addAttribute("owners", ownerRepository.findAll());
        return "index";
    }

    @GetMapping("/addOwner")
    public String addOwner(Model model){
        model.addAttribute("owner", new Owner());
        return "ownerForm";
    }

    @RequestMapping("/updateOwner/{id}")
    public String updateOwner(@PathVariable("id") long id, Model model){
        model.addAttribute("owner", ownerRepository.findById(id).get());
        return "ownerForm";
    }

    @RequestMapping("/deleteOwner/{id}")
    public String deleteOwner(@PathVariable("id") long id){
        ownerRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processOwner")
    public String processOwner(@ModelAttribute Owner owner){
        ownerRepository.save(owner);
        return "redirect:/";
    }



    @GetMapping("/addPet")
    public String addPet(Model model){
        model.addAttribute("pet", new Pet());
        model.addAttribute("owners", ownerRepository.findAll());
        return "petForm";
    }


    @RequestMapping("/updatePet/{id}")
    public String updatePet(@PathVariable("id") long id, Model model){
        model.addAttribute("pet", petRepository.findById(id).get());
        model.addAttribute("owners", ownerRepository.findAll());
        return "petForm";
    }

    @RequestMapping("/deletePet/{id}")
    public String deletePet(@PathVariable("id") long id){
        petRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/processPet")
    public String processMovie(@ModelAttribute Pet pet){
        petRepository.save(pet);
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