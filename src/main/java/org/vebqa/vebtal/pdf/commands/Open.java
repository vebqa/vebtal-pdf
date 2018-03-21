package org.vebqa.vebtal.pdf.commands;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;
import org.vebqa.vebtal.pdf.PDF;

public class Open extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(Open.class);
	
	public Open(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl() {
		
		try {
			CurrentDocument.getInstance().setDoc(new PDF(new File(this.target)));
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing.", e);
		}
		logger.info("PDF successfully opend with {} Pages. ", CurrentDocument.getInstance().getDoc().numberOfPages);
		
		Response tResp = new Response();

		if ( CurrentDocument.getInstance().getDoc().content == null ) {
			tResp.setCode("1");
			tResp.setMessage("Cannot open pdf: " + this.target);
		} else {
			tResp.setCode("0");
			tResp.setMessage("SUT file successfully read.");
		}
		return tResp;
	}
}
