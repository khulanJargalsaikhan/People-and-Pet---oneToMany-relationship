package com.example.springboot_exercise_401;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    OwnerRepository ownerRepository;

    public void run(String...args){
        User user = new User("bart", "bart@domain.com", "bart", "Bart", "Simpson", true);
        Role userRole = new Role("bart", "ROLE_USER");

        userRepository.save(user);
        roleRepository.save(userRole);

        User admin = new User("super", "super@domain.com", "super", "Super", "Hero", true);
        Role adminRole1 = new Role("super", "ROLE_ADMIN");
        Role adminRole2 = new Role("super", "ROLE_USER");

        userRepository.save(admin);
        roleRepository.save(adminRole1);
        roleRepository.save(adminRole2);

        // pets collection
        Set<Pet> pets = new HashSet<Pet>();


        // First let’s create a owner
        Owner owner = new Owner();
        owner.setName("Stephen");
        owner.setAddress("Arlington, VA-22202");

        // Now let’s create a pet
        Pet pet = new Pet();
        pet.setName("Bella");
        pet.setAge(2);
        pet.setOwner(owner);
        // Add the pet to an empty list
        pets.add(pet);

        // Now let’s create another pet
        pet = new Pet();
        pet.setName("Angel");
        pet.setAge(5);
        pet.setOwner(owner);
        // Add the pet to the list
        pets.add(pet);

        // Add the list of pets to the owner’s pet list
        owner.setPets(pets);

        // Save the owner to the database
        ownerRepository.save(owner);


    }



}
