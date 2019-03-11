package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.core.StringStartsWith.startsWith;

import java.nio.file.NoSuchFileException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class FileNotFoundTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void failBecauseFileDoesNotExists() {
		thrown.expect(NoSuchFileException.class);
		thrown.expectMessage(startsWith("File does not exist."));

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf"));
	}

}