package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.Database;
import javafx.scene.control.ListView;
import model.User_tweet;

public abstract class ControllerFX {
	
	protected MainApp mainApp;
	protected Database db;
	
	protected ControllerFX(){
		db = new Database();
	}
	
	protected void createUsers(ResultSet rs, ListView<User_tweet> list){
		try {
			while(rs.next()){
				list.getItems().add(new User_tweet(rs.getLong("id_user"), rs.getString("name"), rs.getString("screen_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
