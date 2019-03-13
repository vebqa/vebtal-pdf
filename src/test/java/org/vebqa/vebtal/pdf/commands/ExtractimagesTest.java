package org.vebqa.vebtal.pdf.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.PDFDriver;

public class ExtractimagesTest {

	@Rule
	public final PDFDriver dut = new PDFDriver().setFilePath("./src/test/java/resource/SampleFileWithImage.pdf");

	@Rule
	public final PDFDriver dut_ni = new PDFDriver().setFilePath("./src/test/java/resource/SampleFile.pdf");

	@Test
	public void extractImages() {
		// create command to test
		Extractimages cmd = new Extractimages("extractImages", "page=1", "");
		Response result = cmd.executeImpl(dut);

		// create a green result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Successfully extracted 1 image(s) from page: 1");

		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
	}
	
	@Test
	public void extractNoImages() {
		// create command to test
		Extractimages cmd = new Extractimages("extractImages", "page=1", "");
		Response result = cmd.executeImpl(dut_ni);

		// create a green result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("No images found in page: 1");

		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
	}

}