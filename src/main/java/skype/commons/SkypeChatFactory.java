package skype.commons;

import com.skype.Chat;

public interface SkypeChatFactory {
	SkypeChat produce(Chat chat);
	SkypeChat produceEmpty();
}
