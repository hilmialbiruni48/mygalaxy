package myapp.controller;

import myapp.model.RoleModel;
import myapp.model.UserModel;
import myapp.service.RoleService;
import myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/add")
    private String addUserFormPage(Model model){
        UserModel user = new UserModel();
        List<RoleModel> listRole = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRole", listRole);
        return "form-add-user";

    }

    @PostMapping(value = "/add")
    private String addUserSubmit(@ModelAttribute UserModel user, Model model){
        user.setIsSso(false);
        userService.addUser(user);
        model.addAttribute("user", user);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser (@PathVariable String id, Model model) {

        UserModel user = userService.getUserById(id);
        userService.deleteUser(user);
        model.addAttribute( "id",user.getId());
        return "delete-user";
    }

    @GetMapping("/viewall")
    public String listUser(Model model) {
        List<UserModel> listUser = userService.getListUser();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String username = user.getUsername();
        UserModel userModel = userService.getUserByUsername(username);

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", userModel);

        return "viewall-user";
    }
    @PostMapping("/update-password")
    public String updatePassword(@ModelAttribute UserModel userModel, String newPassword, String confPassword, Model model){
        UserModel user = userService.getUserByUsername(userModel.getUsername());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(userModel.getPassword(), user.getPassword())){
            if (newPassword.equals(confPassword)){
                user.setPassword(newPassword);
                userService.addUser(user);
                return "sukses-update-password";
            } else {
                model.addAttribute("message", "password yang dikonfirmasi tidak sama. Ulangi!");
            }
        }else {
            model.addAttribute("message", "password lama invalid. Ulangi!");
        }
        return "form-update-password";
    }
    @GetMapping("/update-password")
    public String getUpdatePassword(){
        return "form-update-password";
    }
}
