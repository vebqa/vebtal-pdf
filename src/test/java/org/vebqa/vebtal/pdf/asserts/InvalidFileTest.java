package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.core.StringStartsWith.startsWith;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class InvalidFileTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

//	@Test
	public void failBecauseFileIsInvalid() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(startsWith("Cannot read data from file <"));

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/InvalidFile.pdf"));
	}

}