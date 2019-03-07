package sergg.samples;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * https://www.tutorialspoint.com/java/java_sending_email.htm
 *
 * @version $Revision:$
 */
public class JavaMail {

    private static final String REPORT_PATH = "3ir/runtime/SMP_2/smp/tmp/archive";
    private static final String AGGREGATED_REPORT_FILE_NAME_PATTERN = "3ir-performance-report-{0}.csv";

    private String email;
    private String primarySmtpServer;
    private String secondarySmtpServer;

    public static void main(String[] args) {
        sendTextEmail(
                "SergeyGomanyuk@yandex.ru",
                "sergeygo@docs.com",
                "localhost",
                "Text mail test",
                "Mail without attachment!");
//        sendHtmlEmail(
//                "SergeyGomanyuk@yandex.ru",
//                "sergeygo@docs.com",
//                "localhost",
//                "HTML email test",
//                "Mail without attachment!");
//        sendEmailWithAttach(
//                "SergeyGomanyuk@yandex.ru",
//                "sergeygo@docs.com",
//                "localhost",
//                "Mail with attachment test",
//                "Mail with attachment!",
//                "attachement.csv");
    }

    public static void sendTextEmail(String to, String from, String smtpServer, String subject, String text) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", smtpServer);
        //properties.setProperty("mail.user", "myuser");
        //properties.setProperty("mail.password", "mypwd");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            //message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


//    public static String[] processReportDirectory() {
//        List<String> fileNames = new ArrayList<>();
//        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory))) {
//            for (Path path : directoryStream) {
//                fileNames.add(path.toString());
//            }
//        } catch (IOException ex) {}
//        return fileNames;
//    }

    public static void sendHtmlEmail(String to, String from, String smtpServer, String subject, String text) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", smtpServer);
        //properties.setProperty("mail.user", "myuser");
        //properties.setProperty("mail.password", "mypwd");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Reach text</h1><br><i>" + text + "</i>", "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendEmailWithAttach(String to, String from, String smtpServer, String subject, String text, String filename) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", smtpServer);
        //properties.setProperty("mail.user", "myuser");
        //properties.setProperty("mail.password", "mypwd");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(text);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart );

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

