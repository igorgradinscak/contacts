package org.jugenfeier.contacts;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@SpringBootConfiguration
@ContextConfiguration(classes = ContactsApplicationTestConfig.class)
@ComponentScan(basePackages = "org.jugenfeier.contacts")
public class ContactsApplicationTestConfig {
}