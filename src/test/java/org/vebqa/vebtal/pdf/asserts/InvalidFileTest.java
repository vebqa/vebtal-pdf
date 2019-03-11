package org.vebqa.vebtal.pdf.asserts;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.vebqa.vebtal.pdf.PDFDriver;

public class InvalidFileTest {

	@Test
	public void failBecauseFileIsInvalid() {

		try {
			VerifyTextByAreaAssert
					.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/InvalidFile.pdf").load())
					.hasText("This is a text.").atPage(1).inArea(390, 220, 25, 15).check();
			assertFalse(true);
		} catch (IOException e) {
			assertTrue(true);
		}
	}

}