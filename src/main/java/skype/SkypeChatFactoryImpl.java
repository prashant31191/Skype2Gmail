package skype;

import java.util.LinkedList;
import java.util.List;

import com.skype.Chat;
import com.skype.ChatMessage;
import com.skype.SkypeException;
import com.skype.User;

public class SkypeChatFactoryImpl implements SkypeChatFactory {

	@Override
	public SkypeChat produce(Chat chat) {
		try {
			return new SkypeChatImpl(
					chat.getId(), 
					chat.getTime(), 
					chat.getWindowTitle(), 
					populateUserList(chat), 
					populateChatList(chat));
		} catch (SkypeException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private List<String> populateUserList(Chat chat) throws SkypeException {
		User[] allMembers = chat.getAllPosters();
		LinkedList<String> memberIds = new LinkedList<String>();
		for (User user : allMembers) {
			memberIds.add(user.getId());
		}
		return memberIds;
	}

	private TimeSortedMessages populateChatList(Chat chat) throws SkypeException {
		ChatMessage[] allChatMessages = chat.getAllChatMessages();
		TimeSortedMessages chatMessageList = new TimeSortedMessages();
		try {
			for (ChatMessage chatMessage : allChatMessages) {
				chatMessageList
						.add(new SkypeChatMessageData(chatMessage));
			}

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return chatMessageList;
	}

}
