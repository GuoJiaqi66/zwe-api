package top.zwsave.zweapi.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.zwsave.zweapi.service.MailService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: Ja7
 * @Date: 2022-03-19 21:54
 */
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void htmlRegistrationMail(String to, int count) {
        String subject = "注册成功";
        String content2 = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        /* #Registration {\n" +
                "            width: 194px;\n" +
                "            height: 300px;\n" +
                "            background-color: red;\n" +
                "        } */\n" +
                "\n" +
                "        img {\n" +
                "            width: 388px;\n" +
                "            height: 600px;\n" +
                "        }\n" +
                "\n" +
                "        .textarea {\n" +
                "            position: absolute;\n" +
                "            top: 275px;\n" +
                "            font-size: 20px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id=\"Registration\">\n" +
                "        <div class=\"textarea\">\n" +
                "            <pre>\n" +
                "感谢您注册zwsave:\n" +
                "    您是 " + count + " 位注册用户,\n" +
                "    若您有任何问题, \n" +
                "    均可联系本人: \n" +
                "    mail: zwsave@aliyun.com\n" +
                "            </pre>\n" +
                "        </div>\n" +
                "        <img src=\"https://zwe-api-1258360747.cos.ap-beijing.myqcloud.com/zwsave/system/jordan.jpg\" alt=\"照片由Jordan(班主任)提供\">\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content2, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
