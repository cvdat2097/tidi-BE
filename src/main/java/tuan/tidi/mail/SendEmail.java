package tuan.tidi.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

    static final String FROM = "tidiservice1@gmail.com";
    static final String FROMNAME = "TIDI";
    static final String TO = "kingtuan97@gmail.com";
    static final String SMTP_USERNAME = "tidiservice1@gmail.com";
    static final String SMTP_PASSWORD = "Aa@123456";
    static final String CONFIGSET = "ConfigSet";
    static final String HOST = "smtp.gmail.com";
    static final int PORT = 587;
    static final String SUBJECT = "Verification code TIDI"; 

    public void sendEmail(String code) throws Exception {

    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        String BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<h1>your verification code: </h1>",
        	    "<h2>", code ,"</h2>"
        	);
        msg.setContent(BODY,"text/html");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME,SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        }
        finally
        {
            // Close and terminate the connection.
            try{
            	transport.close();
            }catch(Exception e){
            	
            }
        }
    }
}