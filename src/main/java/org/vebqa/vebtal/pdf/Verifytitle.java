package org.vebqa.vebtal.pdf;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Verifytitle extends AbstractCommand {

	public Verifytitle(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {

		Response tResp = new Response();

		if (PdfResource.current.title.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found title: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected title: " + target + " but found: " + PdfResource.current.title);
		}
		return tResp;
	}
}
