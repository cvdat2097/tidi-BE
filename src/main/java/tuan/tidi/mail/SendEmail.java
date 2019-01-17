package tuan.tidi.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail implements Runnable{

    private static final String FROM = "tidiservice1@gmail.com";
    private static final String FROMNAME = "TIDI";
    private String to = "";
    private static final String SMTP_USERNAME = "tidiservice1@gmail.com";
    private static final String SMTP_PASSWORD = "Aa@123456";
    private static final String CONFIGSET = "ConfigSet";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String SUBJECT = "Verification code TIDI"; 
    private String code = "";
    private String link = "";
    
    public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public void run() {
    	try{
    		sendEmail();
    	}catch(Exception e) {
    		System.out.println("Send Email Error: " + e);
    	}
    }
    
    
    
    public String getTo() {
		return to;
	}



	public void setTo(String to) {
		this.to = to;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}

	public void sendEmail() throws Exception {

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
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(SUBJECT);
        String BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<h1>your verification code: </h1>",
        	    "<h2>", code, "</h2>",
        	    "<h1> or </h1>",
        	    "<a href=\"", link , "\">Click HERE</a>"
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