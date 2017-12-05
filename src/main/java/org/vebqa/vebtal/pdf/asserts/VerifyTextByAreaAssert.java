package org.vebqa.vebtal.pdf.asserts;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.assertj.core.api.AbstractAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.pdf.Area;
import org.vebqa.vebtal.pdf.PDF;

/**
 * Special assertion class - inherits from AbstractAssert!
 * @author doerges
 *
 */
public class VerifyTextByAreaAssert extends AbstractAssert<VerifyTextByAreaAssert, String> {

	private static final Logger logger = LoggerFactory.getLogger(VerifyTextByAreaAssert.class);
	
	private PDF current;
	private String text; 
	private int pageNumber;
	private Area area;
	private int areaX;
	private int areaY;
	private int areaWidth;
	private int areaHeight;

	/**
	 * Constructor assertion class, PDF filename ist the object we want to make assertions on.
	 * @param aFileName
	 */
	public VerifyTextByAreaAssert(String aPdfToTest) {
		super(aPdfToTest, VerifyTextByAreaAssert.class);
		
		// Load and analyze pdf
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
    public static VerifyTextByAreaAssert assertThat(String aPdfToTest) {
        return new VerifyTextByAreaAssert(aPdfToTest);
    }

    /**
     * A configuration
     * @param someText
     * @return
     */
    public VerifyTextByAreaAssert hasText(String someText) {
    	this.text = someText;
    	
		return this;
    }    
    
    /**
     * A configuration
     * @param someText
     * @return
     */
    public VerifyTextByAreaAssert inArea(int x, int y, int width, int height) {
    	this.areaX = x;
    	this.areaY = y;
    	this.areaWidth = width;
    	this.areaHeight = height;
    	
		return this;
    }
    
    /**
     * A configuration
     * @param someText
     * @return
     */
    public VerifyTextByAreaAssert atPage(int pageNumber) {
		this.pageNumber = pageNumber;
		
		return this;
    }
    
    /**
     * A specific assertion
     * @param someText
     * @return
     */
    public VerifyTextByAreaAssert check() {
    	// check that we really have a pdf filename defined.
    	isNotNull();

    	this.area = new Area(this.pageNumber, this.areaX, this.areaY, this.areaHeight, this.areaWidth);
    	
    	String areaText = null;
		try {
			PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
			textStripper.setSortByPosition(true);
			textStripper.addRegion("test", area.getRectangle());

			InputStream inputStream = new ByteArrayInputStream(this.current.content);
			PDDocument pdf = PDDocument.load(inputStream);
			PDPage page = pdf.getPage(area.getPage());
			textStripper.extractRegions(page);
			areaText = textStripper.getTextForRegion("test");
			logger.info("extracted text from area: {}", areaText);
		} catch (IOException e) {
			failWithMessage("Cannot extract text from area!");
		}    	
    	
		if (!areaText.contains(this.text)) {
			failWithMessage("Expected text is <%s> but it is not availabe in <%s>", this.text, areaText);
		}
		
		return this;
    }    
}
