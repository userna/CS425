package testCase;

import org.junit.Assert;
import org.junit.Test;
import contract.DistributedGrepCommand;
public class TestCaseForCommands {

	@Test
	public void testGettingFileNameCommand() {
		DistributedGrepCommand command = new DistributedGrepCommand("grep -v ok logfile");
		Assert.assertEquals("logfile",command.getLogfile());
		Assert.assertEquals("ok", command.getValue());
		Assert.assertNull(command.getKey());
	}
	@Test
	public void testGettingFileNameLongCommand() {
		DistributedGrepCommand command = new DistributedGrepCommand("grep -v ok -k ok logfile");
		Assert.assertEquals("logfile",command.getLogfile());
		Assert.assertEquals("ok", command.getValue());
		Assert.assertEquals("ok", command.getKey());
	}
}
