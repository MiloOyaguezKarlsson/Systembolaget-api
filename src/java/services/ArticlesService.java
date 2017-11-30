/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import beans.ArticlesBean;
import javax.ejb.EJB;
import javax.json.JsonArray;
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
public class ArticlesService {
    @EJB
    ArticlesBean articlesBean;
    
    @GET
    @Path("articles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticles(){
        JsonArray articles = articlesBean.getArticles();
        if(articles != null){
            return Response.ok(articles).build();
        } else{
            return Response.serverError().build();
        }
    }
    @GET
    @Path("articles_in_store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticlesInStore(@QueryParam("id") int id){
        JsonArray articles = articlesBean.getArticlesInStore(id);
        if(articles != null){
            if(articles.getJsonObject(0).containsKey("ERROR")){
                return Response.status(400).build();
            }
            return Response.ok(articles).build();
        } else{
            return Response.serverError().build();
        }
    }
    @GET
    @Path("search_articles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchArticles(@QueryParam("query") String query){
        JsonArray articles = articlesBean.searchArticles(query);
        if(articles != null){
            return Response.ok(articles).build();
        } else{
            return Response.serverError().build();
        }
    }
    @GET
    @Path("search_articles_in_store")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchArticlesInStore(@QueryParam("query") String query, @QueryParam("id") int id){
        JsonArray articles = articlesBean.searchArticlesInStore(query, id);
        if(articles != null){
            return Response.ok(articles).build();
        } else{
            return Response.serverError().build();
        }
    }
    
    
    
}
