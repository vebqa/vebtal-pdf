package org.vebqa.vebtal.pdf.asserts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class FileNotFoundTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Ignore
	@Test
	public void failWhileFileDoesNotExists() {

		try {
			assertTrue(new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf").load().isLoaded());
		} catch (IOException e) {
			assertFalse("File could not be loaded!", true);
		}

	}

}