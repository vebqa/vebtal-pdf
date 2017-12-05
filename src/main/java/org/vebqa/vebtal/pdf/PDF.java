package org.vebqa.vebtal.pdf;

import static java.nio.file.Files.readAllBytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hamcrest.Matcher;

public class PDF {
	public final byte[] content;

	// DublinCore
	private String dcTitle;
	private String dcDescription;
	private List<String> dcCreators;
	private List<Calendar> dcDates;
	private List<String> dcSubjects;
	
	// Adobe PDF Schema
	
	// XMP Basic Schema
	
	// Document Information
	public final String text;
	public final int numberOfPages;
	public final String author;
	public final Calendar creationDate;
	public final String creator;
	public final String keywords;
	public final String producer;
	public final String subject;
	public final String title;
	public final boolean encrypted;
	public final boolean signed;
	public final String signerName;
	public final Calendar signatureTime;

	private PDF(String name, byte[] content) {
		this.content = content;

		// separate metadata if availabe in
		// - Dublin Core
		// - Adobe PDF Schema
		// - XMP Basic Schema
		// - Document Information
		
		try (InputStream inputStream = new ByteArrayInputStream(content)) {
			try (PDDocument pdf = PDDocument.load(inputStream)) {
				this.text = new PDFTextStripper().getText(pdf);
				this.numberOfPages = pdf.getNumberOfPages();
				this.author = pdf.getDocumentInformation().getAuthor();
				this.creationDate = pdf.getDocumentInformation().getCreationDate();
				this.creator = pdf.getDocumentInformation().getCreator();
				this.keywords = pdf.getDocumentInformation().getKeywords();
				this.producer = pdf.getDocumentInformation().getProducer();
				this.subject = pdf.getDocumentInformation().getSubject();
				this.title = pdf.getDocumentInformation().getTitle();
				this.encrypted = pdf.isEncrypted();

				PDSignature signature = pdf.getLastSignatureDictionary();
				this.signed = signature != null;
				this.signerName = signature == null ? null : signature.getName();
				this.signatureTime = signature == null ? null : signature.getSignDate();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid PDF file: " + name, e);
		}
	}

	public PDF(File pdfFile) throws IOException {
		this(pdfFile.getAbsolutePath(), readAllBytes(Paths.get(pdfFile.getAbsolutePath())));
	}

	public PDF(URL url) throws IOException {
		this(url.toString(), readBytes(url));
	}

	public PDF(URI uri) throws IOException {
		this(uri.toURL());
	}

	public PDF(byte[] content) {
		this("", content);
	}

	public PDF(InputStream inputStream) throws IOException {
		this(readBytes(inputStream));
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

	private void extractDublinCore() {
		this.dcTitle = "";
		
	}
	
	private void extractAdobePDF() {
		
	}
	
	private void extractXMPBasic() {
		
	}
	
	public static Matcher<PDF> containsText(String text) {
		return new ContainsText(text);
	}
}
