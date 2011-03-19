package skype;

import skype2disk.MessageBodyParserFactory;
import skype2disk.MessageBodyParserFactoryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class Skype2StorageModuleCommons extends AbstractModule {
	@Override
	protected void configure() {
		bind(SkypeChatFactory.class).to(SkypeChatFactoryImpl.class).in(Scopes.SINGLETON);
		bind(SkypeHistoryRecorder.class).to(SkypeRecorder.class).in(Scopes.SINGLETON);
		bind(SkypeChatDateFormat.class).to(SkypeChatDateFormatImpl.class).in(Scopes.SINGLETON);
		bind(SkypeMessageDateFormat.class).to(SkypeMessageDateFormatImpl.class).in(Scopes.SINGLETON);
		bind(MessageBodyParserFactory.class).to(MessageBodyParserFactoryImpl.class).in(Scopes.SINGLETON);
	}
}
