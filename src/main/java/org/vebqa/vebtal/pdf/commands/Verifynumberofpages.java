package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifynumberofpages extends AbstractCommand {

	public Verifynumberofpages(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object aDocument) {

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
