package pmf.mina.bjelica.travelholic.service;

import org.springframework.mail.MailException;

import pmf.mina.bjelica.travelholic.model.dto.EmailDto;

public interface EmailService {


	void sendMail(EmailDto emailDto) throws MailException;

}
