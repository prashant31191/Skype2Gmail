package skype2gmail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import skype.commons.Skype2StorageModuleCommons;
import skype.exceptions.ApplicationException;
import skype2disk.Skype2GmailConfigDir;
import utils.Maybe;

import com.google.inject.Inject;

public class Skype2GmailConfigContentsImpl implements Skype2GmailConfigContents {
	private Properties config;
	private final Skype2GmailConfigDir configDir;

	@Inject
	public Skype2GmailConfigContentsImpl(Skype2GmailConfigDir configDir) {
		this.configDir = configDir;
	}
	
	@Override
	public boolean isOutputVerbose() {
		Maybe<String> l = getProperty("verbosity");
		if (l.unbox() == null)
			return true;
		return l.unbox().equals("verbose");
	}


	@Override
	public Maybe<String> getUserName() {
		return this.getProperty("gmail.user");
	}

	@Override
	public void setUserName(String u) {
		setProperty("gmail.user", u);
	}

	@Override
	public Maybe<String> getPassword() {
		return getProperty("gmail.password");
	}

	@Override
	public void setPassword(String p) {
		setProperty("gmail.password", p);
	}


	@Override
	public boolean isSyncWithRecentsDisabled() {
		Maybe<String> property = getProperty("skype.neverSyncWithRecentChats");
		if (property.unbox() == null) {
			return false;
		}
		return Boolean.parseBoolean(property.unbox());
	}
	
	@Override
	public Class<? extends Skype2StorageModuleCommons> getSelectedRecorder() {
		Maybe<String> selectedRecorder = getProperty("skype2gmail.selectedRecorder");
		if (selectedRecorder.unbox() == null) {
			return Skype2GmailModule.class;
		}
		return getModuleClassOrCry(selectedRecorder);
	}
	
	@Override
	public void setSelectedRecorderModule(
			Class<? extends Skype2StorageModuleCommons> recorderModuleClass) {
		setProperty("skype2gmail.selectedRecorder", recorderModuleClass.getName());
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Skype2StorageModuleCommons> getModuleClassOrCry(Maybe<String> selectedRecorder) {
		try {
			return (Class<? extends Skype2StorageModuleCommons>) Class.forName(selectedRecorder.unbox());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	
	private Maybe<String> getProperty(String key) {
		if (config == null) {
			readConfiguration();
		}
		String property = config.getProperty(key);
		return new Maybe<String>(property);
	}

	private void setProperty(String key, String value) {
		if (config == null) {
			readConfiguration();
		}
		config.setProperty(key, value);
		save();
	}

	private void save() {
		final File file = configDir.getConfigFile();
		try {
			config.store(new FileOutputStream(file), "Skype2Gmail configuration");
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}

	private void readConfiguration() {
		try {
			final File file = configDir.getConfigFile();
			if (!file.exists()) {
				FileUtils.touch(file);
			}
			config = new Properties();
			config.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new ApplicationException(e);
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
	}


}
