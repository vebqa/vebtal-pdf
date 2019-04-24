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
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionJavaScript;
import org.apache.pdfbox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.text.PDFTextStripper;
import org.hamcrest.Matcher;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFDriver extends ExternalResource {

	public static final String ACTION_VALIDATE = "validate";
	public static final String ACTION_CALCULATE = "calculate";
	public static final String ACTION_FORMAT = "format";
	public static final String ACTION_KEYSTROKE = "keystroke";
	
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

	public String getValueByFieldName(String aName) {
		PDDocumentCatalog docCatalog = this.document.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		List<PDField> fields = acroForm.getFields();
		for (PDField field : fields) {
			if (field.getPartialName().contentEquals(aName)) {
				PDFormFieldAdditionalActions actions = field.getActions();
				PDActionJavaScript js = (PDActionJavaScript) actions.getV();
				return field.getValueAsString();
			}
		}
		return null;
	}

	public String getActionByFieldName(String aName) {
		PDDocumentCatalog docCatalog = this.document.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		List<PDField> fields = acroForm.getFields();
		for (PDField field : fields) {
			if (field.getPartialName().contentEquals(aName)) {
				PDFormFieldAdditionalActions actions = field.getActions();
				if ((actions != null) && (actions.getV() != null)) {
					PDActionJavaScript js = (PDActionJavaScript) actions.getV();
					return js.getAction();
				}
			}
		}
		return null;
	}

	/**
	 * Returns true if a field has the expected additional action.
	 * 
	 * @param aName 	field name
	 * @param anAction	specified additional action, e.g. validate, calculate, keystroke, format
	 * @return			true, if expected additional action was found
	 */
	public boolean hasAdditionalActionByFieldName(String aName, String anAction) {
		PDDocumentCatalog docCatalog = this.document.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		List<PDField> fields = acroForm.getFields();
		for (PDField field : fields) {
			if (field.getPartialName().contentEquals(aName)) {
				PDFormFieldAdditionalActions actions = field.getActions();
				// there is no action attached
				if (actions == null) {
					return false;
				}
				
				if ((actions.getV() != null) && anAction.contentEquals(PDFDriver.ACTION_VALIDATE)) {
					return true;
				}
				if ((actions.getC() != null) && anAction.contentEquals(PDFDriver.ACTION_CALCULATE)) {
					return true;
				}
				if ((actions.getF() != null) && anAction.contentEquals(PDFDriver.ACTION_FORMAT)) {
					return true;
				}
				if ((actions.getK() != null) && anAction.contentEquals(PDFDriver.ACTION_KEYSTROKE)) {
					return true;
				}
			}
		}
		return false;
	}	
	
	/**
	 * 
	 * @param aName		field name
	 * @param aValue	expected value
	 * @return			true, if field contains the expected value 
	 */
	public boolean setValueByFieldName(String aName, String aValue) {
		PDDocumentCatalog docCatalog = this.document.getDocumentCatalog();
		PDAcroForm acroForm = docCatalog.getAcroForm();
		List<PDField> fields = acroForm.getFields();
		for (PDField field : fields) {
			if (field.getPartialName().contentEquals(aName)) {
				logger.info("Field found: " + aName);
				try {
					field.setValue(aValue);
				} catch (IOException e) {
					logger.error("cannot write to field: " + aName, e);
					return false;
				}
				return true;
			} else {
				logger.info("Field not matching: " + field.getPartialName());
			}
		}
		return false;
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