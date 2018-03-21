package org.vebqa.vebtal.pdf;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Verifysubject extends AbstractCommand {

	public Verifysubject(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {

		Response tResp = new Response();

		if (PdfResource.current.subject.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found subject: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected subject: " + target + " but found: |" + PdfResource.current.subject + "|");
		}
		return tResp;
	}
}
