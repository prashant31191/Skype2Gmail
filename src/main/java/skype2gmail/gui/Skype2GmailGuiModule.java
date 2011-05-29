package skype2gmail.gui;

import skype.commons.Skype2GmailTrayProvider;

import com.google.inject.AbstractModule;

public class Skype2GmailGuiModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Skype2GmailTrayProvider.class).to(Skype2GmailTrayProviderImpl.class);
	}
}
