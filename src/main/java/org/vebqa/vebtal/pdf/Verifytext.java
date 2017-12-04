package org.vebqa.vebtal.pdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class Verifytext extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(Verifytext.class);

	public Verifytext(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	/**
	 * | command | target | value | 
	 * 
	 * | verifyText | | text | 
	 * 
	 * | verifyText | page=n | text |
	 */
	@Override
	public Response executeImpl(PDF current) {

		Response tResp = new Response();

		if (target == null || target.contentEquals("")) {
			Matcher<PDF> matcher = PDF.containsText(value);

			if (PdfResource.current == null) {
				tResp.setCode("1");
				tResp.setMessage("No SUT loaded yet. Cannot test against null.");
			} else if (matcher.matches(PdfResource.current)) {
				tResp.setCode("0");
				tResp.setMessage("Successfully found text: " + value);
			} else {
				tResp.setCode("1");
				tResp.setMessage("Cannot find text: " + value);
			}
		} else {
			// resolve target
			String[] token = target.split("=");
			// token[0] ist page
			String pageText = null;
			try {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(Integer.parseInt(token[1]));
				stripper.setEndPage(Integer.parseInt(token[1]));
				
				InputStream inputStream = new ByteArrayInputStream(PdfResource.current.content);
				PDDocument pdf = PDDocument.load(inputStream);
				pageText = stripper.getText(pdf);
			} catch (IOException e) {
				logger.error("Error while stripping text from pdf document!", e);
			}
			if (pageText != null && pageText.contains(value)) {
				tResp.setCode("0");
				tResp.setMessage("Expected text: " + value + " found at page: " + token[1]);
			} else {
				tResp.setCode("1");
				tResp.setMessage("Did not found expected text: " + value + " at page: " + token[1]);

			}
		}
		return tResp;
	}
}