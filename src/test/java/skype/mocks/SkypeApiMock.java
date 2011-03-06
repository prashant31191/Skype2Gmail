package skype.mocks;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import skype.SkypeApi;
import skype.SkypeChat;
import testutils.DateHelper;

public class SkypeApiMock implements SkypeApi {
	private List<SkypeChatMock> mockChatList = new LinkedList<SkypeChatMock>();

	@Override
	public SkypeChat[] getAllRecentChats() {
		
		
		return mockChatList.toArray(new SkypeChatMock[0]);
	}
	
	public void addMockChat(SkypeChatMock mockChat){
		mockChatList.add(mockChat);
	}
	

	public static SkypeChatMock produceChatMock(String chatId, String member1, String member2) {
		String[] members= new String[]{member1,member2};
		
		Date aDate = DateHelper.makeDate(2011, 3, 21, 15, 0, 0);
		
		SkypeChatMock chat = new SkypeChatMock(chatId, aDate, "FOO", members);
		
		createMockMessage(member1, chat, "2011/04/21 15:14:18", "Hya");
		createMockMessage(member2, chat, "2011/04/21 15:14:24", "Howdy");
		
		return chat;
	}

	private static SkypeChatMock createMockMessage(String memberId, SkypeChatMock chat,
			String time, String message) {
		return chat.addMockMessage(time, memberId, memberId.toUpperCase(), message);
	}
}
