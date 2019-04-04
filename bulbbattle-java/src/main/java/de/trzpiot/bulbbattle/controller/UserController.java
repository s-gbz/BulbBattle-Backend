package de.trzpiot.bulbbattle.controller;

import de.trzpiot.bulbbattle.exception.RegistrationException;
import de.trzpiot.bulbbattle.exception.UserNotFoundException;
import de.trzpiot.bulbbattle.model.UserModel;
import de.trzpiot.bulbbattle.service.NativeService;
import de.trzpiot.bulbbattle.domain.User;
import de.trzpiot.bulbbattle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        Optional<User> user = userService.get(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody UserModel model) {

        if (userService.findByName(model.getName()).isPresent()) {
            throw new RegistrationException(model.getName());
        }

        return new ResponseEntity<>(userService.register(model.getName()), HttpStatus.CREATED);
    }
}
