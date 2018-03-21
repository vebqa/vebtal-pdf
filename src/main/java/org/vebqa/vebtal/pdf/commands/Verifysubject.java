package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifysubject extends AbstractCommand {

	public Verifysubject(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().subject.contains(target)) {
			tResp.setCode("0");
			tResp.setMessage("Successfully found subject: " + target);
		} else {
			tResp.setCode("1");
			tResp.setMessage("Expected subject: " + target + " but found: |" + CurrentDocument.getInstance().getDoc().subject + "|");
		}
		return tResp;
	}
}
