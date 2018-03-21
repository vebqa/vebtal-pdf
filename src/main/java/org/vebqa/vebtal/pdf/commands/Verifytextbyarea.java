package org.vebqa.vebtal.pdf.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.Area;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifytextbyarea extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(Verifytextbyarea.class);

	public Verifytextbyarea(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
	}

	/**
	 * | command | target | value |
	 * 
	 * | verifyTextByArea | x:1;y:1;height:1;width:1 | text |
	 */
	@Override
	public Response executeImpl() {

		Response tResp = new Response();

		if (target == null || target.contentEquals("")) {
			tResp.setCode("1");
			tResp.setMessage("Command needs target and value data to work!");
		} else {
			// target to area
			Area area;
			try {
				area = new Area(target);
			} catch (Exception e) {
				tResp.setCode("1");
				tResp.setMessage("Could not create area definition!");
				return tResp;
			}

			String areaText = null;

			try {
				PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
				textStripper.setSortByPosition(true);
				textStripper.addRegion("test", area.getRectangle());

				InputStream inputStream = new ByteArrayInputStream(CurrentDocument.getInstance().getDoc().content);
				PDDocument pdf = PDDocument.load(inputStream);
				PDPage page = pdf.getPage(area.getPage());
				textStripper.extractRegions(page);
				areaText = textStripper.getTextForRegion("test");
				logger.info("extracted text from area: {}", areaText);
			} catch (IOException e) {
				tResp.setCode("1");
				tResp.setMessage("Cannot handle pdf source: " + e.getMessage());
				return tResp;
			}
			
			if (areaText == null) {
				tResp.setCode("1");
				tResp.setMessage("Could not find text in area. Area is empty!");
			} else {
				logger.info("Text found in area: {} ", areaText);
				logger.info("Search for: {}", this.value);
				// We need to convert line breaks
				areaText = areaText.replace("\r\n", "\\r\\n");
				logger.info("CV Line Breaks: {}", areaText);
				if (StringUtils.contains(areaText, this.value)) {
					tResp.setCode("0");
					tResp.setMessage("Text found in area text.");
				} else {
					tResp.setCode("1");
					tResp.setMessage("Could not find text in area! Result is: " + areaText);
				}
			}
		}
		return tResp;
	}
}