package org.vebqa.vebtal.pdf;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Verifyauthor extends AbstractCommand {

	public Verifyauthor(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {

		Response tResp = new Response();

		if (PdfResource.current.author.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found author: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected author: " + target + " but found: " + PdfResource.current.author);
		}
		return tResp;
	}
}
