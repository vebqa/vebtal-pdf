package org.vebqa.vebtal.pdf;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFResource extends ExternalResource {
	
	private static final Logger logger = LoggerFactory.getLogger(PDFResource.class);
	
	private PDF current;
	private PDDocument pdd;
	
	private String pathToResource;
	
	/**
	 * Standard Constructor
	 */
	public PDFResource() {
		this.pathToResource = null;
		this.current = null;
		this.pdd = null;
	}

	public PDFResource loadDocument(String aPathToDoc) {
		this.pathToResource = aPathToDoc;
		
		return this;
	}
	
	/**
	 * initialize resource before testcase.
	 */
	@Override
	protected void before() throws Throwable {
		try {
			this.current = new PDF(new File(this.pathToResource));
			InputStream inputStream = new ByteArrayInputStream(this.current.content);
			this.pdd = PDDocument.load(inputStream);
			inputStream.close();
			logger.info("PDF successfully opend with {} Pages. ", this.current.numberOfPages);
		} catch (IOException e) {
			logger.error("Cannot open pdf for testing, but i will inform later.", e);
		}
	}

	/**
	 * Clean up resource after testcase.
	 */
	@Override
	protected void after() {
		if (this.pdd == null) {
			return;
		}
		try {
			this.pdd.close();
		} catch (IOException e) {
			logger.error("Error while closing document.", e);
		}
	}
	
	public PDDocument getDocument() {
		return this.pdd;
	}
}
