package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.core.StringStartsWith.startsWith;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.pdf.PDFDriver;

public class VerifyTextAssert3PagerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Document to test (dut)
	 */
	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/LoremIpsum_3Pages.pdf");

	@Test
	public void findSpecificTextAtPage_1() {
		VerifyTextAssert.assertThat(dut).hasText("Marker Page 1").atPage(1).check();
	}

	@Test
	public void findSpecificTextAtPage_2() {
		VerifyTextAssert.assertThat(dut).hasText("Marker Page 2").atPage(2).check();
	}

	@Test
	public void findSpecificTextAtPage_3() {
		VerifyTextAssert.assertThat(dut).hasText("Marker Page 3").atPage(3).check();
	}

	@Test
	public void failFindingSpecificTextOnGivenPage() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(startsWith("Expected text <Marker Page 2> not found in the content <"));

		VerifyTextAssert.assertThat(dut).hasText("Marker Page 2").atPage(1).check();
	}

	@Test
	public void failWhileFileDoesNotExist() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(startsWith("File does not exist."));

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/FileNotExisting.pdf"))
				.hasText("Marker Page 1").atPage(1).check();
	}

//	@Test
	public void failWhileFileIsInvalid() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(startsWith("Cannot read data from file <"));

		VerifyTextAssert.assertThat(new PDFDriver().loadDocument("./src/test/java/resource/InvalidFile.pdf"))
				.hasText("Marker Page 1").atPage(1).check();
	}

}