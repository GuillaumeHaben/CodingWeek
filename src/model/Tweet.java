package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Tweet {

    private IntegerProperty id_t;
    private IntegerProperty id_r;
    private StringProperty n;
    private StringProperty sn;
    private StringProperty t;
    private IntegerProperty rt;
    private StringProperty cit;
    private StringProperty co;
    private IntegerProperty lat;
    private IntegerProperty lon;
    private ObjectProperty<LocalDate> d;

    public Tweet(ResultSet res) throws SQLException {

	this.id_t = (IntegerProperty) res.getArray(0);
	this.id_r = (IntegerProperty) res.getArray(1);
	this.n = (StringProperty) res.getArray(2);
	this.sn = (StringProperty) res.getArray(3);
	this.t = (StringProperty) res.getArray(4);
	this.rt = (IntegerProperty) res.getArray(5);
	this.cit = (StringProperty) res.getArray(6);
	this.co = (StringProperty) res.getArray(7);
	this.lat = (IntegerProperty) res.getArray(8);
	this.lon = (IntegerProperty) res.getArray(9);
	this.d = (ObjectProperty<LocalDate>) res.getArray(10);
    }

    public IntegerProperty getId_t() {
	return id_t;
    }

    public void setId_t(IntegerProperty id_t) {
	this.id_t = id_t;
    }

    public IntegerProperty getId_r() {
	return id_r;
    }

    public void setId_r(IntegerProperty id_r) {
	this.id_r = id_r;
    }

    public StringProperty getN() {
	return n;
    }

    public void setN(StringProperty n) {
	this.n = n;
    }

    public StringProperty getSn() {
	return sn;
    }

    public void setSn(StringProperty sn) {
	this.sn = sn;
    }

    public StringProperty getT() {
	return t;
    }

    public void setT(StringProperty t) {
	this.t = t;
    }

    public IntegerProperty getRt() {
	return rt;
    }

    public void setRt(IntegerProperty rt) {
	this.rt = rt;
    }

    public StringProperty getCit() {
	return cit;
    }

    public void setCit(StringProperty cit) {
	this.cit = cit;
    }

    public StringProperty getCo() {
	return co;
    }

    public void setCo(StringProperty co) {
	this.co = co;
    }

    public IntegerProperty getLat() {
	return lat;
    }

    public void setLat(IntegerProperty lat) {
	this.lat = lat;
    }

    public IntegerProperty getLon() {
	return lon;
    }

    public void setLon(IntegerProperty lon) {
	this.lon = lon;
    }

    public ObjectProperty<LocalDate> getD() {
	return d;
    }

    public void setD(ObjectProperty<LocalDate> d) {
	this.d = d;
    }
}