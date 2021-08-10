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
    DirectorRepository directorRepository;

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

        // movies collection
        Set<Movie> movies = new HashSet<Movie>();


        // First let’s create a director
        Director director = new Director();
        director.setName("Stephen Bullock");
        director.setGenre("Sci Fi");

        // Now let’s create a movie
        Movie movie = new Movie();
        movie.setTitle("Star Movie");
        movie.setYear(2017);
        movie.setDescription("About Stars...");
        movie.setDirector(director);
        // Add the movie to an empty list
        movies.add(movie);

        // Now let’s create another movie
        movie = new Movie();
        movie.setTitle("DeathStar Ewoks");
        movie.setYear(2011);
        movie.setDescription("About Ewoks on the DeathStar...");
        movie.setDirector(director);
        movies.add(movie);

        // Add the list of movies to the director’s movie list
        director.setMovies(movies);

        // Save the director to the database
        directorRepository.save(director);


    }



}
