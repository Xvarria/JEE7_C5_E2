package com.empresa.servicio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.empresa.model.Respuesta;
import com.empresa.model.Rule;
import com.empresa.model.RuleResponse;

@Path("/")
public class TestWebService {
	

	
	public TestWebService() {
		super();
	}


	@GET
	@Path("test/{palabra}")
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta getCliente(@PathParam("palabra") String palabra) {
		Respuesta respuesta = new Respuesta();
		
		Client client = ClientBuilder.newClient();
		
		WebTarget myResource = client.target("http://localhost:8080/JEE7_Clase5_Ejemplo1/matrizDinamica/ruleTest");
		Rule parametro = new Rule();
		parametro.setLogic("Prueeba de relga " + palabra);
		RuleResponse rr =  myResource.request(MediaType.APPLICATION_JSON).post(Entity.xml(parametro), RuleResponse.class);
		
		
		respuesta.setPalabra(palabra);
		respuesta.setRegla(parametro.getLogic());
		respuesta.setResultado(rr.getResult());
		
		return respuesta;
	}
	


}
