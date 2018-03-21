package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifytitle extends AbstractCommand {

	public Verifytitle(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().title.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found title: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected title: " + target + " but found: " + CurrentDocument.getInstance().getDoc().title);
		}
		return tResp;
	}
}
