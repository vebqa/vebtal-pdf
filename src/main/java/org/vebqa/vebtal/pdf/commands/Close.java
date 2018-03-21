package org.vebqa.vebtal.pdf;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Close extends AbstractCommand {

	public Close(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {
		
		PdfResource.current = null;
		
		Response tResp = new Response();

		tResp.setCode("0");
		tResp.setMessage("Successfully removes SUT from memory.");
		return tResp;
	}
}
