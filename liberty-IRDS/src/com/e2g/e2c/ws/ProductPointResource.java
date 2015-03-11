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

import com.e2g.e2c.model.ProductPoint;

@Path("/productpoint")
public class ProductPointResource {

	private UserTransaction utx;
	private EntityManager em;

    public ProductPointResource() {
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
	public Response create(ProductPoint ent) {
		try {
			utx.begin();
			if(ent.getIdProdutoPonto()==0){
				ent.setIdProdutoPonto(null);
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
		ProductPoint ent = new ProductPoint((long)0);
		String json = ent.toString();
		try{	
			if(id==0){
				List<ProductPoint> ents = em.createNamedQuery("ProductPoint.findAll",ProductPoint.class).getResultList();
				json =  ents.toString();
			}else{
				ent = (ProductPoint) em.createNamedQuery("ProductPoint.findByIdProdutoPonto").setParameter("idProdutoPonto", id).getSingleResult();
				json = ent.toString();
			}
		}catch (Exception er) {
			json= ent.toString();
		}
		
		return Response.ok(json).build();
	}
	
	
	
	@GET
	@Path("/produtosponto/{idponto}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProdutosPonto(@PathParam("idponto") Long id){
		ProductPoint ent = new ProductPoint((long)0);
		String json = ent.toString();
		try{	
			if(id==0){
				List<ProductPoint> ents = em.createNamedQuery("ProductPoint.findAll",ProductPoint.class).getResultList();
				json = ents.toString();
			}else{
				List<ProductPoint> ents = em.createNamedQuery("ProductPoint.findByPoint",ProductPoint.class).setParameter("idPonto", id).getResultList();
				json = ents.toString();
			}
			
		}catch (Exception er) {
			json= ent.toString();
		}
		return Response.ok(json).build();
	}
	
	
		
}
