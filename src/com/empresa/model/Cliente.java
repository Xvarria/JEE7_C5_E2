package com.empresa.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cliente")
public class Cliente {
	private int id;
	private String nombre;
	private String cedula;
	private String[][] matriz;
	
	public Cliente() {
		super();
	}

	public int getId() {
		return id;
	}
	
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@XmlElement
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCedula() {
		return cedula;
	}
	
	@XmlElement
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	public String[][] getMatriz() {
		return matriz;
	}
	
	public void setMatriz(String[][] matriz) {
		this.matriz = matriz;
	}
	
	public String getValorEnMatriz(int coordenadaX, int coordenadaY) throws IndexOutOfBoundsException{
		return this.matriz[coordenadaX][coordenadaY];
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nombre=" + nombre + ", cedula=" + cedula + ", " + this.stringMatriz()
				+ "]";
	}
	
	public String stringMatriz(){
		String result = " matriz=\n";
		for (int y = 0; y <= 4; y++){
			for (int x = 0; x <= 4; x++){
				result = result + this.matriz[x][y] + " / ";
			}
			result = result + "\n";
		}
		return result;
	}
	
	
}
