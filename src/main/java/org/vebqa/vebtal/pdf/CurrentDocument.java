package org.vebqa.vebtal.pdf;

public class CurrentDocument {
	
	private static final CurrentDocument cd = new CurrentDocument();
	
	private PDF current = null;
	
	public CurrentDocument() {
		
	}
	
	public static CurrentDocument getInstance() {
		return cd;
	}

	public PDF getDoc() {
		return current;
	}
	
	public void setDoc(PDF aDoc) {
		current = aDoc;
	}
}
