package skype;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.skype.ChatMessage;
import com.skype.SkypeException;

public class SkypeChatMessageData implements SkypeChatMessage {

	
	private final String msgId;
	private final String userDisplay;
	private final String message;
	private final Date date;
	private final String userId;

	
	public SkypeChatMessageData(SkypeChat skypeChat, ChatMessage chatMessage) throws ParseException, SkypeException {
		this(
			skypeChat,
			chatMessage.getSenderId(),
			chatMessage.getSenderDisplayName(),
			chatMessage.getContent(),
			chatMessage.getTime()
			);
	}
	
	public SkypeChatMessageData(SkypeChat skypeChat, String userId,
			String userDisplay, String message, Date time) throws ParseException {
		String md5Seed = userId+"/"+message;
		this.msgId = DigestUtils.md5Hex(md5Seed);
		this.userDisplay = userDisplay;
		this.message = message;
		this.date = time;
		this.userId = userId;
		
	}

	@Override
	public String getDisplayUsername() {
		return this.userDisplay;
	}

	@Override
	public String getMessageBody() {
		return this.message;
	}

	@Override
	public String getId() {
		return this.msgId;
	}

	@Override
	public Date getTime() {
		return this.date;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public String messageText(boolean printSender) {
		String formattedTime = new SimpleDateFormat("HH:mm:ss").format(this.getTime());
		final String senderDisplayName;
		if (printSender)
			senderDisplayName = this.getDisplayUsername()+":";
		else
			senderDisplayName = "...";
		
		return String.format("[%s] %s %s\n",
				formattedTime,
				senderDisplayName,
				this.getMessageBody()
				);
	}

	@Override
	public String toString() {
		return this.messageText(true);
	}
}
