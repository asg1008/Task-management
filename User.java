package com.company.user.bean;


public class User {
	
private int id;
private String title;
private String status;
private String date;

//constructor w/o id
public User(String title, String status, String date) {
	super();
	this.title = title;
	this.status = status;
	this.date = date;
}

//constructor with id
public User(int id, String title, String status, String date) {
	super();
	this.id = id;
	this.title = title;
	this.status = status;
	this.date = date;
}


//getter setter methods
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getDate() {
    return date;
}
public void setDate(String date) {
    this.date = date;
}




}
