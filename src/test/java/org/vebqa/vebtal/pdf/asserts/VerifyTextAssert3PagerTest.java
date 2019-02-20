package org.vebqa.vebtal.pdf.asserts;

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
	
	@Test(expected = AssertionError.class)
	public void failFindingSpecificTextAtPage_1() {
		VerifyTextAssert.assertThat(dut).hasText("Marker Page 2").atPage(1).check();
	}
}