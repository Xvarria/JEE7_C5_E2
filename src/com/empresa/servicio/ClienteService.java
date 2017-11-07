package com.empresa.servicio;

import java.util.Collection;

import com.empresa.model.Cliente;

public class ClienteService {
	
	private final static String[] CARACTERES = {"1","2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P"};
	
	public Cliente crearCliente(int id, String nombre){
		Cliente cliente = new Cliente();
		cliente.setMatriz(this.generarMatriz());
		cliente.setNombre(nombre);
		cliente.setId(id);
		return cliente;
	}
	
	public Cliente crearCliente(Cliente clienteParam){
		Cliente cliente = new Cliente();
		cliente.setMatriz(this.generarMatriz());
		cliente.setNombre(clienteParam.getNombre());
		cliente.setId(clienteParam.getId());
		cliente.setCedula(clienteParam.getCedula());
		return cliente;
	}
	
	public void addClienteToPool(Collection<Cliente> pool, Cliente cliente){
		if (!isClienteInPool(pool, cliente.getId())){
			pool.add(cliente);
		}else{
			//Throw clientExistError
		}
	}

	public boolean isClienteInPool(Collection<Cliente> pool, int idCliente){
		return this.getClienteById(pool, idCliente) != null;
	}
	
	public Cliente getClienteById(Collection<Cliente> pool, int idCliente){
		Cliente cliente = null;
		for (Cliente clienteAux : pool){
			if (clienteAux.getId() == idCliente){
				cliente = clienteAux;
				break;
			}
		}
		return cliente;
	}
	
	public boolean isCoordenadaValida(int coordenadaX, int coordenadaY, String valor, Cliente cliente){
		return cliente.getValorEnMatriz(coordenadaX, coordenadaY).equals(valor);
	}
	
	public String[][] generarMatriz(){
		String[][] matriz = new String[5][5];
		for (int y = 0; y <= 4; y++){
			for (int x = 0; x <= 4; x++){
				matriz[x][y] = CARACTERES[(int)(Math.random() * 26)];
			}
		}
		return matriz;
	}
}

	