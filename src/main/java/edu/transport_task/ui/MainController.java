package edu.transport_task.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.hibernate.annotations.PolymorphismType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.transport_task.service.ChargesPlaneService;
import edu.transport_task.service.ChargesPlaneServiceImpl;
import javafx.stage.Modality;
import static javafx.stage.Modality.NONE;
import static javafx.stage.Modality.WINDOW_MODAL;
import static javafx.stage.Modality.APPLICATION_MODAL;
import javafx.stage.Window;
import javafx.scene.paint.Color;

import static org.mockito.Mockito.verify;
import java.nio.file.attribute.PosixFilePermission;

import javax.annotation.PostConstruct;

@SuppressWarnings({ "SpringJavaAutowiringInspection", "unchecked", "restriction", "unused", "static-access" })

public class MainController {

	@FXML
	private TextField necessity;
	@FXML
	private TextField availability;
	@FXML
	private TextField cost;
	@FXML
	private ScrollPane scroll;
	

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	private ChargesPlaneService chargesPlane;
	private ChargesPlaneMarkUp chargesPlaneMarkUp;
	private GridPane mainGrid;

	@FXML
	public void initialize() {
		chargesPlane = new ChargesPlaneServiceImpl();
				
			
		necessity.setText("0,First,40,1,Second,40,2,Third,40");
		availability.setText("0,First,20,1,Second,30,2,Third,50,3,Fourth,20");
		cost.setText("0,0,3,0,1,2,0,2,7,0,3,0,1,0,5,1,1,6,1,2,4,1,3,0,2,0,4,2,1,7,2,2,5,2,3,0");
				
		
		chargesPlane.fullPlaneMatrix(necessity.getText(), availability.getText(), cost.getText());
		chargesPlaneMarkUp = new ChargesPlaneMarkUp(chargesPlane);
		
		drawChargesPlane();
		
		scroll.setContent(mainGrid);
		logger.info("getting basic data ...");
	}

	@PostConstruct
	public void init() {
	}

	@FXML
	public void handleSolve() {
		chargesPlane = new ChargesPlaneServiceImpl();
		chargesPlane.fullPlaneMatrix(necessity.getText(), availability.getText(), cost.getText());
		chargesPlane.solveTask();
		chargesPlaneMarkUp = new ChargesPlaneMarkUp(chargesPlane);
		drawChargesPlane();
		scroll.setContent(mainGrid);
		
		if (chargesPlane.isOptimal()) showDialog(null, "A charges plane is optimal!");
		
		logger.info("solve transport task");
	}

	@FXML
	public void handleExit() {
		System.exit(0);
	}

	private VBox cellGrid(String i, String j, String k) {
		GridPane gp1 = new GridPane();
		gp1.setAlignment(Pos.CENTER);

		Label lb1 = new Label(k);
		lb1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		int size = 20;
		lb1.setPrefHeight(2 * size);
		lb1.setPrefWidth(2 * size);
		lb1.setAlignment(Pos.CENTER);

		gp1.setRowIndex(lb1, 0);
		gp1.setColumnIndex(lb1, 0);
		gp1.getChildren().add(lb1);

		setZeroAnchorPane(lb1);
		setZeroAnchorPane(gp1);

		GridPane gp2 = new GridPane();
		gp2.setAlignment(Pos.CENTER);
		Label lb2 = new Label(i);
		lb2.setPrefHeight(size);
		lb2.setPrefWidth(size);
		lb2.setAlignment(Pos.CENTER_LEFT);

		gp2.setRowIndex(lb2, 0);
		gp2.setColumnIndex(lb2, 0);
		gp2.getChildren().add(lb2);

		Label lb3 = new Label(j);
		lb3.setPrefHeight(size);
		lb3.setPrefWidth(size);
		lb3.setAlignment(Pos.CENTER_RIGHT);

		gp2.setRowIndex(lb3, 0);
		gp2.setColumnIndex(lb3, 1);
		gp2.getChildren().add(lb3);

		setZeroAnchorPane(lb2);
		setZeroAnchorPane(lb3);
		setZeroAnchorPane(gp2);

		VBox vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		vb.setStyle(chargesPlaneMarkUp.MAIN_CSS_STYLE);

		vb.getChildren().addAll(gp1, gp2);
		setUCS(vb);
		setZeroAnchorPane(vb);

		return vb;
	}

