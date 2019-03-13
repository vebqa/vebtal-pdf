package org.vebqa.vebtal.pdf;

import static java.nio.file.Files.readAllBytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hamcrest.Matcher;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFDriver extends ExternalResource {

	private static final Logger logger = LoggerFactory.getLogger(PDFDriver.class);

	private boolean isSuccessfullyLoaded;

	private String pathToResource;

	private PDDocument document;

	private byte[] content;

	// Document Information
	public String text;
	public int numberOfPages;
	public String author;
	public Calendar creationDate;
	public String creator;
	public String keywords;
	public String producer;
	public String subject;
	public String title;
	public boolean encrypted;
	public boolean signed;
	public String signerName;
	public Calendar signatureTime;

	public PDFDriver() {
		this.isSuccessfullyLoaded = false;
		this.content = null;
	}

	public String getFilePath() {
		return this.pathToResource;
	}

	public PDFDriver setFilePath(String aPathToDoc) {
		this.pathToResource = aPathToDoc;
		return this;
	}

	public PDFDriver load() throws IOException {
		load(this.pathToResource, readAllBytes(Paths.get(this.pathToResource)));
		return this;
	}

	public void load(File pdfFile) throws IOException {
		load(pdfFile.getAbsolutePath(), readAllBytes(Paths.get(pdfFile.getAbsolutePath())));
	}

	public void load(URL url) throws IOException {
		load(url.toString(), readBytes(url));
	}

	private void load(String name, byte[] content) throws IOException {
		this.content = content;

		// separate metadata if available in
		// - Dublin Core
		// - Adobe PDF Schema
		// - XMP Basic Schema
		// - Document Information

		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			this.document = PDDocument.load(inputStream);
			this.text = new PDFTextStripper().getText(this.document);
			this.pathToResource = name;
			this.numberOfPages = this.document.getNumberOfPages();
			this.author = this.document.getDocumentInformation().getAuthor();
			this.creationDate = this.document.getDocumentInformation().getCreationDate();
			this.creator = this.document.getDocumentInformation().getCreator();
			this.keywords = this.document.getDocumentInformation().getKeywords();
			this.producer = this.document.getDocumentInformation().getProducer();
			this.subject = this.document.getDocumentInformation().getSubject();
			this.title = this.document.getDocumentInformation().getTitle();
			this.encrypted = this.document.isEncrypted();
			PDSignature signature = this.document.getLastSignatureDictionary();
			this.signed = signature != null;
			this.signerName = signature == null ? null : signature.getName();
			this.signatureTime = signature == null ? null : signature.getSignDate();
			this.isSuccessfullyLoaded = true;
		} catch (IllegalArgumentException e) {
			logger.error("Invalid PDF file: {}", name);
			throw new IllegalArgumentException("Invalid PDF file: " + name, e);
		} catch (IOException e) {
			logger.error("There was an error while processing the document!", e);
			throw new IOException("Cannot load PDF!");
		}
	}

	public PDDocument getDocument() {
		return this.document;
	}

	public boolean isLoaded() {
		return this.isSuccessfullyLoaded;
	}

	public void close() throws IOException {
		if (this.document != null) {
			this.document.close();
			this.isSuccessfullyLoaded = false;
		} else {
			logger.warn("Cannot close document because it is not existing.");
		}
	}

	private static byte[] readBytes(URL url) throws IOException {
		try (InputStream inputStream = url.openStream()) {
			return readBytes(inputStream);
		}
	}

	private static byte[] readBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream(2048);
		byte[] buffer = new byte[2048];

		int nRead;
		while ((nRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
			result.write(buffer, 0, nRead);
		}

		return result.toByteArray();
	}

	public Matcher<PDFDriver> containsText(String text) {
		return new ContainsText(text);
	}

	public byte[] getContentStream() {
		return this.content;
	}

	/**
	 * initialize resource before testcase.
	 */
	@Override
	protected void before() throws Throwable {
		load(new File(pathToResource));
	}

	/**
	 * Clean up resource after testcase.
	 */
	@Override
	protected void after() {
		try {
			close();
		} catch (IOException e) {
			logger.error("Could not close pdf file.", e);
		}
	}
	
}