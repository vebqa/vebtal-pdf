package org.vebqa.vebtal.pdf;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.asserts.VerifyTextAssert;
import org.vebqa.vebtal.pdf.asserts.VerifyTextByAreaAssert;

public class PDFDriverTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Ignore
	@Test
	public void failWhileFileNotFound() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("No document loaded!");

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf"))
				.hasText("FindMe!").check();
	}

	@Ignore
	@Test
	public void failToOpenInvalidFile() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("No document loaded!");
		
		VerifyTextByAreaAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/InvalidFile.pdf"))
				.hasText("FindMe!").atPage(1).inArea(390, 220, 25, 15).check();
	}

}