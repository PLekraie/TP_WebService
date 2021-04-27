package fr.epsi.store.ressource;

import fr.epsi.store.db.ProductDB;
import fr.epsi.store.model.Product;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api")
public class ProductRessources {
    private final static ProductDB database = new ProductDB();;

    public ProductRessources() {
    }

    /**
     * @return list of products
     */
    @GET
    @Path("/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return database.getProducts();
    }

    /**
     * Add a list of products
     *
     * @param products JSON [{"name": string,"detail": string,"price": int,"quantity": int,"info": string,"image": string},{...}]
     * @return ok with lis of created product if products were added, 400 otherwise
     */
    @POST
    @Path("/products")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postProducts(List<Product> products) {
        List<Product> result = database.createProducts(products);
        return result != null ? Response.ok(result).build() : Response.status(400).build();
    }

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") int id) {
        Product result = database.getProduct(id);
        return result != null ? Response.ok(result).build() : Response.status(404).build();
    }

    @PUT
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putProduct(@PathParam("id") int id, Product product) {
        if(id != product.getId()){
            return Response.status(400).build();
        }
        Product result = database.updateProduct(id, product);
        return result != null ? Response.ok(result).build() : Response.status(404).build();
    }

    /**
     * Add a product
     *
     * @param product JSON {"name": string,"detail": string,"price": int,"quantity": int,"info": string,"image": string}
     * @return ok created product if product was added, 400 otherwise
     */
    @POST
    @Path("/product")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postProduct(Product product) {
        Product result = database.createProduct(product);
        return result != null ? Response.ok(result).build() : Response.status(400).build();
    }

    @DELETE
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") int id) {
        Product result = database.deleteProduct(id);
        return result != null ? Response.ok(result).build() : Response.status(404).build();
    }

    @GET
    @Path("/product/search/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProductsBySearch(@PathParam("search") String search) {
        return database.getProductsBySearch(search);
    }

    @POST
    @Path("/product/buy/{id}/{quantity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response buyProduct(@PathParam("id") int id, @PathParam("quantity") int quantity) {
        String result = database.buyProduct(id, quantity);
        return Response.ok(result).build();
    }
}
