package com.iamzakaria.dripp;

import com.iamzakaria.dripp.entity.User;
import com.iamzakaria.dripp.enums.Role;
import com.iamzakaria.dripp.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

	private final UserService userService;

	public Main(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createUserIfNotExist();
		createAdminIfNotExist();
	}

	private void createAdminIfNotExist() {
		if (userService.getUserByEmail("admin@drip.com") == null) {
			User admin = new User();
			admin.setFirstName("Zakaria");
			admin.setLastName("Admin");
			admin.setEmail("admin@drip.com");
			admin.setPassword("password");
			admin.setRole(Role.ROLE_ADMIN);
			userService.addUser(admin);

			System.out.println("Admin created !!");
		} else {
			System.out.println("Error");
		}
	}
	private void createUserIfNotExist() {
		if (userService.getUserByEmail("customer@drip.com") == null) {
			User user = new User();
			user.setFirstName("Zakaria");
			user.setLastName("Customer");
			user.setEmail("customer@drip.com");
			user.setPassword("12345");
			user.setRole(Role.ROLE_CUSTOMER);
			userService.addUser(user);

			System.out.println("Customer created too!!");
		} else {
			System.out.println("Error");
		}
	}

}