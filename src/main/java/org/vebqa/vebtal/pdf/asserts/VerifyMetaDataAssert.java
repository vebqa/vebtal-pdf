package org.vebqa.vebtal.pdf.asserts;

import java.io.File;
import java.io.IOException;

import org.assertj.core.api.AbstractAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.pdf.PDF;

/**
 * Special assertion class - inherits from AbstractAssert!
 * @author doerges
 *
 */
public class VerifyMetaDataAssert extends AbstractAssert<VerifyMetaDataAssert, String> {

	private static final Logger logger = LoggerFactory.getLogger(VerifyMetaDataAssert.class);
	
	private PDF current;

	/**
	 * Constructor assertion class, PDF filename ist the object we want to make assertions on.
	 * @param aFileName
	 */
	public VerifyMetaDataAssert(String aPdfToTest) {
		super(aPdfToTest, VerifyMetaDataAssert.class);
		
		// Load and analze pdf
		try {
			this.current = new PDF(new File(actual));
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing.", e);
			failWithMessage("Cannot open pdf file <%s> for testing", actual);
		}
		logger.info("PDF successfully opend with {} Pages. ", this.current.numberOfPages);		
	}	
	
    /**
     * A fluent entry point to our specific assertion class, use it with static import. 
     * @param anActualImageFile
     * @return
     */
    public static VerifyMetaDataAssert assertThat(String aPdfToTest) {
        return new VerifyMetaDataAssert(aPdfToTest);
    }
    
    /**
     * A specific assertion
     * @param someText
     * @return
     */
    public VerifyMetaDataAssert hasNumberOfPages(int pages) {
    	// check that we really have a pdf filename defined.
    	isNotNull();

		if (current.numberOfPages != pages) {
			failWithMessage("Expected text is <%s> but was <%s>", this.current.numberOfPages, pages);
		}
		
		return this;
    }
    
    
}
