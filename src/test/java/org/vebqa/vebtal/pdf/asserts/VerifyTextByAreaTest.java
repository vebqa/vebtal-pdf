package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.Matchers.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.vebqa.vebtal.pdf.PDFResource;

@RunWith(BlockJUnit4ClassRunner.class)
public class VerifyTextByAreaTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public PDFResource dut = new PDFResource().loadDocument("./src/test/java/resource/Testtext_Area.pdf");

	@Test
	public void checkThatTextIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(350, 220, 65, 15).check();
	}

	@Test(expected = AssertionError.class)
	public void failBecausTextIsNotAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(1, 1, 65, 15).check();
	}

	@Test
	public void checkThatTextFragmentIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat(dut).hasText("text.").atPage(1).inArea(390, 220, 25, 15).check();
	}

	@Test
	public void failBecauseOnlyTextFragmentIsAvailabeInSpecificArea() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(containsString("text."));

		VerifyTextByAreaAssert.assertThat(dut).hasText("This is a text.").atPage(1).inArea(390, 220, 25, 15).check();
	}

}
