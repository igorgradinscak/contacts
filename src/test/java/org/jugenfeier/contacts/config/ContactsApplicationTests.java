package org.jugenfeier.contacts.config;

import org.jugenfeier.contacts.ContactsApplicationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = ContactsApplicationTestConfig.class)
@ComponentScan(basePackages = "org.jugenfeier.contacts")
class ContactsApplicationTests {

	@Test
	void contextLoads() {
		assert (true);
	}

}
