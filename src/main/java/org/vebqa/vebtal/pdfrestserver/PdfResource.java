package org.vebqa.vebtal.pdfrestserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.AbstractTestAdaptionResource;
import org.vebqa.vebtal.TestAdaptionResource;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.Response;

public class PdfResource extends AbstractTestAdaptionResource implements TestAdaptionResource {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfResource.class);
	
	public PdfResource() {
	}
	
	public Response execute(Command cmd) {
	
		PdfTestAdaptionPlugin.addCommandToList(cmd);
		
		Response tResponse = new Response();
		
		Response result = null;
		try {
			Class<?> cmdClass = Class.forName("org.vebqa.vebtal.pdf.commands." + getCommandClassName(cmd));
			Constructor<?> cons = cmdClass.getConstructor(String.class, String.class, String.class);
			Object cmdObj = cons.newInstance(cmd.getCommand(), cmd.getTarget(), cmd.getValue());
			Method m = cmdClass.getDeclaredMethod("executeImpl");
			
			setStart();
			result = (Response)m.invoke(cmdObj);
			setFinished();
			
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
