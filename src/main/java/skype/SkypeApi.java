package skype;



public interface SkypeApi {
	SkypeChat[] getAllChats();

	boolean isRunning();
}
