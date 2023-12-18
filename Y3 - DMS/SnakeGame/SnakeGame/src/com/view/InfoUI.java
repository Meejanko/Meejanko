package com.view;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.ArrayList;
import javafx.util.Callback;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert.AlertType;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Line;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class InfoUI extends AnchorPane {

	private String mainid;
	public String getMainid() {
		return mainid;
	}
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	public InfoUI(String param) {
		this.mainid=param;
		Label lbl1=new Label("MOVE LEFT               ←");
		lbl1.setFont(new Font("Dialog", 26));
		lbl1.setPrefWidth(Double.valueOf(400));
		lbl1.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(lbl1, Double.valueOf(167));
		this.setTopAnchor(lbl1, Double.valueOf(62));
		this.getChildren().add(lbl1);
		Label lbl2=new Label("MOVE DOWN               ↓");
		lbl2.setFont(new Font("Dialog", 26));
		lbl2.setPrefWidth(Double.valueOf(400));
		lbl2.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(lbl2, Double.valueOf(146));
		this.setTopAnchor(lbl2, Double.valueOf(277));
		this.getChildren().add(lbl2);
		Label lbl3=new Label("MOVE UP               ↑");
		lbl3.setFont(new Font("Dialog", 26));
		lbl3.setPrefWidth(Double.valueOf(400));
		lbl3.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(lbl3, Double.valueOf(195));
		this.setTopAnchor(lbl3, Double.valueOf(207));
		this.getChildren().add(lbl3);
		Label lbl4=new Label("MOVE RIGHT               →");
		lbl4.setFont(new Font("Dialog", 26));
		lbl4.setPrefWidth(Double.valueOf(400));
		lbl4.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(lbl4, Double.valueOf(151));
		this.setTopAnchor(lbl4, Double.valueOf(133));
		this.getChildren().add(lbl4);
		
	}
 	public void showMsg(String msg){
   	 Alert alert = new Alert(AlertType.INFORMATION);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
   }
}
