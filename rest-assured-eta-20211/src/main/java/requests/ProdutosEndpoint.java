package requests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Produtos;
import models.User;

import static io.restassured.RestAssured.given;

public class ProdutosEndpoint extends RequestBase{
    public static Response getProdutosRequest(RequestSpecification spec) {
        Response produtosRequest =
                given().
                        spec(spec).
                when().
                        get("/produtos");
        return produtosRequest;
    }

    public static Response postProdutosRequest(RequestSpecification spec, User user, Produtos produto){
        Gson gson = new Gson();
        String produtosJsonRepresentation = gson.toJson(produto);
        Response postProdutosResponse =
            given().
                    spec(spec).
                    header("Content-Type", "application/json").
                    header("Authorization", user.getAuthorization()).
                    and().
                    body(produtosJsonRepresentation).
            when().
                    post("/produtos");
        produto.setIdProduto(getResponseValue(postProdutosResponse,"_id"));
        return postProdutosResponse;
    }

    public static Response deletarProduto(RequestSpecification spec, User user, Produtos produto){
        Response deleteProdutosResponse =
                given().
                        spec(spec).
                        header("Authorization", user.getAuthorization()).
                        and().
                        pathParam("_id", produto.getProdutoId()).
                when().
                        delete("/produtos/{_id}");
        return deleteProdutosResponse;
    }

    public static Response deletarProdutos(RequestSpecification spec, User user, String id){
        Response deleteProdutosResponse =
                given().
                        spec(spec).
                        header("Authorization", user.getAuthorization()).
                        and().
                        pathParam("_id", id).
                        when().
                        delete("/produtos/{_id}");
        return deleteProdutosResponse;
    }

}