	private void print(GridPane gp, String message, int x, int y) {
		gp.add(getLabel(message), x, y);
	}

	private void print(GridPane gp, String message, int x, int y, Pos pos) {
		Label lb = getLabel(message);
		lb.setAlignment(pos);
		gp.add(lb, x, y);
	}

	private void print(GridPane gp, String message, int x, int y, int dx, int dy) {
		Label lb = getLabel(message);
		gp.add(lb, x, y, dx, dy);
	}

	private void print(GridPane gp, String message, int x, int y, int dx, int dy, Pos pos) {
		Label lb = getLabel(message);
		lb.setAlignment(pos);
		gp.add(lb, x, y, dx, dy);
	}

	private void print(GridPane gp, String message, int x, int y, int dx, int dy, int angel) {
		Label lb = getLabel(message, angel);
		Label frameLb = getFrameLabel(lb);
		lb.setAlignment(Pos.CENTER_LEFT);
		gp.add(frameLb, x, y, dx, dy);
		gp.add(lb, x, y, dx, dy);
		setColumnSize(gp, x);
	}

	private void print(GridPane gp, String message, int x, int y, int dx, int dy, int angel, Pos pos) {
		Label lb = getLabel(message, angel);
		Label frameLb = getFrameLabel(lb);
		lb.setAlignment(pos);
		gp.add(frameLb, x, y, dx, dy);
		gp.add(lb, x, y, dx, dy);
		setColumnSize(gp, x);
	}

	private Label getLabel(String message) {
		Label lb = new Label(message);
		lb.setStyle(chargesPlaneMarkUp.MAIN_CSS_STYLE);
		lb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		return lb;
	}

	private Label getLabel(String message, int angel) {
		Label lb = getLabel(message);
		lb.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		lb.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		lb.setRotate(angel);
		return lb;
	}

	private Label getFrameLabel(Label lb) {
		Label frameLb = new Label();
		frameLb.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		lb.minWidthProperty().bind(frameLb.heightProperty());
		lb.minHeightProperty().bind(frameLb.widthProperty());
		return frameLb;
	}

	private void setColumnSize(GridPane gp, int columnIndex) {
		gp.getColumnConstraints().get(columnIndex).setMinWidth(chargesPlaneMarkUp.CELL_COLUMN_SIZE);
		gp.getColumnConstraints().get(columnIndex).setPrefWidth(chargesPlaneMarkUp.CELL_COLUMN_SIZE);
		gp.getColumnConstraints().get(columnIndex).setMaxWidth(chargesPlaneMarkUp.CELL_COLUMN_SIZE);
	}

