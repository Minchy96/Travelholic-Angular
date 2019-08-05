package pmf.mina.bjelica.travelholic.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import pmf.mina.bjelica.travelholic.model.dto.EmailDto;
import pmf.mina.bjelica.travelholic.model.dto.LoginDto;
import pmf.mina.bjelica.travelholic.model.dto.UserDto;
import pmf.mina.bjelica.travelholic.model.entity.User;
import pmf.mina.bjelica.travelholic.service.EmailService;
import pmf.mina.bjelica.travelholic.service.StorageService;
import pmf.mina.bjelica.travelholic.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserApi {

	@Autowired
	UserService userService;

	@Autowired
	StorageService storageService;
	
	@Autowired
	EmailService emailService;
	
	private Logger logger = LoggerFactory.getLogger(UserApi.class);
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.findAll());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
		System.out.println("usao");

		userService.save(userDto);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		System.out.println("usao");

		int eq = userService.login(loginDto);
		System.out.println(eq);

		return new ResponseEntity<Object>(eq, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		System.out.println("usao");

		User user = userService.get(username);

		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public ResponseEntity<?> userUpdate(@RequestBody UserDto userDto) {
		System.out.println("usao" + userDto.getFirstName());

		User user = userService.update(userDto);

		return new ResponseEntity<Object>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/try/{username}")
	public ResponseEntity<?> tryUsername(@PathVariable String username) {
		System.out.println("usao");

		boolean ok = userService.tryUsername(username);

		return new ResponseEntity<Object>(ok, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/uploadImage/{username}")
	public ResponseEntity<?> userUpdate(@RequestBody MultipartFile file, @PathVariable String username) {
		String message = "";
		try {
			storageService.store(file);
			userService.setImage(file.getOriginalFilename(), username);
			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			System.out.println(message);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			System.out.println(message);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getImage/{fileName}")
	public ResponseEntity<?> getImage(@PathVariable String fileName) {
		System.out.println("Ovde sam");
		String encodeBase64 = null;
		String image = null;
		String mediaType = null;
		try {
			Resource fileResource = storageService.loadFile(fileName);
			File file = fileResource.getFile();
			byte[] bytesArray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(bytesArray); // read file into bytes[]
			encodeBase64 = Base64.getEncoder().encodeToString(bytesArray);
			mediaType = Files.probeContentType(file.toPath());
			System.out.println(mediaType);
			image = "data:"+mediaType+";base64," + encodeBase64;
			fis.close();
			return new ResponseEntity<String>(image, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			String message = "Doslo je do greske.";
			System.out.println("doslo je do greske");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
	public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) {
		System.out.println("usao");

		try {
			emailService.sendMail(emailDto);
			System.out.println("Nema greske b r e !");
		} catch (MailException e) {
			// TODO: handle exception
			logger.info("Desila se greska "+e.getMessage());
		}
		return new ResponseEntity<Object>( HttpStatus.OK);
	}

}
