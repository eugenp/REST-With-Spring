package org.rest.messaging.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rest.common.messaging.email.SesMailTemplate;
import org.rest.common.messaging.email.spring.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { EmailConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SesIntegrationTest {

    @Autowired
    private SesMailTemplate mailTemplate;

    // tests

    @Test
    public final void whenTestEmailIsSent_thenNoExceptions() {
        final String sender = "baeldung@gmail.com"; // should be verified email
        final String recipient = "hanriseldon@gmail.com"; // again a verified email, if you are in sandbox

        mailTemplate.sendOneMail(sender, recipient, "Straight from AWS SES", "Hey, did you know that this message was sent via Simple Email Service programmatically using AWS Java SDK.");
    }

}
