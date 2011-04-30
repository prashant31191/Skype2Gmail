package skype2gmail.mocks;

import mail.SkypeMailFolder;
import mail.mocks.FolderMock;
import skype.BasePath;
import skype.LastSynchronizationProvider;
import skype.SkypeApi;
import skype.SkypeUserFactory;
import skype.mocks.LastSynchronizationProviderMock;
import skype.mocks.SkypeApiImplMock;
import skype.mocks.SkypeUserFactoryMock;
import skype2disk.mocks.BasePathMock;
import skype2gmail.Skype2GmailModuleCommons;
import skype2gmail.UserAuthProvider;
import utils.LoggerProvider;
import utils.SimpleLoggerProvider;

import com.google.inject.Scopes;


public class Skype2GmailModuleMockingSkypeApi extends Skype2GmailModuleCommons {

	@Override
	public void configure() {
		super.configure();
		bind(UserAuthProvider.class).to(MockAuthProvider.class).in(Scopes.SINGLETON);
		bind(SkypeMailFolder.class).to(FolderMock.class).in(Scopes.SINGLETON);
		
		bind(SkypeApi.class).to(SkypeApiImplMock.class).in(Scopes.SINGLETON);
		bind(SkypeUserFactory.class).to(SkypeUserFactoryMock.class).in(Scopes.SINGLETON);
		bind(LoggerProvider.class).to(SimpleLoggerProvider.class).in(Scopes.SINGLETON);
		bind(LastSynchronizationProvider.class).to(LastSynchronizationProviderMock.class).in(Scopes.SINGLETON);	
		bind(BasePath.class).to(BasePathMock.class).in(Scopes.SINGLETON);
	}

}
