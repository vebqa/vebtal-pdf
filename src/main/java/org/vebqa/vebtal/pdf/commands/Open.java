package org.vebqa.vebtal.pdf;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Open extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(Open.class);
	
	public Open(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	@Override
	public Response executeImpl(PDF current) {
		
		try {
			PdfResource.current = new PDF(new File(this.target));
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing.", e);
		}
		logger.info("PDF successfully opend with {} Pages. ", PdfResource.current.numberOfPages);
		
		Response tResp = new Response();

		if ( PdfResource.current.content == null ) {
			tResp.setCode("1");
			tResp.setMessage("Cannot open pdf: " + this.target);
		} else {
			tResp.setCode("0");
			tResp.setMessage("SUT file successfully read.");
		}
		return tResp;
	}
}
