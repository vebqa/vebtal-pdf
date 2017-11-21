package org.vebqa.vebtal.pdfrestserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.text.WordUtils;
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
		PdfRoboPlugin.addCommandToList(cmd);
		
		Response tResponse = new Response();
		
		// Test - to be refactored
		// Command instanziieren
		// erst alles klein schreiben
		String tCmd = cmd.getCommand().toLowerCase().trim();
		// erster Buchstabe gross
		tCmd = WordUtils.capitalizeFully(tCmd);
		String tClass = "org.vebqa.roborest.pdf." + tCmd;
		Response result = null;
		try {
			Class<?> cmdClass = Class.forName(tClass);
			Constructor<?> cons = cmdClass.getConstructor(String.class, String.class, String.class);
			Object cmdObj = cons.newInstance(cmd.getCommand(), cmd.getTarget(), cmd.getValue());
			Method m = cmdClass.getDeclaredMethod("executeImpl", PDF.class);
			result = (Response)m.invoke(cmdObj, this.current);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (result == null) {
			tResponse.setCode("1");
			tResponse.setMessage("Cannot resolve command.");
			return tResponse;
		}
		if (result.getCode() != "0") {
			PdfRoboPlugin.setLatestResult(false, result.getMessage());
		} else {
			PdfRoboPlugin.setLatestResult(true, "ok");
		}
		return result;
	}
}
