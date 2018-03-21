package org.vebqa.vebtal.pdf;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Verifynumberofpages extends AbstractCommand {

	public Verifynumberofpages(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {

		Response tResp = new Response();

		if (PdfResource.current.numberOfPages == Integer.parseInt(target)) {
			tResp.setCode("0");
			tResp.setMessage("Document has expected amount of pages: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected amount of pages: " + target + " but found: " + PdfResource.current.numberOfPages);
		}
		return tResp;
	}
}
