package org.vebqa.vebtal.pdf.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class VerifyTextAssertTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/LoremIpsum500.pdf");
	
	@Test
	public void findSomeTextSomewhere() {
		VerifyTextAssert.assertThat(dut).hasText("Duis autem").check();
	}
	
	@Test(expected = AssertionError.class)
	public void checkThatSomeTextIsNotAvailable() {
		VerifyTextAssert.assertThat(dut).hasText("Duis autem Entenhausen").check();
	}
	
//	
//	@Test(expected = AssertionError.class)
//	public void failBecauseFileDoesNotExists() {
//		VerifyTextAssert.assertThat("./src/test/java/resource/FileNotExisting.pdf").hasText("FindMe!").check();
//	}
//	
//	@Test
//	public void failBecauseFileIsInvalid() {
//		thrown.expect(AssertionError.class);
//		thrown.expectMessage(containsString("Cannot read data from file"));
//		
//		VerifyTextAssert.assertThat("./src/test/java/resource/InvalidFile.pdf").hasText("FindMe!").check();
//	}
	
}