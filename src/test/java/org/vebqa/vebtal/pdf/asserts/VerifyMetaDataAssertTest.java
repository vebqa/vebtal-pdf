package org.vebqa.vebtal.pdf.asserts;

import org.junit.Test;

public class VerifyMetaDataAssertTest {

	@Test
	public void checkThatDocumentHas_1_Page() {
		VerifyMetaDataAssert.assertThat("./src/test/java/resource/LoremIpsum500.pdf").hasNumberOfPages(1);
	}
	
	@Test
	public void checkThatDocumentHas_3_Pages() {
		VerifyMetaDataAssert.assertThat("./src/test/java/resource/LoremIpsum_3Pages.pdf").hasNumberOfPages(3);
	}

	@Test(expected = AssertionError.class)
	public void checkFailThatDocumentHas_3_Pages() {
		VerifyMetaDataAssert.assertThat("./src/test/java/resource/LoremIpsum500.pdf").hasNumberOfPages(3);
	}
	
	
}
