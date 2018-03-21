package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifynumberofpages extends AbstractCommand {

	public Verifynumberofpages(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().numberOfPages == Integer.parseInt(target)) {
			tResp.setCode("0");
			tResp.setMessage("Document has expected amount of pages: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected amount of pages: " + target + " but found: " + CurrentDocument.getInstance().getDoc().numberOfPages);
		}
		return tResp;
	}
}
