package com.dongdaozhu.test.Mail;

import android.util.Log;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
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
import javax.mail.internet.MimeUtility;

/**
 * ������
 */
public class MailSender {
	/**
	 * ���ı���ʽ�����ʼ
	 * @param mailInfo �����͵��ʼ�����Ϣ
	 */
	public boolean sendTextMail(final MailInfo mailInfo) {

		// ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// �����Ҫ�����֤���򴴽һ��������֤��
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

//		Session sendMailSession = Session.getInstance(pro, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(mailInfo.getUserName(),mailInfo.getPassword());
//			}
//		});

		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵ʱ��
			mailMessage.setSentDate(new Date());

			// �����ʼ���Ϣ����Ҫ����
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// �����ʼ
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * ��HTML��ʽ�����ʼ
	 * @param mailInfo �����͵��ʼ���Ϣ
	 */
	public static boolean sendHtmlMail(MailInfo mailInfo) {
		// ж��Ƿ���Ҫ�����֤
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// �����Ҫ�����֤���򴴽һ��������֤��
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// ����session����һ���ʼ���Ϣ
			Message mailMessage = new MimeMessage(sendMailSession);
			// �����ʼ������ߵַ
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// �����ʼ���Ϣķ�����
			mailMessage.setFrom(from);
			// �����ʼ��Ľ����ߵַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO���Աʾ�����ߵ�����ΪTO
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// �����ʼ���Ϣ������
			mailMessage.setSubject(mailInfo.getSubject());
			// �����ʼ���Ϣ���͵ʱ��
			mailMessage.setSentDate(new Date());
			// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
			Multipart mainPart = new MimeMultipart();
			// ����һ������HTML���ݵMimeBodyPart
			BodyPart html = new MimeBodyPart();
			// ����HTML����
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// ��MiniMultipart��������Ϊʼ�����
			mailMessage.setContent(mainPart);
			// �����ʼ
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}


	/**
	 * ���ʹ��������ʼ
	 * @param info
	 * @return
     */
	public boolean sendFileMail(MailInfo info, File file){
		Message attachmentMail = createAttachmentMail(info,file);
		try {
			Transport.send(attachmentMail);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * �������и������ʼ
	 * @return
	 */
	private Message createAttachmentMail(final MailInfo info, File file) {
		//�����ʼ
		MimeMessage message = null;
		Properties pro = info.getProperties();
		try {

			Session sendMailSession = Session.getInstance(pro, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(info.getUserName(),info.getPassword());
				}
			});

			message = new MimeMessage(sendMailSession);
			// �����ʼ��Ļ�����Ϣ
			//�����ʼ������ߵַ
			Address from = new InternetAddress(info.getFromAddress());
			//�����ʼ���Ϣķ�����
			message.setFrom(from);
			//�����ʼ��Ľ����ߵַ�������õ��ʼ���Ϣ��
			Address to = new InternetAddress(info.getToAddress());
			//�����ʼ���ϢĽ�����, Message.RecipientType.TO���Աʾ�����ߵ�����ΪTO
			message.setRecipient(Message.RecipientType.TO, to);
			//ʼ�����
			message.setSubject(info.getSubject());

			// �����ʼ����ģΪ˱����ʼ����������������⣬��Ҫʹ��CharSet=UTF-8ָ���ַ�����
			MimeBodyPart text = new MimeBodyPart();
			text.setContent(info.getContent(), "text/html;charset=UTF-8");

			// ���������������ݹϵ
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(text);
				// �����ʼ�����
				MimeBodyPart attach = new MimeBodyPart();

			FileDataSource ds = new FileDataSource(file);
			DataHandler dh = new DataHandler(ds);
				attach.setDataHandler(dh);
				attach.setFileName(MimeUtility.encodeText(dh.getName()));
				mp.addBodyPart(attach);
			mp.setSubType("mixed");
			message.setContent(mp);
			message.saveChanges();

		} catch (Exception e) {
			Log.e("TAG", "�������������ʼʧ��");
			e.printStackTrace();
		}
		// �������ɵ��ʼ
		return message;
	}

}
