package org.vebqa.vebtal.pdfrestserver;

import java.util.TreeSet;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.vebqa.vebtal.AbstractTestAdaptionPlugin;
import org.vebqa.vebtal.CommandAutoComplete;
import org.vebqa.vebtal.GuiManager;
import org.vebqa.vebtal.TestAdaptionType;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.CommandResult;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.sut.SutStatus;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class PdfTestAdaptionPlugin extends AbstractTestAdaptionPlugin {

	/**
	 * unique id of the test adapter
	 */
	public static final String ID = "pdf";
	
	/**
	 * tableview with commands
	 */
	protected static final TableView<CommandResult> commandList = new TableView<>();
	
	/**
	 * results after execution
	 */
	protected static final ObservableList<CommandResult> clData = FXCollections.observableArrayList();		
	
	public PdfTestAdaptionPlugin() {
		super(TestAdaptionType.ADAPTER);
	}

	public String getName() {
		return "PDF Plugin for VEB Test Adaption Layer";
	}

	@Override
	public Tab startup() {
		Tab pdfTab = createTab(ID, commandList, clData);
		// Add
		TreeSet<String> allCommands = new TreeSet<String>();
		allCommands.add("open");
		allCommands.add("verifyText");
		final CommandAutoComplete addCommand = new CommandAutoComplete(allCommands);
        addCommand.setPromptText("Command");
        addCommand.setMaxWidth(200);
        final TextField addTarget = new TextField();
        addTarget.setMaxWidth(200);
        addTarget.setPromptText("Target");
        final TextField addValue = new TextField();
        addValue.setMaxWidth(200);
        addValue.setPromptText("Value");
 
        final Button addButton = new Button("Go");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	Command newCmd = new Command(addCommand.getText(), addTarget.getText(), addValue.getText());
            	
                addCommand.clear();
                addTarget.clear();
                addValue.clear();
                
                PdfResource aResource = new PdfResource();
                GuiManager.getinstance().setTabStatus(PdfTestAdaptionPlugin.ID, SutStatus.CONNECTED);
                aResource.execute(newCmd);
                GuiManager.getinstance().setTabStatus(PdfTestAdaptionPlugin.ID, SutStatus.DISCONNECTED);
            }
        });
 
        HBox hbox = new HBox();
         
        hbox.getChildren().addAll(addCommand, addTarget, addValue, addButton);
        hbox.setSpacing(3);

		BorderPane pane = (BorderPane)pdfTab.getContent();
		pane.setTop(hbox);        

		
		return pdfTab;
	}

	public static void addCommandToList(Command aCmd, CommandType aType) {
		String aValue = aCmd.getValue();
		CommandResult tCR = new CommandResult(aCmd.getCommand(), aCmd.getTarget(), aValue, aType);
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

	@Override
	public CombinedConfiguration loadConfig() {
		return loadConfig(ID);
	}
}
