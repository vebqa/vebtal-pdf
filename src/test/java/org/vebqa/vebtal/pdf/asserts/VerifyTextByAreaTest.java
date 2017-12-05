package org.vebqa.vebtal.pdf.asserts;

import static org.hamcrest.Matchers.containsString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class VerifyTextByAreaTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	
	@Test
	public void failBecauseOnlyTextFragmentIsAvailabeInSpecificArea() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage(containsString("text."));

		VerifyTextByAreaAssert.assertThat("./src/test/java/resource/Testtext_Area.pdf").hasText("This is a text.")
				.atPage(1).inArea(390, 220, 25, 15).check();
		
	}	
	
}
