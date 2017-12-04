package org.vebqa.vebtal.pdf.asserts;

import org.junit.Test;

public class VerifyTextAssertTest {

	@Test
	public void testHasTextSomewhere() {
		VerifyTextAssert.assertThat("./src/test/java/resource/LoremIpsum500.pdf").hasText("Duis autem").check();
	}
}