package com.falsschocostudios.ibetuome;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class Bowme implements Serializable{
	
	private String title, description;
	private List<String> participants;
	private double amount;
	
	public Bowme(String t, String d, List<String> p, double a){
		title = t;
		description = d;
		participants = p;
		amount = a;
	}
	
	public double getAmount() {
	    return amount;
	}
	public String getTitle(){
		return title;
	}
	public String getDescription(){
		return description;
	}
	public List<String> getParticipants(){
		return participants;
	}
}