	private void drawChargesPlane() {
		
		mainGrid = chargesPlaneMarkUp.getMainGrid();
		
		print(mainGrid, 
			  chargesPlaneMarkUp.CONSUMERS_LABEL, 
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_BEGIN_COLUMN,
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_BEGIN_ROW,
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH,
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT, 
			  Pos.CENTER);

		print(mainGrid, 
			  chargesPlaneMarkUp.SUPPLIERS_LABEL, 
			  chargesPlaneMarkUp.SUPPLIER_INSCRIPTION_BEGIN_COLUMN, 
			  chargesPlaneMarkUp.SUPPLIER_INSCRIPTION_BEGIN_ROW, 
			  chargesPlaneMarkUp.getSupplierQty(), 
			  chargesPlaneMarkUp.SUPPLIER_INSCRIPTION_HEIGHT, 
			  Pos.CENTER);

		
		for (int i = 0; i < chargesPlaneMarkUp.getConsumerQty(); i++) {
			print(mainGrid, 
				  " " + (i + 1) + "." + chargesPlane.getConsumers().get(i), 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_COLUMN, 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_ROW + i,
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH, 
				  chargesPlaneMarkUp.CONSUMER_NAME_HEIGHT);
		}

		for (int i = 0; i < chargesPlaneMarkUp.getSupplierQty(); i++) {
			print(mainGrid, 
				  " " + (i + 1) + "." + chargesPlane.getSuppliers().get(i), 
				  chargesPlaneMarkUp.SUPPLIER_NAME_BEGIN_COLUMN + i, 
				  chargesPlaneMarkUp.SUPPLIER_NAME_BEGIN_ROW, 
				  chargesPlaneMarkUp.SUPPLIER_POTENTIAL_WIDTH,
				  chargesPlaneMarkUp.SUPPLIER_NAME_HEIGHT, 
				  chargesPlaneMarkUp.ANGEL_ROTATE_SUPPLIER_NAME);
		}
		
		for (int i = 0; i < chargesPlaneMarkUp.getSupplierQty(); i++) {
			for (int j = 0; j < chargesPlane.getConsumers().size(); j++) {

				mainGrid.add(
						cellGrid("" + chargesPlane.getChargesPlane()[i][j].getTransportMatrix().getCurrentTax(), 
								 "" + chargesPlane.getChargesPlane()[i][j].getTransportMatrix().getTransportTax(),
								 "" + chargesPlane.getChargesPlane()[i][j].getTransportMatrix().getCost()),
						i + chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT,
						j + chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH);
			}
		}

		print(mainGrid, 
			  chargesPlaneMarkUp.SUPPLIERS_AVAILABILITY_LABEL, 
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH + chargesPlaneMarkUp.getSupplierQty(), 
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_BEGIN_COLUMN, 
			  chargesPlaneMarkUp.SUPPLIER_INSCRIPTION_HEIGHT,
			  chargesPlaneMarkUp.SUPPLIER_NAME_HEIGHT + 1, 
			  chargesPlaneMarkUp.ANGEL_ROTATE_SUPPLIER_NAME,
			  Pos.CENTER);
		
		
	
		for (int i = 0; i < chargesPlaneMarkUp.getConsumerQty(); i++)
			print(mainGrid,
				  chargesPlane.getConsumersNecessity().get(i), 
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty(), 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + i,
				  Pos.CENTER);
		
		print(mainGrid, 
			  "" + chargesPlane.getResult(),
			  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty(),
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + chargesPlaneMarkUp.getConsumerQty(), 
			  Pos.CENTER);

		print(mainGrid, 
				  "",
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty(),
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + chargesPlaneMarkUp.getConsumerQty() + 1, 
				  Pos.CENTER);

		
		
		
		print(mainGrid, 
				  chargesPlaneMarkUp.CONSUMERS_POTENTIAL_LABEL, 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH + chargesPlaneMarkUp.getSupplierQty() + 1, 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_BEGIN_COLUMN, 
				  chargesPlaneMarkUp.SUPPLIER_INSCRIPTION_HEIGHT,
				  chargesPlaneMarkUp.SUPPLIER_NAME_HEIGHT + 1, 
				  chargesPlaneMarkUp.ANGEL_ROTATE_SUPPLIER_NAME,
				  Pos.CENTER);
		
		for (int i = 0; i < chargesPlaneMarkUp.getConsumerQty(); i++)
			print(mainGrid, 
				  chargesPlane.getConsumersPotentail().get(i), 
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty() + 1, 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + i,
				  Pos.CENTER);

		
		print(mainGrid, 
			  "",
			  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty() + 1,
			  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + chargesPlaneMarkUp.getConsumerQty(), 
			  Pos.CENTER);

		print(mainGrid, 
				  "",
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH + chargesPlaneMarkUp.getSupplierQty() + 1,
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_HEIGHT + chargesPlaneMarkUp.getConsumerQty() + 1, 
				  Pos.CENTER);
		

		
		print(mainGrid, 
				  chargesPlaneMarkUp.CONSUMERS_NECESSITY_LABEL, 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_COLUMN, 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_ROW + chargesPlaneMarkUp.getConsumerQty(),
				  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH, 
				  chargesPlaneMarkUp.CONSUMER_NAME_HEIGHT,
				  Pos.CENTER);
		
		for (int i = 0; i < chargesPlane.getSuppliers().size(); i++)
			print(mainGrid, 
				  chargesPlane.getSuppliersAvailability().get(i), 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH + i, 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_ROW + chargesPlaneMarkUp.getConsumerQty(),
				  Pos.CENTER);

		print(mainGrid, chargesPlaneMarkUp.SUPPLIERS_POTENTIAL_LABEL, 
			  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_COLUMN, 
			  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_ROW + chargesPlaneMarkUp.getConsumerQty() + 1,
			  chargesPlaneMarkUp.CONSUMER_NAME_WIDTH, 
			  chargesPlaneMarkUp.CONSUMER_NAME_HEIGHT, 
			  Pos.CENTER);

		for (int i = 0; i < chargesPlaneMarkUp.getSupplierQty(); i++)
			print(mainGrid, 
				  chargesPlane.getSuppliersPotentail().get(i), 
				  chargesPlaneMarkUp.CONSUMER_INSCRIPTION_WIDTH + i, 
				  chargesPlaneMarkUp.CONSUMER_NAME_BEGIN_ROW + chargesPlaneMarkUp.getConsumerQty() + 1,
				  Pos.CENTER);
		
	}

