package com.e2g.e2c.ws;

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
import com.e2g.e2c.model.PontoTroca;



@Path("/point")
public class PontoTrocaResource {

	private UserTransaction utx;
	private EntityManager em;

    public PontoTrocaResource() {
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
	public Response create(PontoTroca ent) {
		try {
			utx.begin();
			if(ent.getIdPontoColeta()==0){
				ent.setIdPontoColeta(null);
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
		PontoTroca ent = new PontoTroca((long)0);
		String json = ent.toString();
		try{	
			if(id==0){
				List<PontoTroca> ents = em.createNamedQuery("PontoTroca.findAll",PontoTroca.class).getResultList();
				json =  ents.toString() ;
			}else{
				ent = (PontoTroca) em.createNamedQuery("PontoTroca.findByIdPontoColeta").setParameter("idPontoColeta", id).getSingleResult();
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
	public Response login(PontoTroca point){
		String jsonString =new Client().toString();
		try{
			PontoTroca p = em.createNamedQuery("PontoTroca.findByLogin",PontoTroca.class).setParameter("login", point.getLogin()).getSingleResult();
			if(p.getPassword().equals(p.getPassword())){
				jsonString = p.toString();
			}
		}catch (Exception e) {
			return Response.status(401).build();
		}
		
		return Response.ok(jsonString).build();
	}
	
	
}
