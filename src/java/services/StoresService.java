/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.StoresBean;
import javax.ejb.EJB;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author milooyaguez karlsson
 */
@Path("/")
public class StoresService {
    @EJB
    StoresBean storesBean;
    
    
    @GET
    @Path("stores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores(){
        JsonArray stores = storesBean.getStores();
        if(stores != null){
            return Response.ok(stores).build();
        } else{
            return Response.serverError().build();
        }
    }
    @GET
    @Path("store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStore(@QueryParam("id") int id){
        JsonObject store = storesBean.getStore(id);
        if(store != null){
            if(store.containsKey("ERROR")){
                return Response.status(400).build();
            }
            return Response.ok(store).build();
        } else{
            return Response.serverError().build();
        }
    }
    @GET
    @Path("stores_search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoresByCity(@QueryParam("query") String query){
        JsonArray stores = storesBean.searchStoresByCity(query);
        if(stores != null){
            return Response.ok(stores).build();
        } else{
            return Response.serverError().build();
        }
    }
    
}
