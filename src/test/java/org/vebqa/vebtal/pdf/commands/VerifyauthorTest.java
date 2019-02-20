package org.vebqa.vebtal.pdf.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.PDFDriver;

public class VerifyauthorTest {

	@Rule
	public final PDFDriver dut = new PDFDriver().loadDocument("./src/test/java/resource/LoremIpsum_3Pages.pdf");
	
	@Test
	public void verifyAuthorWithExistingName() {
		// create command to test
		Verifyauthor cmd = new Verifyauthor("verifyAuthor", "Dörges, Karsten", "");
		Response result = cmd.executeImpl(dut);
		
		// create a green result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Successfully found author: Dörges, Karsten");
		
		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
	}
}
