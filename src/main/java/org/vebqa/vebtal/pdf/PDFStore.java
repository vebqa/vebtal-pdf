package org.vebqa.vebtal.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFStore {

	public static final Logger logger = LoggerFactory.getLogger(PDFStore.class);
	
	private static final PDFStore store = new PDFStore();
	
	private PDFDriver pdfDriver = new PDFDriver();
	
	public PDFStore() {
		logger.debug("PDF Store created");
	}
	
	public static PDFStore getStore() {
		return store;
	}
	
	public PDFDriver getDriver() {
		return pdfDriver;
	}
}
