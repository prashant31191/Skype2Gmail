package skype2disk;

import skype.commons.Skype2StorageModuleCommons;
import skype.commons.SkypeHistoryCli;
import skype.commons.SkypeStorage;

import com.google.inject.Scopes;

public class Skype2DiskModuleCommons extends Skype2StorageModuleCommons {
	
	private final String[] args;

	public Skype2DiskModuleCommons(String[] args)
	{
		this.args = args;
	}

	@Override
	protected void configure() {
		super.configure();
		bind(SkypeStorage.class).to(FileSystemStorage.class).in(Scopes.SINGLETON);
		bind(HistoryDir.class).to(SkypeHistoryCli.class);
		bind(FileDumpContentParser.class).to(FileDumpContentParserImpl.class);
		bind(String[].class).toInstance(args);
	}
}
