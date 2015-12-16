package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.User_tweet;

public abstract class ControllerFX {
	
	protected MainApp mainApp;
	protected Database db;
	
	protected ControllerFX(){
		db = new Database();
	}
	
	protected ObservableList<User_tweet> createUsers(ResultSet rs){
		ObservableList<User_tweet> userObservable = FXCollections.observableArrayList();
		
		try {
			while(rs.next()){
				userObservable.add(new User_tweet(rs.getLong("id_user"), rs.getString("name"), rs.getString("screen_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userObservable;
	}
}
