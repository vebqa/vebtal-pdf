package org.vebqa.vebtal.pdfrestserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.AbstractTestAdaptionPlugin;
import org.vebqa.vebtal.TestAdaptionType;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.CommandResult;

import javafx.application.Platform;
import javafx.scene.control.Tab;

@SuppressWarnings("restriction")
public class PdfTestAdaptionPlugin extends AbstractTestAdaptionPlugin {

	private static final Logger logger = LoggerFactory.getLogger(PdfTestAdaptionPlugin.class);
	
	/**
	 * unique id of the test adapter
	 */
	private static final String ID = "pdf";
	
	public PdfTestAdaptionPlugin() {
		super(TestAdaptionType.ADAPTER);
	}

	public String getName() {
		return "PDF Plugin for VEB Test Adaption Layer";
	}

	@Override
	public Tab startup() {
		return createTab(ID);
	}

	public static void addCommandToList(Command aCmd) {
		String aValue = aCmd.getValue();
		CommandResult tCR = new CommandResult(aCmd.getCommand(), aCmd.getTarget(), aValue);
		Platform.runLater(() -> clData.add(tCR));
	}

	public static void setLatestResult(boolean success, final String aResult) {
		Platform.runLater(() -> clData.get(clData.size() - 1).setLogInfo(aResult));
		Platform.runLater(() -> clData.get(clData.size() - 1).setResult(success));

		commandList.refresh();
		Platform.runLater(() -> commandList.scrollTo(clData.size() - 1));
	}

	@Override
	public boolean shutdown() {
		return true;
	}
	
	@Override
	public Class<?> getImplementation() {
		return null;
	}
	
	@Override
	public String getAdaptionID() {
		return ID;
	}
}