	private void setZeroAnchorPane(Node n) {
		AnchorPane.setLeftAnchor(n, 0.0);
		AnchorPane.setRightAnchor(n, 0.0);
		AnchorPane.setTopAnchor(n, 0.0);
		AnchorPane.setBottomAnchor(n, 0.0);

	}

	private void setUCS(GridPane gp) {
		gp.setMinWidth(Control.USE_COMPUTED_SIZE);
		gp.setMaxWidth(Control.USE_COMPUTED_SIZE);
		gp.setPrefWidth(Control.USE_COMPUTED_SIZE);
		gp.setMinHeight(Control.USE_COMPUTED_SIZE);
		gp.setMaxHeight(Control.USE_COMPUTED_SIZE);
		gp.setPrefHeight(Control.USE_COMPUTED_SIZE);
	}

	private void setUCS(Label lb) {
		lb.setMinWidth(Control.USE_COMPUTED_SIZE);
		lb.setMaxWidth(Control.USE_COMPUTED_SIZE);
		lb.setPrefWidth(Control.USE_COMPUTED_SIZE);
		lb.setMinHeight(Control.USE_COMPUTED_SIZE);
		lb.setMaxHeight(Control.USE_COMPUTED_SIZE);
		lb.setPrefHeight(Control.USE_COMPUTED_SIZE);
	}

	private void setUCS(VBox vb) {
		vb.setMinWidth(Control.USE_COMPUTED_SIZE);
		vb.setMaxWidth(Control.USE_COMPUTED_SIZE);
		vb.setPrefWidth(Control.USE_COMPUTED_SIZE);
		vb.setMinHeight(Control.USE_COMPUTED_SIZE);
		vb.setMaxHeight(Control.USE_COMPUTED_SIZE);
		vb.setPrefHeight(Control.USE_COMPUTED_SIZE);
	}

	private class ChargesPlaneMarkUp {
		private final int ANGEL_ROTATE_SUPPLIER_NAME = -90;
		
		private final int CONSUMER_INSCRIPTION_BEGIN_COLUMN = 0;
		private final int CONSUMER_INSCRIPTION_BEGIN_ROW = 0;
		private final int CONSUMER_INSCRIPTION_WIDTH = 5;
		private final int CONSUMER_INSCRIPTION_HEIGHT = 5;
		
