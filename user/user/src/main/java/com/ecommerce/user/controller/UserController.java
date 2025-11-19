package com.ecommerce.user.controller;


import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    @Autowired
    private com.ecommerce.user.service.UserService userService;
//    private static final Logger log= LoggerFactory.getLogger(UserController.class);


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        System.out.println("REQUEST RECEIVED");
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id)
    {
        log.info("Request recieved for user:{}",id);
        log.trace("This is TRACE level-very detailed logs");
        log.debug("This is DEBUG level-used for development debugging");
        log.info("THis is Info level-General System Information");
        log.warn("This is WARN level -Something might be wrong");
        log.error("This is ERROR level - SOmething failed");
       return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);
        return ResponseEntity.ok(" User addes sucessfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest)
    {
        boolean updated= userService.updateUser(id,userRequest);
       if(updated)
       {
           return ResponseEntity.ok("User updated sucessfully");
       }
       return ResponseEntity.notFound().build();
    }



}
