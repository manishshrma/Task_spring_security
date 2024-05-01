package com.eminence.innovation.task.controller;

import java.util.List;

import com.eminence.innovation.task.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eminence.innovation.task.model.User;
import com.eminence.innovation.task.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    private JwtService jwtService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<Object> authenticatedUser(HttpServletRequest request) {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

         if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
                throw new AccessDeniedException("You are not authorized");
          }

         User currentUser = (User) authentication.getPrincipal();
         long exp_time = jwtService.getExpirationTime();
         currentUser.setExpirationTime(exp_time);
         return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}