		private final int CONSUMER_NAME_BEGIN_COLUMN = 0;
		private final int CONSUMER_NAME_BEGIN_ROW = 5;
		private final int CONSUMER_NAME_WIDTH = 5;
		private final int CONSUMER_NAME_HEIGHT = 1;
		
		private final int CONSUMER_NECESSITY_WIDTH = 1;
		private final int CONSUMER_POTENTIAL_WIDTH = 1;

		private final String CONSUMERS_LABEL = " Consumers ";
		private final String CONSUMERS_NECESSITY_LABEL = " Necessity ";
		private final String CONSUMERS_POTENTIAL_LABEL = " V ";
		
		
		private final int SUPPLIER_INSCRIPTION_BEGIN_COLUMN = 5;
		private final int SUPPLIER_INSCRIPTION_BEGIN_ROW = 0;
		private final int SUPPLIER_INSCRIPTION_HEIGHT = 1;
		
		private final int SUPPLIER_NAME_BEGIN_COLUMN = CONSUMER_INSCRIPTION_WIDTH;
		private final int SUPPLIER_NAME_BEGIN_ROW = 1;
		private final int SUPPLIER_NAME_HEIGHT = 4;
		private final int SUPPLIER_NAME_WIDTH = 1;
		
		private final int SUPPLIER_AVAILABILITY_WIDTH = 1;
		private final int SUPPLIER_POTENTIAL_WIDTH = 1;
		
		private final String SUPPLIERS_LABEL = " Suppliers ";
		private final String SUPPLIERS_AVAILABILITY_LABEL = " Availability ";
		private final String SUPPLIERS_POTENTIAL_LABEL = " U ";
		
		private final int NAMES_LENGHT_ON_LABELS = 25;
		
		private final int CELL_ROW_SIZE = 10;
		private final int CELL_COLUMN_SIZE = 5;
		
		private final String MAIN_CSS_STYLE = "-fx-border-style: solid inside;" + "\r\n" + "-fx-border-width: 1;"
				+ "\r\n" + "-fx-border-color: black;";

		private int width;
		private int height;
		private int beginChargesPlaneMatrixX;
		private int beginChargesPlaneMatrixY;
		private int chargesPlaneMatrixWidth;
		private int chargesPlaneMatrixHieght;
		private int consumerQty;
		private int supplierQty;
		private int maxLenghtNameConsumer;
		private int maxLenghtNameSupplier;
		private int nameSupplierDrx;
		private int nameSupplierDry;

		private ChargesPlaneService chargesPlaneService;

		private GridPane mainGrid;

