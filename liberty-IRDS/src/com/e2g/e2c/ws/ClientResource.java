package com.e2g.e2c.ws;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.e2g.e2c.model.Client;
import com.e2g.e2c.model.Operation;

@Path("/client")
public class ClientResource {
	
	private UserTransaction utx;
	private EntityManager em;

    public ClientResource() {
		utx = getUserTransaction();
		em = getEm();
	}
	
	private UserTransaction getUserTransaction() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (UserTransaction) ic.lookup("java:comp/UserTransaction");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	private EntityManager getEm() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			return (EntityManager) ic.lookup("java:comp/env/openjpa-todo/entitymanager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Client ent) {
		try {
			utx.begin();
			if(ent.getIdCliente()==0){
				//verificar se o login já existe
				//Client c = em.createNamedQuery("Client.findByLogin",Client.class).setParameter("login", ent.getLogin()).getSingleResult();
				ent.setIdCliente(null);
				em.persist(ent);
			}else{
				em.merge(ent);
			}
			utx.commit();
			return Response.ok().build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return Response.ok(ent.toString()).build();
		}
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id){
		Client ent = new Client((long)0);
		String json = ent.toString();
		try{	
			if(id==0){
				List<Client> ents = em.createNamedQuery("Client.findAll",Client.class).getResultList();
				json = ents.toString();
			}else{
				ent = (Client) em.createNamedQuery("Client.findByIdCliente").setParameter("idCliente", id).getSingleResult();
				json = ent.toString();
			}
		}catch (Exception er) {
			json= ent.toString();
		}
		return Response.ok(json).build();
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Client ce){
		String jsonString =new Client().toString();
		try{
			Client c = em.createNamedQuery("Client.findByLogin",Client.class).setParameter("login", ce.getLogin()).getSingleResult();
			if(ce.getPass().equals(c.getPass())){
				jsonString = c.toString();
			}
		}catch (Exception e) {
			return Response.status(401).build();
		}
		
		return Response.ok(jsonString).build();
	}
	
	@GET
	@Path("/coins/{idClient}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCoins(@PathParam("idClient") Long id){
		Client ent = new Client((long)0);
		String json = ent.toString();
		Float coins = Float.valueOf(0);
		Float gasto = Float.valueOf(0);
		
		try{
			ent = (Client) em.createNamedQuery("Client.findByIdCliente").setParameter("idCliente", id).getSingleResult();
			List<Operation> ents = (List<Operation>) em.createNamedQuery("Operation.findByClient",Operation.class).setParameter("idCliente", ent).getResultList();
			json = ents.toString();
			for (Operation operation : ents) {
				if(operation.getPrice()!=null){
					coins = coins+operation.getEcoCoin();
				}else{
					gasto = gasto+operation.getEcoCoin();
				}
			}
			
		}catch (Exception e) {
			 coins = Float.valueOf(0);
			 gasto = Float.valueOf(0);
		}
		json =String.format("{\"coins\":\"%.2f\", \"xp\":\"%.2f\"}", coins,gasto);
		return Response.ok(json).build();
	}
	@GET
	@Path("/operations/{idClient}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOperation(@PathParam("idClient") Long id){
		Client ent = new Client((long)0);
		String json = ent.toString();
		List<Operation> ents = new ArrayList<Operation>();
		StringBuilder sb = new StringBuilder("[");
		try{
			ent = (Client) em.createNamedQuery("Client.findByIdCliente").setParameter("idCliente", id).getSingleResult();
			ents = (List<Operation>) em.createNamedQuery("Operation.findByClient",Operation.class).setParameter("idCliente", ent).getResultList();
			for (Operation operation : ents) {
				sb.append(operation.toStatment());
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]");
			
		}catch (Exception e) {
			 json = "[ ]";
			 return Response.ok(json).build();
		}
		return Response.ok(sb.toString()).build();
	}
	
	
	
}
