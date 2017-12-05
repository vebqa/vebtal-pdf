package org.vebqa.vebtal.pdf.asserts;

import org.junit.Test;

public class VerifyTextAssertTest {

	@Test
	public void findSomeTextSomewhere() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum500.pdf").hasText("Duis autem").check();
	}
	
	@Test(expected = AssertionError.class)
	public void checkThatSomeTextIsNotAvailable() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum500.pdf").hasText("Duis autem Entenhausen").check();
	}
	
	@Test
	public void findSpecificTextAtPage_1() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum_3Pages.pdf").hasText("Marker Page 1").atPage(1).check();
	}

	@Test
	public void findSpecificTextAtPage_2() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum_3Pages.pdf").hasText("Marker Page 2").atPage(2).check();
	}	
	
	@Test
	public void findSpecificTextAtPage_3() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum_3Pages.pdf").hasText("Marker Page 3").atPage(3).check();
	}
	
	@Test(expected = AssertionError.class)
	public void failFindingSpecificTextAtPage_1() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum_3Pages.pdf").hasText("Marker Page 2").atPage(1).check();
	}	
	
	@Test(expected = AssertionError.class)
	public void failBecauseFileDoesNotExists() {
		VerifyTextAssert.assertThat("./src/test/java/resource/FileNotExisting.pdf").hasText("FindMe!").check();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void failBecauseFileIsInvalid() {
		VerifyTextAssert.assertThat("./src/test/java/resource/InvalidFile.pdf").hasText("FindMe!").check();
	}
	
}