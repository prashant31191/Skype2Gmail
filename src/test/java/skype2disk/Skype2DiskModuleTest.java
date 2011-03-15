package skype2disk;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import skype.SkypeHistoryRecorder;
import skype.mocks.Skype2DiskModuleMockingSkypeApi;
import testutils.IOHelper;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Skype2DiskModuleTest {

	@Test
	public void testInjections() {
			final Skype2DiskModule skype2DiskModule = new Skype2DiskModule("");
			final Injector injector = Guice.createInjector(skype2DiskModule);
	
			injector.getInstance(SkypeHistoryRecorder.class);
	}
	
	@Test
	public void testModuleMockingSkypeApi() throws IOException
	{
		File tempTarget = IOHelper.createTempDirOrCry();
		try {
			final Injector injector = Guice.createInjector(new Skype2DiskModuleMockingSkypeApi(tempTarget.getAbsolutePath()));
			final SkypeHistoryRecorder historyRecorder = injector.getInstance(SkypeHistoryRecorder.class);
			
			historyRecorder.record();
			
			final String fileList = IOHelper.getSortedFilesAsString(tempTarget);
			
			final String expectedFileList = 
					"foo42\n" + 
					"foo43";
			Assert.assertEquals(expectedFileList, fileList);
			
			final File foo42 = new File(tempTarget, "foo42");
			String foo42Contents = FileUtils.readFileToString(foo42);
			
			String expectedFoo42Contents = 
					"Chat Id: $foo#42;\n" + 
					"Chat Time: 2011/03/21 15:00:00\n" + 
					"Chat Body Signature: 5#be2fea13a55a0ca638353b71d3f6d35ac94fecceb7d3ce40b99432361356b9a4\n" + 
					"Messages signatures: [41dfeecec52c4e55bc7301a007539c03,4fa1d6184236ac761356a8fac3daafa4]\n" + 
					"Chat topic: foo\n" + 
					"Poster: id=joe; display=JOE\n" + 
					"Poster: id=moe; display=MOE\n" + 
					"[2011/03/21 15:01:00] JOE: howdy\n" + 
					"[2011/03/21 15:02:00] MOE: hiiya";
			Assert.assertEquals(expectedFoo42Contents, foo42Contents);
		}finally
		{
			tempTarget.delete();
		}
	}
}
