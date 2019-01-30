package org.vebqa.vebtal.pdf.commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.pdf.CurrentDocument;

public class Verifycreationdate extends AbstractCommand {

	public Verifycreationdate(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		Response tResp = new Response();

		if (CurrentDocument.getInstance().getDoc().creationDate == null) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Document does not have a creation date. Attribute is null!");
			return tResp;
		}
		
		Calendar created = CurrentDocument.getInstance().getDoc().creationDate;
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
	    Date date = null; 
	    try {
			date = sdf. parse(this.target);
		} catch (ParseException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Cannot parse data: " + this.target);
			return tResp;
		}
	    Calendar cal = Calendar. getInstance();
	    cal. setTime(date);
	    
		if (created.compareTo(cal) == 0) {
			tResp.setCode(Response.PASSED);
			tResp.setMessage("Successfully found subject: " + target);
		} else {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected subject: " + this.target + " but found: " + created.toString() );
		}
		return tResp;
	}

}
