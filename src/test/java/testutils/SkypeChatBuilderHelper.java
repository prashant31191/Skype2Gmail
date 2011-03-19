package testutils;

import java.util.Date;

import skype.SkypeChatFactoryImpl;
import skype.SkypeChatImpl;
import skype.SkypeChatMessage;
import skype.SkypeChatMessageDataFactory;
import skype.SkypeUserFactory;
import skype.TimeSortedMessages;
import skype.UsersSortedByUserId;
import skype.mocks.SkypeUserFactoryMock;
import utils.DigestProvider;

abstract public class SkypeChatBuilderHelper {
	public final DigestProvider digestProvider;
	public final SkypeChatMessageDataFactory skypeChatMessageFactory;
	public final SkypeChatFactoryImpl skypeChatFactoryImpl;
	private  final TimeSortedMessages messageList;

	public SkypeChatBuilderHelper() {
		digestProvider = new DigestProvider();
		skypeChatMessageFactory = new SkypeChatMessageDataFactory(digestProvider);
		SkypeUserFactory skypeUserFactory = new SkypeUserFactoryMock();
		skypeChatFactoryImpl = new SkypeChatFactoryImpl(digestProvider, skypeChatMessageFactory, skypeUserFactory);
		messageList = new TimeSortedMessages();
	}
	public abstract void addChatMessages();

	public SkypeChatImpl getChat(String chatId, String topic) {
		UsersSortedByUserId members = SkypeChatHelper.makeUserList(new String[] { "moe", "joe" });
		addChatMessages();

		Date chatTime = DateHelper.makeDate(2011, 3, 21, 15, 0, 0);
		return (SkypeChatImpl) skypeChatFactoryImpl.produce(chatId, chatTime, topic, members, messageList);
	}


	public void addMessage(String userId, String message, int day,int hour, int minute, int second) {
		Date firstMessageTime = DateHelper.makeDate(2011, 3, day, hour, minute, second);
		SkypeChatMessage firstMessage = skypeChatMessageFactory.produce(userId,
				userId.toUpperCase(), message, firstMessageTime);
		messageList.add(firstMessage);
	}
}