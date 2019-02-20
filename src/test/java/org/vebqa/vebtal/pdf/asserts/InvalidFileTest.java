package org.vebqa.vebtal.pdf.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class InvalidFileTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/InvalidFile.pdf");
		
	
	@Test
	public void failBecauseFileIsInvalid() {
		thrown.expect(AssertionError.class);
		
		VerifyTextAssert.assertThat(dut).hasText("FindMe!").check();
	}
	
}