package pmf.mina.bjelica.travelholic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import pmf.mina.bjelica.travelholic.dao.UserRepository;
import pmf.mina.bjelica.travelholic.model.dto.EmailDto;
import pmf.mina.bjelica.travelholic.model.entity.User;

@Service
public class EmailServiceImpl implements EmailService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void sendMail(EmailDto emailDto) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		User fromUser = userRepo.findUserByUsername(emailDto.getFromUsername());
		User toUser = userRepo.findUserByUsername(emailDto.getToUsername());
		mail.setTo(toUser.getEmail());
		mail.setFrom("travelholic.mina@gmail.com");
		mail.setSubject("Nova poruka sa Travelholic");
		
		String message = "Korisnik "+fromUser.getFirstName()+ " "+fromUser.getLastName()
		+" Vam je poslao poruku na Travelholic-u. Mozete mu odgovoriti na njegov email: "+fromUser.getEmail();
		message += "\n Poruka: \n" + emailDto.getText();
		mail.setText(message);
		
		javaMailSender.send(mail);
		
	}
}
