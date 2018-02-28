package org.vebqa.vebtal.pdf.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFResource;

public class VerifyMetaDataAssertTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public final PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/LoremIpsum_3Pages.pdf");
	
	@Test
	public void checkThatDocumentHas_3_Pages() {
		VerifyMetaDataAssert.assertThat(dut).hasNumberOfPages(3);
	}
	
	@Test
	public void checkThatDocumentHasGivenAuthor() {
		VerifyMetaDataAssert.assertThat(dut).hasAuthor("Dörges, Karsten");
	}
	
	@Test
	public void checkThatDocumentHasGivenCreator() {
		VerifyMetaDataAssert.assertThat(dut).hasCreator("Microsoft® Word 2010");
	}
	
	@Test
	public void checkThatDocumentHasGivenTitle() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("Expected title is <uhm> but there is no title object.");

		VerifyMetaDataAssert.assertThat(dut).hasTitle("uhm");
	}
}
