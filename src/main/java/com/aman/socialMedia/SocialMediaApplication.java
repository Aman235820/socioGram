package com.aman.socialMedia;

import com.aman.socialMedia.Entities.Role;
import com.aman.socialMedia.Repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableCaching
public class SocialMediaApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(SocialMediaApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Autowired
	private RoleRepo roleRepo;


	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));

		try{

			Role role = new Role();
			role.setRoleId(EnumClass.ADMIN_USER);
			role.setRoleName("ADMIN_USER");

			Role role1= new Role();
			role1.setRoleId(EnumClass.NORMAL_USER);
			role1.setRoleName("NORMAL_USER");

			List<Role> roles = List.of(role,role1);

			List<Role> result =  this.roleRepo.saveAll(roles);

			result.forEach(r->{
				System.out.println(r.getRoleName() + r.getRoleId());
			});

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
