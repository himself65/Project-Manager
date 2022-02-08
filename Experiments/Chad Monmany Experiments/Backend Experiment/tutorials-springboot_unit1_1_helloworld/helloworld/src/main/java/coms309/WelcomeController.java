package coms309;

import coms309.Dogs.Dog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello From Chad Monmany";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/dog")
    public List<Dog> dog() {
        return List.of(
                new Dog(
                        "Boomer",
                        2,
                        "Tracy Stevens",
                        "tStevens@gmail.com"

                )
        );
    }
}
