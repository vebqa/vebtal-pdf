package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.core.StringStartsWith.startsWith;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.vebqa.vebtal.pdf.PDFDriver;

@RunWith(BlockJUnit4ClassRunner.class)
public class VerifyTextByAreaPlainModeTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Document to test (dut)
	 */
	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/Testtext_Area.pdf");

	@Test
	public void checkThatTextIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(350, 220, 65, 15).check();
	}

	@Test(expected = AssertionError.class)
	public void failBecauseTextIsNotAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(1, 1, 65, 15).check();
	}

	@Test
	public void checkThatTextFragmentIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("text.").atPage(1).inArea(390, 220, 25, 15).check();
	}

	@Test
	public void failBecauseOnlyTextFragmentIsAvailabeInSpecificArea() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(
				startsWith("Expected text <This is a text.> is not availabe in the located area. Instead found: <"));

		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(390, 220, 25, 15).check();
	}

}
