package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifysubject extends AbstractCommand {

	public Verifysubject(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object aDocument) {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().subject == null) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Document does not have a title. Attribute is null!");
			return tResp;
		}
		
		if (CurrentDocument.getInstance().getDoc().subject.contains(target)) {
			tResp.setCode(Response.PASSED);
			tResp.setMessage("Successfully found subject: " + target);
		} else {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected subject: " + target + " but found: |" + CurrentDocument.getInstance().getDoc().subject + "|");
		}
		return tResp;
	}
}
