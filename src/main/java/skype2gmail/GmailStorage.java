package skype2gmail;

import gmail.GmailMessage;

import org.apache.log4j.Logger;

import skype.SkypeChat;
import skype.SkypeChatFactory;
import skype.SkypeStorage;
import skype.StorageEntry;
import utils.LoggerProvider;

import com.google.inject.Inject;

public class GmailStorage implements SkypeStorage {

	private final GmailStorageEntryFactory entryFactory;
	private final GmailMessageProvider gmailMessageProvider;
	private final GmailMessageChatParser gmailMessageChatParser;
	private final GmailFolderStore rootFolderProvider;
	private final SkypeChatFactory skypeChatFactory;
	private final LoggerProvider loggerProvider;
	private final UserAuthProvider userAuthProvider;
	private Logger LOGGER;

	@Inject
	public GmailStorage(
			GmailStorageEntryFactory entryFactory,
			GmailMessageProvider gmailMessageProvider,
			GmailMessageChatParser gmailMessageChatParser, 
			GmailFolderStore rootFolderProvider,
			SkypeChatFactory skypeChatFactory,
			LoggerProvider loggerProvider,
			UserAuthProvider userAuthProvider) {
		this.entryFactory = entryFactory;
		this.gmailMessageProvider = gmailMessageProvider;
		this.gmailMessageChatParser = gmailMessageChatParser;
		this.rootFolderProvider = rootFolderProvider;
		this.skypeChatFactory = skypeChatFactory;
		this.loggerProvider = loggerProvider;
		this.userAuthProvider = userAuthProvider;
	}

	@Override
	public StorageEntry newEntry(SkypeChat chat) {
		return entryFactory.produce(chat);
	}

	@Override
	public StorageEntry retrievePreviousEntryFor(SkypeChat skypeChat) {
		GmailMessage[] storedMessages = getStoreMessages();
		for (GmailMessage message : storedMessages) {
			String chatId = message.getChatId();
			if (skypeChat.getId().equals(chatId)) {
				SkypeChat previousChat = makeSkypeChat(message);
				return entryFactory.produce(previousChat);
			}
		}
		return entryFactory.produce(this.skypeChatFactory.produceEmpty());
	}

	private SkypeChat makeSkypeChat(GmailMessage message) {
		return gmailMessageChatParser.parse(message);
	}

	private GmailMessage[] getStoreMessages() {
		return gmailMessageProvider.getMessages();
	}

	@Override
	public void open() {
		getLogger().info("Will send messages to " + userAuthProvider.getUser());
	}
	
	@Override
	public void close() {
		rootFolderProvider.close();
		getLogger().info("Messages sent to account " + userAuthProvider.getUser());
	}

	
	private Logger getLogger() {
		if (LOGGER != null)
			return LOGGER;
		LOGGER = loggerProvider.getLogger(getClass());
		return LOGGER;
		
	}
}
