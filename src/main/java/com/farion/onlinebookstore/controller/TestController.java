package com.farion.onlinebookstore.controller;

import com.farion.onlinebookstore.entity.User;
import com.farion.onlinebookstore.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final UserRepository userRepository;

    @GetMapping
    public void test() {
        User user = userRepository.findByEmail("sasha@gmail.com").get();
        System.out.println(user);
    }
}
