package skype;

import java.util.Date;

public interface SkypeChat {

	TimeSortedMessages getChatMessages();
	
	UsersSortedByUserId getPosters();

	String getId();

	Date getTime();

	String getTopic();

	String getBodySignature();

	Date getLastModificationTime();

	SkypeChat merge(SkypeChat skypeChat);

	String getChatAuthor();
}
