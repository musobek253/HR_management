package uz.muso.hrmanagement.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class GMailSender {
    @Autowired
    JavaMailSender mailSender;
    public boolean send(String to, String text) throws MessagingException {

        String from = "Alibek@gmail.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setSubject("Information");
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(text);
        mailSender.send(mimeMessage);
        return true;
    }


    public boolean mailTextAddEmployer(String email, String code, String password) throws MessagingException {
        String link = "http://localhost:80/api/v1/user/verifyEmail?email=" + email + "&code=" + code;

        String text = "<a href=\"" + link + "\">Emailni tasdiqlash</a>\n" +
                "<br>\n" +
                "<p>Parolingiz: " + password + "</p>";

        return send(email, text);
    }
    public boolean mailTextAddTask(String email, String name) throws MessagingException {
        String text = "You have been given a task called " + name + "." + "<br>" + "<a href=\"\" style=\"padding: 10px 15px; background-color: darkslateblue; color: white; text-decoration: none; border-radius: 4px; margin: 10px; display: flex; max-width: 120px;\">View task</a>\n" +
                "<br>\n";
        return send(email, text);
    }

    public boolean taskCompleted(String emailTaskGiver,String emailTaskTaker,String taskName) throws MessagingException {
        String text="Your task completed by "+emailTaskTaker+"</br>"+ " task name:"+taskName;
        return send(emailTaskGiver,text);
    }

}
