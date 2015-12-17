package tests;

import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

import controller.Database;
import controller.Date;
import controller.Language;
import controller.Location;

public class TestController {

    static Connection connection = null;
    private static String req;

    public static void main(String[] args) throws SQLException {
	// TODO Auto-generated method stub

	////////////////////////// TEST DATABASE//////////////////////////
	/**
	 * Test reinit()
	 */
	Database dbase = new Database();
	dbase.reinit();
	assert dbase.equals(null) : "Reinitialization didn't work.";

	/**
	 * Test close()
	 */
	dbase.close();
	assert (!connection.isValid(500)) : "The connection hasn't closed";

	/**
	 * Test connect()
	 */

	/**
	 * Test select_request()
	 */
	dbase.select_request(req);
	Statement statement = connection.createStatement();
	assert statement.executeQuery(req) == dbase.select_request(req) : "The query doesn't fit with the request.";

	/**
	 * Test request()
	 */

	dbase.request(req);
	assert statement.executeUpdate(req) == dbase.request(req) : "The request failed.";

	/**
	 * Test getAutoIncRequest()
	 */

	////////////////////////// TEST DATE////////////////////////////////

	/**
	 * Test getDate() and getKeyword()
	 */

	String str = "codingweek";
	String date = "2015-12-02";
	Date d = new Date(str, date, null);
	assert d.getDate() == date && d.getKeyword() == str : "Error in the getters";

	/**
	 * Test dateIsValid()
	 * 
	 * 
	 * String validDate = "2014-11-26"; assert validDate.dateIsValid() ==
	 * true : "The format of the date isn't correct.";
	 */

	/**
	 * Test startRequest()
	 */

	//////////////////////// TEST LANGUAGE/////////////////////////////

	/**
	 * Test getLanguage()
	 */
	Language l = new Language("codingweek", "en", null);
	assert l.getLanguage() == "en" && l.getKeyword() == "codingweek" : "Error in the getters";

	///////////////////////// TEST LOCATION/////////////////////////////

	/**
	 * Test getCity() and getCountry()
	 */
	Location loc = new Location("Paris", "France", null);
	assert loc.getCity() == "Paris" && loc.getCountry() == "France" : "Error in the getters";

	//////////////////////// TEST MULTIPARAMS//////////////////////////

	/**
	 * Test getters Multiparams multi = new Multiparams("codingweek", "en",
	 * "2015-12-02", "screen_name", null);
	 */
    }

}
