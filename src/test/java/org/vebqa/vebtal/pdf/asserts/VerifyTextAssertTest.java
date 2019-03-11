package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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

	@Test
	public void checkThatSomeTextIsNotAvailable() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(startsWith("Expected text <Duis autem Entenhausen> not found in the content <"));

		VerifyTextAssert.assertThat(dut).hasText("Duis autem Entenhausen").check();
	}

	@Test
	public void failWhileFileDoesNotExist() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("No document loaded!");

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf"))
				.hasText("FindMe!").check();
	}

	@Test
	public void failWhileFileIsEmpty() {

		try {
			VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/EmptyFile.pdf").load())
					.hasText("FindMe!").check();
			assertFalse("AssertionError expected.", true);
		} catch (AssertionError e) {
			assertTrue(e.getMessage() == "Expected text <FindMe!> not found in the content < \r\n>.");
		} catch (IOException e) {
			assertFalse("Got an IOException, but expected an AssertionError.", true);
		}
	}

}