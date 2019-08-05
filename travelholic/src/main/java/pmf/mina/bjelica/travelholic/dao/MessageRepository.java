package pmf.mina.bjelica.travelholic.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pmf.mina.bjelica.travelholic.model.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
