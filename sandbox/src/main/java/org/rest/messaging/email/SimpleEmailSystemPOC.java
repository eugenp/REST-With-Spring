package org.rest.messaging.email;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.google.common.collect.Lists;

public class SimpleEmailSystemPOC {
    public static final String ACCESS_KEY = "_INSERT_ACCESS_KEY_";
    public static final String SECRET_KEY = "_INSERT_SECRET_KEY_";

    public static void main(String args[]) {
        final String sender = "baeldung@gmail.com"; // should be verified email

        final List<String> recipients = Lists.newArrayList("hanriseldon@gmail.com"); // again a verified email, if you are in sandbox
        sendMail(sender, recipients, "Straight from AWS SES", "Hey, did you know that this message was sent via Simple Email Service programmatically using AWS Java SDK.");
    }

    //

    static void sendMail(final String sender, final List<String> recipients, final String subject, final String body) {
        final Destination destination = new Destination(recipients);

        final Content subjectContent = new Content(subject);
        final Content bodyContent = new Content(body);
        final Body msgBody = new Body(bodyContent);
        final Message msg = new Message(subjectContent, msgBody);

        final SendEmailRequest request = new SendEmailRequest(sender, destination, msg);

        final AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        final AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient(credentials);
        final SendEmailResult result = sesClient.sendEmail(request);

        System.out.println(result);
    }

}
