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
		String json = "[]";
		try{	
			if(id==0){
				List<PontoTroca> list = em.createNamedQuery("PontoTroca.findAll",PontoTroca.class).getResultList();
				List<PontoTroca> ents = new ArrayList<PontoTroca>();
				for (PontoTroca pontoTroca : list) {
					if(pontoTroca.getLatitude()!=null){
						ents.add(pontoTroca);
					}
				}				
				json =  ents.toString() ;
			}else{
				ent = (PontoTroca) em.createNamedQuery("PontoTroca.findByIdPontoColeta").setParameter("idPontoColeta", id).getSingleResult();
				json = ent.toString();
			}
		}catch (Exception er) {
			json= "[]";
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
	
	@GET
	@Path("/profile/{idPoint}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(@PathParam("idPoint") Long id){
		//PontoTroca ent = new PontoTroca((long)0);
		//List<Operation> ents = new ArrayList<Operation>();
		StringBuilder sb = new StringBuilder("{");
		Float metal,plastic,paper,glass,organic,general;
		general=metal=plastic=paper=glass=organic=Float.valueOf(0);
		//try{
			//ent = (PontoTroca) em.createNamedQuery("PontoTroca.findByIdPontoColeta").setParameter("idPontoColeta", id).getSingleResult();
			//for(ProductPoint pp : ent.getProductPointCollection()){
				//List<Operation> ents = (List<Operation>) em.createNamedQuery("Operation.findByProductPoint",Operation.class).setParameter("productPointidProdutoPonto", pp).getResultList();
		List<Operation> ents = (List<Operation>) em.createNamedQuery("Operation.findByidPoint",Operation.class)
						.setParameter("idPontoColeta",id).getResultList();
		for (Operation operation :ents) {
			if(!operation.isBuy()){
				String material = operation.getProductPointidProdutoPonto().getProductidProduto().getMaterial();
				general = general+operation.getEcoCoin();
				if(material.equalsIgnoreCase("metal")){
					metal=metal+operation.getEcoCoin();
				}else if(material.equalsIgnoreCase("plastic")){
					plastic = plastic+operation.getEcoCoin();
				}else if(material.equalsIgnoreCase("paper")){
					paper = paper+operation.getEcoCoin();
				}else if(material.equalsIgnoreCase("glass")){
					glass = glass+operation.getEcoCoin();
				}else if(material.equalsIgnoreCase("organic")){
					organic = organic+operation.getEcoCoin();
				}
			}
		}
		
		//String entrada = in.readLine();
		float eco =general;
		int an=1, at=1,p=0;
		if(eco >= 100000){
			for (int i = 1; i < 11; i++) {
				p=an+at;
				sb.append(eco>=at*100000 && eco<p*100000?
						"\"Level\":\""+i+"\",\"next\":\""+p*100000+"\",\"xp\":\""+eco+"\",":"");
				an=at;
				at=p;
			}
		}else{
			sb.append("\"Level\":\""+0+"\",\"next\":\""+100000+"\",\"xp\":\""+eco+"\",");
		}
		sb.append(nivelProdutos("glass", glass)).append(",");
		sb.append(nivelProdutos("paper", paper)).append(",");
		sb.append(nivelProdutos("plastic", plastic)).append(",");
		sb.append(nivelProdutos("organic", organic)).append(",");
		sb.append(nivelProdutos("metal", metal));
		sb.append("}");
		return Response.ok(sb.toString()).build();
		
	}
	private String nivelProdutos(String mat, float eco){
		//String entrada = in.readLine();
		//float eco =Float.parseFloat(entrada);
		int an=3, at=5,p=0;
		if(eco >= 50000){
			for (int i = 1; i < 11; i++) {
				p=an+at;
				if(eco>=at*10000 && eco<p*10000){
						return "\""+mat+"\":{\"Level\":\""+i+"\",\"next\":\""+p*10000+"\",\"xp\":\""+eco+"\"}";
				}
				an=at;
				at=p;
			}
		}else{
			return "\""+mat+"\":{\"Level\":\""+0+"\",\"next\":\""+50000+"\",\"xp\":\""+eco+"\"}";
		}
		return null;	
	}
	
	@GET
	@Path("/profile/recs/{idPoint}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecs(@PathParam("idPoint") Long id){
		List<Operation> operations = (List<Operation>) em.createNamedQuery("Operation.findByidPoint",Operation.class)
				.setParameter("idPontoColeta",id).getResultList();
		return Response.ok(operations.toString()).build();
	}
	
	
}
