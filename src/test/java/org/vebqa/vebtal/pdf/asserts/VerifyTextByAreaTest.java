package org.vebqa.vebtal.pdf.asserts;

import org.junit.Test;

public class VerifyTextByAreaTest {

	@Test
	public void checkThatTextIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat("./src/test/java/resource/Testtext_Area.pdf").hasText("This is a text.")
				.atPage(1).inArea(350, 220, 65, 15).check();
	}
	
	@Test(expected = AssertionError.class)
	public void failBecausTextIsNotAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat("./src/test/java/resource/Testtext_Area.pdf").hasText("This is a text.")
				.atPage(1).inArea(1, 1, 65, 15).check();
	}	
	
	@Test
	public void checkThatTextFragmentIsAvailabeInSpecificArea() {
		VerifyTextByAreaAssert.assertThat("./src/test/java/resource/Testtext_Area.pdf").hasText("text.")
				.atPage(1).inArea(390, 220, 25, 15).check();
	}	
}
