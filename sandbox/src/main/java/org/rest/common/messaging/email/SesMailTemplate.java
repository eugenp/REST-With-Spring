package org.rest.common.messaging.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.google.common.collect.Lists;

@Component
public final class SesMailTemplate {

    @Autowired
    private AmazonSimpleEmailServiceClient sesClient;

    public SesMailTemplate() {
        super();
    }

    // API

    public final SendEmailResult sendOneMail(final String sender, final String recipient, final String subject, final String body) {
        final Destination destination = new Destination(Lists.newArrayList(recipient));

        final Content subjectContent = new Content(subject);
        final Content bodyContent = new Content(body);
        final Body msgBody = new Body(bodyContent);
        final Message msg = new Message(subjectContent, msgBody);

        final SendEmailRequest request = new SendEmailRequest(sender, destination, msg);

        return sesClient.sendEmail(request);
    }

}
