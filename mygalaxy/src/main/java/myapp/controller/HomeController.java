package myapp.controller;

import myapp.model.UserModel;
import myapp.security.xml.Attributes;
import myapp.security.xml.ServiceResponse;
import myapp.service.RoleService;
import myapp.service.UserService;
import myapp.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {
    @RequestMapping("/")
    private String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    private WebClient webClient = WebClient.builder().build();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    ServerProperties serverProperties;

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request
    ){
        ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN
                )
        ).retrieve().bodyToMono(ServiceResponse.class).block();

        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();

        UserModel user = userService.getUserByUsername(username);

        if(user == null){
            user = new UserModel();
            user.setEmail(username + "@ui.ac.id");
            user.setNama(attributes.getNama());
            user.setPassword("dehiksa123");
            user.setUsername(username);
            user.setIsSso(true);
            user.setRole(roleService.getById(1L));
            userService.addUser(user);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "dehiksa123");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO(){
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal){
        UserModel user = userService.getUserByUsername(principal.getName());
        if (user.getIsSso() == false){
            return new ModelAndView("redirect:/logout");

        }
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }
}
