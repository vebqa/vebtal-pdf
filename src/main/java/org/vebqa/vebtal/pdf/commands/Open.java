package org.vebqa.vebtal.pdf.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

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

		Response tResp = new Response();
		boolean successfullyLoaded = false;
		try {
			CurrentDocument.getInstance().setDoc(new PDF(new File(this.target)));
			successfullyLoaded = true;
		} catch (NoSuchFileException e) {
			logger.error("File not found: {}.", e.getMessage());
			tResp.setCode("1");
			tResp.setMessage("File not found: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing: {}", e.getMessage());
			tResp.setCode("1");
			tResp.setMessage(e.getMessage());
		}
		
		if ( successfullyLoaded && CurrentDocument.getInstance().getDoc().content == null ) {
			tResp.setCode("1");
			tResp.setMessage("Cannot process pdf: " + this.target);
		} else if (successfullyLoaded) {
			tResp.setCode("0");
			tResp.setMessage("SUT file successfully read.");
			logger.info("PDF successfully opend with {} Pages. ", CurrentDocument.getInstance().getDoc().numberOfPages);
		}
		return tResp;
	}
}
