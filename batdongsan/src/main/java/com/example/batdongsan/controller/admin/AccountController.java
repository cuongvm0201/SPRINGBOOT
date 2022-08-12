package com.example.batdongsan.controller.admin;


import com.example.batdongsan.entity.User;
import com.example.batdongsan.security.BDSUserDetails;
import com.example.batdongsan.service.EmailService;
import com.example.batdongsan.service.UserService;
import com.example.batdongsan.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountController {

	@Autowired
	private UserService service;

	@Autowired
	private EmailService mailService;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal BDSUserDetails loggedUser,
							  Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		model.addAttribute("user", user);
		
		return "users/account_form";
		
	}

	@GetMapping("/account/quen-mat-khau")
	public String viewQuenMatKhau(){
		return "users/quen-mat-khau";
	}
	@GetMapping("/account/quen-mat-khau/sendemail")
	public String sendEmailQuenMatKhau(@RequestParam String email){
		mailService.sendEmail(email);
		return "redirect:/login";
	}

	@GetMapping("/account/quen-mat-khau/detail/{email}")
	public String viewDetailsQuenMatKhau(@PathVariable String email, Model model) {
		User user = service.getByEmail(email);
		model.addAttribute("user", user);
		return "users/account_form";

	}
	
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal BDSUserDetails loggedUser,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.updateAccount(user);
			
			String uploadDir = "user-photos/" + savedUser.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			service.updateAccount(user);
		}
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
		
		return "redirect:/account";
	}	
}