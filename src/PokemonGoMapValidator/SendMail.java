/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PokemonGoMapValidator;

import static PokemonGoMapValidator.Main.EMAIL_DEST;
import static PokemonGoMapValidator.Main.LOGIN;
import static PokemonGoMapValidator.Main.PASS;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
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

/**
 *
 * @author Altran
 */
public class SendMail {
    //reportFileName = TestExecutionResultFileName

    public void sendMail(String currentDirectory, List<String> subjectList, List<String> attachmentList) {
        final String username = LOGIN;
        final String password = PASS;
        String origem = LOGIN;
        String destino = EMAIL_DEST;
        String assunto = "PokemonGO maps results";
        String mensagem = "";

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

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(origem));
            
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destino));
            message.setSubject(assunto);
            //message.setText("Dear Mail Crawler,");

            // Create the message part 
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            String newline = System.getProperty("line.separator");
            for (int i = 0; i < subjectList.size(); i++) {
                mensagem = mensagem + subjectList.get(i) + newline;
            }
            messageBodyPart.setText(mensagem);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            for (int i = 0; i < attachmentList.size(); i = i+2) {
                messageBodyPart = new MimeBodyPart();
                String filename = attachmentList.get(i) + attachmentList.get(i+1);
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(attachmentList.get(i+1));
                multipart.addBodyPart(messageBodyPart);
            }
           
            //String filename = currentDirectory + "/prints/Coimbra/semPokemongos.png";

            // Send the complete message parts
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
