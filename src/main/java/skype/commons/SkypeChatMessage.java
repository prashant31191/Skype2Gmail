package skype.commons;

import java.util.Date;

public interface SkypeChatMessage {
	public static final SkypeChatDateFormat chatDateFormat = new SkypeChatDateFormatImpl();
	public static final SkypeMessageDateFormat chatMessageDateFormat = new SkypeMessageDateFormatImpl();
	
	String getSenderId();

	String getSenderDisplayname();

	String getMessageBody();

	String getSignature();

	Date getTime();

	String messageText(boolean printUser);
	
	boolean isMatchingSignature(String signature);

	boolean earlierThan(SkypeChatMessage equivalentMessageInOtherChat);

	SkypeChatMessage createCopyWithNewTime(Date newMessageTime);
}
