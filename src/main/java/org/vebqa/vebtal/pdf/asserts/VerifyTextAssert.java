package org.vebqa.vebtal.pdf.asserts;

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

/**
 * Special assertion class - inherits from AbstractAssert!
 * @author doerges
 *
 */
public class VerifyTextAssert extends AbstractAssert<VerifyTextAssert, String> {

	private static final Logger logger = LoggerFactory.getLogger(VerifyTextAssert.class);
	
	private PDF current;
	private int page = -1;
	private String textToFind = null;

	/**
	 * Constructor assertion class, PDF filename ist the object we want to make assertions on.
	 * @param aFileName
	 */
	public VerifyTextAssert(String aPdfToTest) {
		super(aPdfToTest, VerifyTextAssert.class);
	}	
	
    /**
     * A fluent entry point to our specific assertion class, use it with static import. 
     * @param anActualImageFile
     * @return
     */
    public static VerifyTextAssert assertThat(String aPdfToTest) {
        return new VerifyTextAssert(aPdfToTest);
    }
    
    /**
     * Part of assertion - configure
     * @param someText
     * @return
     */
    public VerifyTextAssert hasText(String someText) {
    	this.textToFind = someText;
    	return this;
    }

    /**
     * Part of assertion - configure
     * @param someText
     * @return
     */
    public VerifyTextAssert atPage(int aPageNumber) {
    	this.page = aPageNumber;
    	return this;
    }

    /**
     * A specific assertion
     * @param someText
     * @return
     */
    public VerifyTextAssert check() {
    	// check that we really have a pdf filename defined.
    	isNotNull();
    	
		try {
			this.current = new PDF(new File(actual));
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing.", e);
			failWithMessage("Cannot open pdf file <%s> for testing", actual);
		}
		logger.info("PDF successfully opend with {} Pages. ", this.current.numberOfPages);

    	String pageText = "";
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			if (page != -1) {
			stripper.setStartPage(page);
			stripper.setEndPage(page);
			}
			InputStream inputStream = new ByteArrayInputStream(this.current.content);
			PDDocument pdf = PDDocument.load(inputStream);
			pageText = stripper.getText(pdf);
			
			pdf.close();
		} catch (IOException e) {
			logger.error("Error while stripping text from pdf document!", e);
			failWithMessage("Cannot read data from file <%s>, maybe it is invalid!", actual);
		}
		if (pageText == null) {
			failWithMessage("Expected text is <%s> but was null", this.textToFind);
		}
		if (!pageText.contains(this.textToFind)) {
			failWithMessage("Expected text is <%s> but was <%s>", this.textToFind, pageText);
		}
		
		return this;
    }
}
