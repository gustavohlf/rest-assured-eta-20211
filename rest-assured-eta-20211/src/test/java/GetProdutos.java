import io.restassured.response.Response;
import models.Produtos;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import requests.ProdutosEndpoint;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static requests.LoginEndpoints.postLoginRequest;
import static requests.ProdutosEndpoint.*;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;

public class GetProdutos extends TestBase{
    private User validUser;
    private Produtos produtos;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("User", "user_test@user.com", "1234@", "true");
        postUserRequest(SPEC, validUser);
        postLoginRequest(SPEC, validUser);
        produtos = new Produtos("Produto de Teste", "25.0", "Teste de Produto", "500");
        postProdutosRequest(SPEC, validUser, produtos);
    }

    @Test
    public void getProdutosStatus200(){
        Response getProdutosResponse = getProdutosRequest(SPEC);
        getProdutosResponse.then().log().all();
        getProdutosResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(3)).
                body("quantidade", instanceOf(Integer.class)).
                body("produtos", instanceOf(List.class))
                ;
    }

    @AfterClass
    public void after(){
        deletarProduto(SPEC, validUser, produtos);
        deleteUserRequest(SPEC, validUser);
    }
}
