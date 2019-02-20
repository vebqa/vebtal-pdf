package org.vebqa.vebtal.pdf.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.PDFDriver;

public class VerifytitleTest {

	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/LoremIpsum_3Pages.pdf");
	
	@Test
	public void verifySubjectFailWithText() {
		// create command to test
		Verifytitle cmd = new Verifytitle("verifyTitle", "Uhm", "");
		Response result = cmd.executeImpl(dut);
		
		// create a green result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage("Document does not have a title. Attribute is null!");
		
		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
	}
}