		public ChargesPlaneMarkUp(ChargesPlaneService cps) {
			mainGrid = new GridPane();
			chargesPlaneService = cps;
			consumerQty = cps.getConsumers().size();
			supplierQty = cps.getSuppliers().size();
			maxLenghtNameConsumer = getMaxLenghtNameConsumer();
			maxLenghtNameSupplier = getMaxLenghtNameSupplier();
			fullCellsMainGrid();

		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getBeginChargesPlaneMatrixX() {
			return beginChargesPlaneMatrixX;
		}

		public void setBeginChargesPlaneMatrixX(int beginChargesPlaneMatrixX) {
			this.beginChargesPlaneMatrixX = beginChargesPlaneMatrixX;
		}

		public int getBeginChargesPlaneMatrixY() {
			return beginChargesPlaneMatrixY;
		}

		public void setBeginChargesPlaneMatrixY(int beginChargesPlaneMatrixY) {
			this.beginChargesPlaneMatrixY = beginChargesPlaneMatrixY;
		}

		public int getChargesPlaneMatrixWidth() {
			return chargesPlaneMatrixWidth;
		}

		public void setChargesPlaneMatrixWidth(int chargesPlaneMatrixWidth) {
			this.chargesPlaneMatrixWidth = chargesPlaneMatrixWidth;
		}

		public int getChargesPlaneMatrixHieght() {
			return chargesPlaneMatrixHieght;
		}

		public void setChargesPlaneMatrixHieght(int chargesPlaneMatrixHieght) {
			this.chargesPlaneMatrixHieght = chargesPlaneMatrixHieght;
		}

		public int getConsumerQty() {
			return consumerQty;
		}

		public void setConsumerQty(int consumerQty) {
			this.consumerQty = consumerQty;
		}

		public int getSupplierQty() {
			return supplierQty;
		}

		public void setSupplierQty(int supplierQty) {
			this.supplierQty = supplierQty;
		}

		public int getNameSupplierDrx() {
			return nameSupplierDrx;
		}

		public void setNameSupplierDrx(int nameSupplierDrx) {
			this.nameSupplierDrx = nameSupplierDrx;
		}

		public int getNameSupplierDry() {
			return nameSupplierDry;
		}

		public void setNameSupplierDry(int nameSupplierDry) {
			this.nameSupplierDry = nameSupplierDry;
		}

		public void setMaxLenghtNameConsumer(int maxLenghtNameConsumer) {
			this.maxLenghtNameConsumer = maxLenghtNameConsumer;
		}

		public void setMaxLenghtNameSupplier(int maxLenghtNameSupplier) {
			this.maxLenghtNameSupplier = maxLenghtNameSupplier;
		}

		public GridPane getMainGrid() {
			return mainGrid;
		}

		private void fullCellsMainGrid() {
			width = CONSUMER_INSCRIPTION_WIDTH + supplierQty + SUPPLIER_AVAILABILITY_WIDTH + SUPPLIER_POTENTIAL_WIDTH;
			height = CONSUMER_INSCRIPTION_HEIGHT + consumerQty + CONSUMER_NECESSITY_WIDTH + CONSUMER_POTENTIAL_WIDTH;

			logger.info("width main grid: " + width);
			logger.info("height main grid: " + height);

			// mainGrid.gridLinesVisibleProperty().set(true);
			mainGrid.alignmentProperty().set(Pos.CENTER);

			for (int i = 0; i < height; i++) {
				RowConstraints rc = new RowConstraints();
				rc.setPercentHeight(CELL_ROW_SIZE);
				rc.setValignment(VPos.CENTER);
				mainGrid.getRowConstraints().add(rc);
			}

			for (int j = 0; j < width; j++) {
				ColumnConstraints cc = new ColumnConstraints();
				cc.setPercentWidth(CELL_COLUMN_SIZE);
				cc.setHalignment(HPos.CENTER);
				mainGrid.getColumnConstraints().add(cc);
			}

		}

		private int getMaxLenghtNameConsumer() {
			return this.chargesPlaneService.getConsumers().entrySet().stream().map(v -> v.getValue().length())
					.max((v1, v2) -> v1.compareTo(v2)).get();
		}

		private int getMaxLenghtNameSupplier() {
			return this.chargesPlaneService.getSuppliers().entrySet().stream().map(v -> v.getValue().length())
					.max((v1, v2) -> v1.compareTo(v2)).get();
		}
	}

	private void showDialog(Window owner, String message) {

		Stage stage = new Stage();
		stage.initOwner(owner);
	
		stage.initModality(APPLICATION_MODAL);
		Label modalityLabel = new Label(message + System.lineSeparator());
		modalityLabel.setAlignment(Pos.CENTER);
		Button closeButton = new Button("  OK  ");
		closeButton.setAlignment(Pos.CENTER);
		closeButton.setOnAction(e -> stage.close());
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(modalityLabel, closeButton);
		Scene scene = new Scene(root, 300, 100);
		scene.setFill(Color.TRANSPARENT);

		stage.setScene(scene);
		stage.setTitle("A informer window");
		stage.show();
		
	}

}
