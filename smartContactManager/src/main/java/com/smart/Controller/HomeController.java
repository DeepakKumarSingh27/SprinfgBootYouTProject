package com.smart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
	@Autowired
	private UserRepository userRepository;
	
	//singn up controller
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "home-smart contact manager");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - smart contact manager");
		return "about";
	}
	
	//Singnup up controller
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - smart contact manager");
        model.addAttribute("user", new User());	
		
		return "signup";
	}
	
	// this is handler for registring user
	
	@RequestMapping(value = "/do_register",method = RequestMethod.POST )
	public String registerUser(@ModelAttribute("user") User user,
			@RequestParam(value = "agreement",defaultValue = "false" )
	          boolean agreement,Model model,HttpSession session) 
	{
		try {
			
			if(!agreement) 
			{
				System.out.println("You have not agreed the trrms and conditions");
				throw new Exception("You have not agreed the trrms and conditions");
				
			}
			
			user.setRole("Role_User");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			System.out.println("Agreement "+agreement);
			System.out.println("User "+user);
		    User result	= this.userRepository.save(user);
			
			model.addAttribute("user", new User());
			session.setAttribute("Successfully Registered !!","alert-success");
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",
					new message("something went wrong!!"+e.getMessage(),"alert-danger"));
			return "signup";

		}
		
	}

}

//@Autowired
//private UserRepository userRepository;
//
//@GetMapping("/test")
//@ResponseBody
//public String test() {
//	User user = new User();
//	user.setName("Deepak");
//	user.setEmail("coderDeepaksingh@gmail.com");
//	userRepository.save(user);
//	return "Working";
//}