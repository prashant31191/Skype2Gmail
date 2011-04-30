package skype2gmail;


import javax.mail.MessagingException;

import junit.framework.Assert;

import mail.SkypeMailStore;
import mail.SkypeMailMessageFactoryImpl;
import mail.SkypeMailFolder;
import mail.SkypeMailFolderImpl;
import mail.SkypeMailMessage;

import org.junit.Test;

import skype.SkypeChat;
import skype.mocks.SkypeApiMock;
import skype2gmail.mocks.SkypeMailMessageMock;
import skype2gmail.mocks.SkypeMailStoreMock;

public class SkypeMailFolderTest {
	@Test
	public void testAppendAndRetrieve() {
		SkypeMailFolder subject = createSubject();

		String expectedChatId = "#foo$bar";
		SkypeChat skypeChat = SkypeApiMock.produceChatMock(expectedChatId,
				"joe", "moe");
		subject.appendMessage(new SkypeMailMessageMock(skypeChat));

		SkypeMailMessage retrievedMessage = subject.retrieveMessageEntryFor(skypeChat);

		String chatId = retrievedMessage.getChatId();
		Assert.assertEquals(expectedChatId, chatId);
	}

	@Test
	public void testDelete() throws MessagingException {
		SkypeMailFolder subject = createSubject();

		String chatToDelete = "#foo$bar";
		SkypeChat skypeChat = SkypeApiMock.produceChatMock(chatToDelete, "joe", "moe");
		subject.appendMessage(new SkypeMailMessageMock(skypeChat));

		subject.deleteMessageBasedOnId(chatToDelete);
		
		SkypeMailMessage retrievedMessage = subject.retrieveMessageEntryFor(skypeChat);
		Assert.assertNull(retrievedMessage);
	}
	
	@Test
	public void testReplaceMessageMatchingTerm() {
		SkypeMailFolder subject = createSubject();

		String chatToDelete = "#foo$bar";
		SkypeChat skypeChat = SkypeApiMock.produceChatMock(chatToDelete, "joe", "moe");
		subject.appendMessage(new SkypeMailMessageMock(skypeChat));

		
		SkypeMailMessageFactoryImpl gmailMessageFactoryImpl = getGmailMessageFactory();
		SkypeMailMessage replacementMessage = gmailMessageFactoryImpl.factory();
		replacementMessage.setBody("replacement message");
		subject.replaceMessageMatchingTerm(null, replacementMessage);
		
		SkypeMailMessage retrievedMessage = subject.retrieveMessageEntryFor(skypeChat);
		Assert.assertEquals(replacementMessage.getBody(), retrievedMessage.getBody());
	}

	private SkypeMailMessageFactoryImpl getGmailMessageFactory() {
		SessionProviderImpl sessionProvider = new SessionProviderImpl();
		SkypeMailMessageFactoryImpl gmailMessageFactoryImpl = new SkypeMailMessageFactoryImpl(sessionProvider);
		return gmailMessageFactoryImpl;
	}

	private SkypeMailFolder createSubject() {
		SkypeChatFolderProvider fp = new DefaultSkypeChatFolderProvider();
		SkypeMailStore mockStore = new SkypeMailStoreMock();
		return new SkypeMailFolderImpl(fp, mockStore, getGmailMessageFactory());
	}
}
