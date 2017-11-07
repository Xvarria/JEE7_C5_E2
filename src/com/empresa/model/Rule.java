package com.empresa.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rule")
public class Rule {
	private String logic;
	
	public Rule() {
		super();
	}

	public String getLogic() {
		return logic;
	}

	@XmlElement
	public void setLogic(String logic) {
		this.logic = logic;
	}
}
