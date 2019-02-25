package org.vebqa.vebtal.pdf.asserts;

import java.nio.file.NoSuchFileException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class FileNotFoundTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Ignore("Find a way to test loading in before method of the rule")
	@Test
	public void failBecauseFileDoesNotExists() {
		thrown.expect(NoSuchFileException.class);
		PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf");
	}
	
}