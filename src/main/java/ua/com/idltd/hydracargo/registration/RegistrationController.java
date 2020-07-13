package ua.com.idltd.hydracargo.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.po_user.entity.Po_User;
import ua.com.idltd.hydracargo.po_user.repository.Po_UserRepository;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Po_UserRepository po_userRepository;

    public RegistrationController(Po_UserRepository po_userRepository) {
        this.po_userRepository = po_userRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(ModelAndView model
    ){
        model.setViewName("/registration/cover");
        return model;
    }

    @RequestMapping(value = {"/newuser"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> newuser(@RequestParam(value="username") String username,
                                     @RequestParam(value="password") String password,
                                     @RequestParam(value="email") String email,
                                     @RequestParam(value="phone") String phone,
                                     @RequestParam(value="firstname") String firstname,
                                     @RequestParam(value="lastname") String lastname,
                                     @RequestParam(value="country") String country,
                                     @RequestParam(value="zipcode") String zipcode,
                                     @RequestParam(value="city") String city,
                                     @RequestParam(value="street") String street,
                                     @RequestParam(value="house") String house,
                                     @RequestParam(value="apartment") String apartment
    ){
        ResponseEntity<?> result;
        try {
            Po_User po_user = new Po_User();
            po_user.po_username=username;
            po_user.po_password=password;
            po_user.po_encriptpassword=bCryptPasswordEncoder.encode(password);
            po_user.po_email=email;
            po_user.po_phone=phone;
            po_user.po_firstname=firstname;
            po_user.po_lastname=lastname;
            po_user.po_country=country;
            po_user.po_zipcode=zipcode;
            po_user.po_city=city;
            po_user.po_street=street;
            po_user.po_house=house;
            po_user.po_apartment=apartment;
            po_userRepository.save(po_user);
            result = ResponseEntity.ok(username);
        } catch (Exception e) {
            result = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return result;
    }

}
