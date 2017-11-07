package com.empresa.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ruleResponse")
public class RuleResponse {
	private String rule;
	private String result;
	
	public String getRule() {
		return rule;
	}
	
	@XmlElement
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getResult() {
		return result;
	}
	@XmlElement
	public void setResult(String result) {
		this.result = result;
	}
	
	public RuleResponse(String rule, String result) {
		super();
		this.rule = rule;
		this.result = result;
	}
	
	public RuleResponse() {
		super();
	}
	
	@Override
	public String toString() {
		return "RuleResponse [rule=" + rule + ",\nresult=" + result + "]";
	}
}
