package org.vebqa.vebtal.pdf.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class FileNotFoundTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf");
		
	@Test
	public void failBecauseFileDoesNotExists() {
		thrown.expect(AssertionError.class);
		VerifyTextAssert.assertThat(dut).hasText("FindMe!").check();
	}
	
}