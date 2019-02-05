package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;
import org.vebqa.vebtal.pdfrestserver.PdfTestAdaptionPlugin;

@Keyword(module = PdfTestAdaptionPlugin.ID, command = "verifyAuthor", hintTarget = "<string>")
public class Verifyauthor extends AbstractCommand {

	public Verifyauthor(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object aDocument) {

		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().author == null) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Document does not have a author value. Attribute is null!");
			return tResp;
		}		
		
		if (CurrentDocument.getInstance().getDoc().author.contains(target)) {
			tResp.setCode(Response.PASSED);
			tResp.setMessage("Successfully found author: " + target);
		} else {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected author: " + target + " but found: " + CurrentDocument.getInstance().getDoc().author);
		}
		return tResp;
	}
}
