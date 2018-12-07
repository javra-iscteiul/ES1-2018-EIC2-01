package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import BDA.Mensagem;
import BDA.Email.Email;
import BDA.Email.MensagemEmail;
import BDA.Facebook.MensagemFacebook;
import BDA.Twitter.MensagemTwitter;
import twitter4j.MediaEntity;

public class MensagemTest {
	
	@Test
    public void testMsgEmail() {
		Mensagem msg1 = new MensagemEmail("user1", "subject", "18/12/2018", "teste2");
		
		assertNotNull(msg1);
		
		assertNotNull(msg1.getUser());
		msg1.setUser("user teste");
		assertEquals("user teste", msg1.getUser());
		
		assertNotNull(((MensagemEmail)msg1).getSubject());
		((MensagemEmail)msg1).setSubject("subjectTeste");
		assertEquals("subjectTeste", ((MensagemEmail)msg1).getSubject());
		
		assertNotNull(msg1.getDate());
		msg1.setDate("18/11/2018");
		assertEquals("18/11/2018", msg1.getDate());
		
		assertNotNull(msg1.getContent());
		msg1.setContent("content teste");
		assertEquals("content teste", msg1.getContent());
		
		assertTrue(msg1.containsFilter("teste"));
		assertFalse(msg1.containsFilter("pass"));
		
		assertTrue(msg1.userContainsFilter("user"));
		assertFalse(msg1.userContainsFilter("pass"));
		
		Email.setFolder("INBOX");
		assertNotNull(msg1.toString());
		Email.setFolder("Sent");
		assertNotNull(msg1.toString());
    }
	
	@Test
	public void testMsgFacebook() {
		Mensagem msg1 = new MensagemFacebook("user2", "18/12/2018", "teste3");
		
		assertNotNull(msg1);
		
		assertNotNull(msg1.getUser());
		msg1.setUser("user teste");
		assertEquals("user teste", msg1.getUser());
		
		assertNotNull(msg1.getDate());
		msg1.setDate("18/11/2018");
		assertEquals("18/11/2018", msg1.getDate());
		
		assertNotNull(msg1.getContent());
		msg1.setContent("content teste");
		assertEquals("content teste", msg1.getContent());
		
		assertTrue(msg1.containsFilter("teste"));
		assertFalse(msg1.containsFilter("pass"));
		
		assertTrue(msg1.userContainsFilter("user"));
		assertFalse(msg1.userContainsFilter("pass"));
		
		assertNotNull(msg1.toString());
    }
	
	@Test
	public void testMsgTwitter() {
		Mensagem msg1 = new MensagemTwitter("user3", "18/12/2018", "teste4");
		
		assertNotNull(msg1);
		
		assertNotNull(msg1.getUser());
		msg1.setUser("user teste");
		assertEquals("user teste", msg1.getUser());
		
		assertNotNull(msg1.getDate());
		msg1.setDate("18/11/2018");
		assertEquals("18/11/2018", msg1.getDate());
		
		assertNotNull(msg1.getContent());
		msg1.setContent("content teste");
		assertEquals("content teste", msg1.getContent());
		
		((MensagemTwitter)msg1).setMedia(new MediaEntity[1]);
		assertNotNull(((MensagemTwitter)msg1).getMedia());
		
		assertTrue(msg1.containsFilter("teste"));
		assertFalse(msg1.containsFilter("pass"));
		
		assertTrue(msg1.userContainsFilter("user"));
		assertFalse(msg1.userContainsFilter("pass"));
		
		assertNotNull(msg1.toString());
    }
}