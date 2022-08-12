package com.example.batdongsan.controller;

import com.example.batdongsan.entity.Role;
import com.example.batdongsan.entity.User;
import com.example.batdongsan.repository.RoleRepository;
import com.example.batdongsan.service.UserService;
import com.example.batdongsan.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {


	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		return "redirect:/trang-chu";
	}

	@GetMapping("/register")
	public String newUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Đăng Ký Tài Khoản");
		return "dangky/user-form";
	}
	@PostMapping("/register/users/save")
	public String saveUser(User user,@RequestParam("image") MultipartFile multipartFile) throws IOException {
		Set<Role> listRoles = new HashSet<>();
		listRoles.add(roleRepository.findById(2).get());
		user.setRoles(listRoles);
		user.setEnabled(true);

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.saveUser(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if (user.getPhotos().isEmpty()) {
				user.setPhotos(null);
			}
			userService.saveUser(user);
		}
		// userService.saveUser(user);
		return "redirect:/login";
	}

}
