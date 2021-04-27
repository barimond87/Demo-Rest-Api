package de.arimond.demo.demorestapi.controller;

import de.arimond.demo.demorestapi.controller.annotations.POST;
import de.arimond.demo.demorestapi.persistence.dto.UserDto;
import de.arimond.demo.demorestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ben Arimond
 */

@RestController
@RequestMapping(value = "/user")
@Secured({"ROLE_ADMIN"})
public class UserController extends BaseController{

    public final UserService userService;


    @Autowired
    public UserController(MessageSource messageSource, UserService userService) {
        super(messageSource);
        this.userService = userService;
    }


    @POST(path = "/create")
    public @ResponseBody ResponseEntity<String> postKondition(
            @RequestBody UserDto request) {
        try{
            userService.createUser(request);
            return ResponseEntity.ok("User created");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Failed to create User - Message: "+e.getMessage());
        }
    }

}
