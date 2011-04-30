package skype2gmail.mocks;

import gmail.mocks.FolderMock;
import skype.SkypeApi;
import skype.SkypeUserFactory;
import skype.mocks.SkypeApiImplMock;
import skype.mocks.SkypeUserFactoryMock;
import skype2gmail.GmailFolder;
import skype2gmail.Skype2GmailModuleCommons;
import skype2gmail.UserAuthProvider;
import utils.LoggerProvider;
import utils.SimpleLoggerProvider;

import com.google.inject.Scopes;


public class Skype2GmailModuleMockingSkypeApi extends Skype2GmailModuleCommons {

	@Override
	public void configure() {
		super.configure();
		bind(SkypeApi.class).to(SkypeApiImplMock.class).in(Scopes.SINGLETON);
		bind(GmailFolder.class).to(FolderMock.class).in(Scopes.SINGLETON);
		
		bind(UserAuthProvider.class).to(MockAuthProvider.class).in(Scopes.SINGLETON);
		bind(SkypeUserFactory.class).to(SkypeUserFactoryMock.class).in(Scopes.SINGLETON);
		bind(LoggerProvider.class).to(SimpleLoggerProvider.class);
		
	}

}
