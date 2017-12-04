package org.vebqa.vebtal.pdfrestserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.pdf.PDF;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.Response;

@Path("pdf")
public class PdfResource {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfResource.class);

	/**
	 * PDF
	 */
	public static PDF current;
	
	@POST
	@Path("execute")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response executePdf(Command cmd) {
		PdfTestAdaptionPlugin.addCommandToList(cmd);
		
		Response tResponse = new Response();
		
		// Test - to be refactored
		// Command instanziieren
		// erst alles klein schreiben
		String tCmd = cmd.getCommand().toLowerCase().trim();
		// erster Buchstabe gross
		tCmd = WordUtils.capitalizeFully(tCmd);
		String tClass = "org.vebqa.vebtal.pdf." + tCmd;
		Response result = null;
		try {
			Class<?> cmdClass = Class.forName(tClass);
			Constructor<?> cons = cmdClass.getConstructor(String.class, String.class, String.class);
			Object cmdObj = cons.newInstance(cmd.getCommand(), cmd.getTarget(), cmd.getValue());
			Method m = cmdClass.getDeclaredMethod("executeImpl", PDF.class);
			result = (Response)m.invoke(cmdObj, current);
			
		} catch (ClassNotFoundException e) {
			logger.error("Keyword class not found.", e);
		} catch (NoSuchMethodException e) {
			logger.error("Execute method in keyword class not found.", e);
		} catch (SecurityException e) {
			logger.error("Cannot invoke keyword class.", e);
		} catch (InstantiationException e) {
			logger.error("Cannot instantiate keyword class.", e);
		} catch (IllegalAccessException e) {
			logger.error("Illegal access for keyword class.", e);
		} catch (IllegalArgumentException e) {
			logger.error("Illegal argument while invoking keyword.", e);
		} catch (InvocationTargetException e) {
			logger.error("Cannot invoke keyword class.", e);
		}
		
		if (result == null) {
			tResponse.setCode("1");
			tResponse.setMessage("Cannot resolve command.");
			return tResponse;
		}
		if (result.getCode() != "0") {
			PdfTestAdaptionPlugin.setLatestResult(false, result.getMessage());
		} else {
			PdfTestAdaptionPlugin.setLatestResult(true, "ok");
		}
		return result;
	}
}
