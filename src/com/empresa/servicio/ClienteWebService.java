package com.empresa.servicio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.empresa.model.Cliente;

@Path("/")
public class ClienteWebService {
	
	private Collection<Cliente> clientes = new ArrayList<Cliente>();
	
	public ClienteService clienteServicio = new ClienteService();
	
	public ClienteWebService() {
		Cliente cliente = this.clienteServicio.crearCliente(1, "Marcos");
		this.clienteServicio.addClienteToPool(clientes, cliente);
	}


	@GET
	@Path("clientesMatriz/{id}")
	@Produces("application/json")
	public StreamingOutput getCliente(@PathParam("id") int id) {
		final Cliente cliente = this.clienteServicio.getClienteById(clientes, id);
		if (cliente == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, 
			                                                    WebApplicationException {
				outputCliente(outputStream, cliente.toString());
			}
		};
	}
	
	@GET
	@Path("matriz/{id}")
	@Produces("application/json")
	public StreamingOutput getMatriz(@PathParam("id") int id) {
		final Cliente cliente = this.clienteServicio.getClienteById(clientes, id);
		if (cliente == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, 
			                                                    WebApplicationException {
				outputCliente(outputStream, cliente.stringMatriz());
			}
		};
	}
	
	@GET
	@Path("accion/{accion}/{id}/{c1}-{c2}-{c3}")
	//@Path("accion/{accion}/{id}")
	@Produces("application/json")
	public StreamingOutput getAccion(@PathParam("id") int id, @PathParam("accion") String accion, @PathParam("c1") String c1, @PathParam("c2") String c2, @PathParam("c3") String c3) {
	//public StreamingOutput getAccion(@PathParam("id") int id, @PathParam("accion") String accion) {
		final Cliente cliente = this.clienteServicio.getClienteById(clientes, id);
		String result = "";
		if (cliente == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if (c1.isEmpty() || c2.isEmpty() || c3.isEmpty()){
			throw new WebApplicationException(Response.Status.EXPECTATION_FAILED);
		}
		try {
			if (validarParam(c1, cliente) && validarParam(c2, cliente) && validarParam(c3, cliente)){
				result = " Aprobada";
			}else{
				result = " Denegada";
			}
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.EXPECTATION_FAILED);
		}
		String mensaje = "Accion: " + accion +" para cliente: " + cliente.getNombre() +" fue "+result+" !!!!";
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputCliente(outputStream, mensaje);
			}
		};
	}
	
	@POST
	@Path("agregar")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addCliente(Cliente cliente) {
		Cliente create = this.clienteServicio.crearCliente(cliente);
		this.clienteServicio.addClienteToPool(clientes, create);
		System.out.println("Created	customer	" + create.getId());
		return Response.created(URI.create("/agregar/" + create.getId())).build();		
		
	}
	
	protected Cliente readCliente(InputStream is) {
		try {
		     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		     DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
		     documentBuilder.parse(is);
		   
			Document doc = documentBuilder.parse(is);
			Element root = doc.getDocumentElement();
			Cliente cliente = new Cliente();
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				if (element.getTagName().equals("nombre")) {
					cliente.setNombre(element.getTextContent());
				} else if (element.getTagName().equals("cedula")) {
					cliente.setCedula(element.getTextContent());
				}
			}
			return cliente;
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	
	protected void outputCliente(OutputStream os, String data) throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println(data);
	}
	
	private boolean validarParam(String param, Cliente cliente) throws Exception{
		StringTokenizer st = new StringTokenizer(param, ".");
		if (st.countTokens() != 3)
			throw new Exception();
		int coordenadaX = Integer.parseInt(st.nextToken());
		int coordenadaY = Integer.parseInt(st.nextToken());
		String valor = st.nextToken();
		return this.clienteServicio.isCoordenadaValida(coordenadaX, coordenadaY, valor, cliente);
	}

}
