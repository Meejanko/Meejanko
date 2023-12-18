package com.view;


import java.util.Collections;
import java.util.List;

import com.model.Youxifenshu;
import com.util.Dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class Scoreui extends AnchorPane {

	private String mainid;
	public String getMainid() {
		return mainid;
	}
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	Dao<Youxifenshu> dao = new Dao(new Youxifenshu());
	ObservableList<Youxifenshu> data = FXCollections.observableArrayList();
	public Scoreui(String param) {
		this.mainid=param;
		 TableView tableView=new TableView();
		tableView.setPrefWidth(Double.valueOf(800));
		tableView.setPrefHeight(Double.valueOf(400));
		tableView.setMaxWidth(Double.valueOf(800));
		tableView.setMaxHeight(Double.valueOf(400));
		tableView.setMinWidth(Double.valueOf(800));
		tableView.setMinHeight(Double.valueOf(400));
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setLeftAnchor(tableView, Double.valueOf(145));
		this.setTopAnchor(tableView, Double.valueOf(122));
		 TableColumn c1=new TableColumn("RANK");
		 c1.setCellValueFactory(new PropertyValueFactory<>("id"));
		 tableView.getColumns().add(c1);
		 TableColumn c2=new TableColumn("PLAYER");
		 c2.setCellValueFactory(new PropertyValueFactory<>("wanjia"));
		 tableView.getColumns().add(c2);
		 TableColumn c3=new TableColumn("SCORE");
		 c3.setCellValueFactory(new PropertyValueFactory<>("yingdedejushu"));
		 tableView.getColumns().add(c3);
		tableView.setItems(data);
		this.getChildren().add(tableView);
		cid88afa0fa68refresh();
		
	}
	public void cid88afa0fa68refresh(){
		data.clear();
		List<Youxifenshu> list=dao.getAll();
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(i+1);
		}
    	data.addAll(list);
	}
 	public void showMsg(String msg){
   	 Alert alert = new Alert(AlertType.INFORMATION);
        alert.titleProperty().set("TIPS");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
   }
}
