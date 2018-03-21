package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.Matchers.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.vebqa.vebtal.pdf.PDFResource;

@RunWith(BlockJUnit4ClassRunner.class)
public class VerifyTextByAreaPlainModeTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();


	@Test
	public void checkThatTextIsAvailabeInSpecificArea() {
		PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/Testtext_Area.pdf");
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(350, 220, 65, 15).check();

		dut.closeDocument();
	}

	@Test(expected = AssertionError.class)
	public void failBecausTextIsNotAvailabeInSpecificArea() {
		PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/Testtext_Area.pdf");
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(1, 1, 65, 15).check();

		// not reachable
		dut.closeDocument();
	}

	@Test
	public void checkThatTextFragmentIsAvailabeInSpecificArea() {
		PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/Testtext_Area.pdf");
		VerifyTextByAreaAssert.assertThat(dut).hasText("text.").atPage(1).inArea(390, 220, 25, 15).check();
		
		dut.closeDocument();
	}

	@Test
	public void failBecauseOnlyTextFragmentIsAvailabeInSpecificArea() {
		PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/Testtext_Area.pdf");
		thrown.expect(AssertionError.class);
		thrown.expectMessage(containsString("text."));

		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(390, 220, 25, 15).check();
		
		// not reachable
		dut.closeDocument();
	}

}
