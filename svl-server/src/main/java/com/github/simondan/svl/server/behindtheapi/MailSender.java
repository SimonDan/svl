package com.github.simondan.svl.server.behindtheapi;

import com.github.simondan.svl.communication.auth.UserName;
import com.github.simondan.svl.server.auth.User;
import org.simplejavamail.email.*;
import org.simplejavamail.mailer.MailerBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.*;

import static com.github.simondan.svl.communication.utils.SharedUtils.RESTORE_CODE_EXPIRATION_THRESHOLD;

/**
 * @author Simon Danner, 23.11.2019
 */
@ApplicationScoped
public class MailSender
{
  private static final Logger LOGGER = Logger.getLogger(MailSender.class.getName());
  private static final String FROM = "SVL Team Management";

  @Inject
  private Config config;

  void sendRestoreCodeMail(User pUser)
  {
    final UserName userName = pUser.getValue(User.NAME);
    final String mail = pUser.getValue(User.EMAIL);
    final String restoreCode = pUser.getValue(User.RESTORE_CODE);

    final String text = "Hello " + userName + "! \n\nYour account restore code is: " + restoreCode + "\nIt will expire in " +
        RESTORE_CODE_EXPIRATION_THRESHOLD.toMinutes() + " minutes!\n\n" + FROM;

    final Email email = EmailBuilder.startingBlank()
        .from(FROM, config.getMailUser())
        .to(userName.toString(), mail)
        .withSubject("Account recovery code")
        .withPlainText(text)
        .buildEmail();

    try
    {
      MailerBuilder
          .withSMTPServer(config.getMailHost(), config.getMailPort(), config.getMailUser(), config.getMailPassword())
          .buildMailer()
          .sendMail(email);
    }
    catch (Exception pE)
    {
      LOGGER.log(Level.WARNING, "Mail not sent! mail: " + mail + ", user: " + userName + ", reason: " + pE.getMessage());
    }
  }
}
