package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Close extends AbstractCommand {

	public Close(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {
		
		CurrentDocument.getInstance().setDoc(null);
		
		Response tResp = new Response();

		tResp.setCode("0");
		tResp.setMessage("Successfully removes SUT from memory.");
		return tResp;
	}
}
