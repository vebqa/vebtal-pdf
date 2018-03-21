package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifyauthor extends AbstractCommand {

	public Verifyauthor(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().author.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found author: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected author: " + target + " but found: " + CurrentDocument.getInstance().getDoc().author);
		}
		return tResp;
	}
}