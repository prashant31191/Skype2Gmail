package mail;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import skype.ApplicationException;
import skype2gmail.SessionProvider;
import skype2gmail.UserAuthProvider;

import com.google.inject.Inject;

public class SkypeGmailStore implements SkypeMailStore {
	private Store store;
	private final Session session;
	private final UserAuthProvider userInfoProvider;

	@Inject
	public SkypeGmailStore(SessionProvider sessionProvider,
			UserAuthProvider userInfoProvider) 
	{
		this.userInfoProvider = userInfoProvider;
		this.session = sessionProvider.getInstance();
	}

	@Override
	public Folder getFolder(String folder) {
		Folder skypeChatFolder;
		String user = userInfoProvider.getUser();
		String password = userInfoProvider.getPassword();
		try {
			store = session.getStore("imaps");
			store.connect("imap.gmail.com", user, password);

			skypeChatFolder = store.getFolder(folder);
			if (!skypeChatFolder.exists()) {
				skypeChatFolder.create(Folder.HOLDS_MESSAGES);
			} else {
				skypeChatFolder.open(Folder.READ_WRITE);
			}
			return skypeChatFolder;
		} catch (NoSuchProviderException e) {
			throw new ApplicationException(e);
		} catch (MessagingException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void close() {
		try {
			store.close();
		} catch (MessagingException e) {
			throw new ApplicationException(e);
		}
	}
}
