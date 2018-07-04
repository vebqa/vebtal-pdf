package org.vebqa.vebtal.pdf.commands;

import org.vebqa.vebtal.GuiManager;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;
import org.vebqa.vebtal.pdfrestserver.PdfTestAdaptionPlugin;
import org.vebqa.vebtal.sut.SutStatus;

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
		
		GuiManager.getinstance().setTabStatus(PdfTestAdaptionPlugin.ID, SutStatus.DISCONNECTED);
		
		return tResp;
	}
}
