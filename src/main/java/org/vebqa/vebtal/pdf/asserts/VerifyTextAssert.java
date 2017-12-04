package org.vebqa.vebtal.pdf.asserts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.assertj.core.api.AbstractAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.pdf.PDF;
import org.vebqa.vebtal.pdfrestserver.PdfResource;

public class VerifyTextAssert  extends AbstractAssert<VerifyTextAssert, String> {

	private static final Logger logger = LoggerFactory.getLogger(VerifyTextAssert.class);
	
	private int page = -1;
	private String fileName = null;
	private String textToFind = null;
	
	public VerifyTextAssert(String aFileName, Class<?> selfType) {
		super(aFileName, selfType);
	}
	
	public VerifyTextAssert(String aFileName) {
		super(aFileName, VerifyTextAssert.class);
		this.fileName = aFileName;
	}

    /**
     * A fluent entry point to our specific assertion class, use it with static import. 
     * @param anActualImageFile
     * @return
     */
    public static VerifyTextAssert assertThat(String aPdfToTest) {
        return new VerifyTextAssert(aPdfToTest);
    }
    
    public VerifyTextAssert hasText(String someText) {
    	this.textToFind = someText;
    	return this;
    }

    public VerifyTextAssert atPage(int aPageNumber) {
    	this.page = aPageNumber;
    	return this;
    }
    
    public void check() {
    	isNotNull();
    	
		try {
			PdfResource.current = new PDF(new File(actual));
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing.", e);
		}
		logger.info("PDF successfully opend with {} Pages. ", PdfResource.current.numberOfPages);
		

    	String pageText = "";
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			if (page != -1) {
			stripper.setStartPage(page);
			stripper.setEndPage(page);
			}
			InputStream inputStream = new ByteArrayInputStream(PdfResource.current.content);
			PDDocument pdf = PDDocument.load(inputStream);
			pageText = stripper.getText(pdf);
		} catch (IOException e) {
			logger.error("Error while stripping text from pdf document!", e);
		}
		if (pageText != null && pageText.contains(this.textToFind)) {
			assertTrue("Document contains text", true);
		} else {
			assertFalse("Document does not contain text", true);
		}
    }
}
