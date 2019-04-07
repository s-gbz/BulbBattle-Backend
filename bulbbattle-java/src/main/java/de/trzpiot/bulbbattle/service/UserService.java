package de.trzpiot.bulbbattle.service;

import de.trzpiot.bulbbattle.domain.User;
import de.trzpiot.bulbbattle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long register(String name) {
        User entity = new User();
        entity.setName(name);
        return userRepository.save(entity).getId();
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
