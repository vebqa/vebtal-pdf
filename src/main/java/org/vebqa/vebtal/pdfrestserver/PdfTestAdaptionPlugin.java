package org.vebqa.vebtal.pdfrestserver;

import org.vebqa.vebtal.AbstractTestAdaptionPlugin;
import org.vebqa.vebtal.TestAdaptionType;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.CommandResult;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

@SuppressWarnings("restriction")
public class PdfTestAdaptionPlugin extends AbstractTestAdaptionPlugin {

	public PdfTestAdaptionPlugin() {
		super(TestAdaptionType.ADAPTER);
	}

	/** Start/Stop Button **/
	private static final Button btnStartStop = new Button();

	private static final TableView<CommandResult> commandList = new TableView<>();
	private static final ObservableList<CommandResult> clData = FXCollections.observableArrayList();

	public String getName() {
		return "PDF Plugin for VEB Test Adaption Layer";
	}

	@Override
	public Tab startup() {
		// Richtet den Plugin-spezifischen Tab ein

		Tab pdfTab = new Tab();
		pdfTab.setText("PDF");

		// LogBox
		BorderPane root = new BorderPane();

		// Top bauen
		HBox hbox = new HBox();
		hbox.getChildren().addAll(btnStartStop);

		// Table bauen
		TableColumn selCommand = new TableColumn("Command");
		selCommand.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("command"));
		selCommand.setSortable(false);
		selCommand.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selTarget = new TableColumn("Target");
		selTarget.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("target"));
		selTarget.setSortable(false);
		selTarget.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selValue = new TableColumn("Value");
		selValue.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("value"));
		selValue.setSortable(false);
		selValue.prefWidthProperty().bind(commandList.widthProperty().multiply(0.15));

		TableColumn selResult = new TableColumn("Result");
		selResult.setCellValueFactory(new PropertyValueFactory<CommandResult, Image>("result"));
		selResult.setSortable(false);
		selResult.prefWidthProperty().bind(commandList.widthProperty().multiply(0.10));

		TableColumn selInfo = new TableColumn("LogInfo");
		selInfo.setCellValueFactory(new PropertyValueFactory<CommandResult, String>("loginfo"));
		selInfo.setSortable(false);
		selInfo.prefWidthProperty().bind(commandList.widthProperty().multiply(0.45));

		commandList.setItems(clData);
		commandList.getColumns().addAll(selCommand, selTarget, selValue, selResult, selInfo);

		// einfuegen
		root.setTop(hbox);
		root.setCenter(commandList);
		pdfTab.setContent(root);

		return pdfTab;
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
}
