import io.restassured.response.Response;
import models.Produtos;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.LoginEndpoints.postLoginRequest;
import static requests.ProdutosEndpoint.deletarProduto;
import static requests.ProdutosEndpoint.postProdutosRequest;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;

public class PostProdutos extends TestBase {
    private User validUser;
    private User invalidUser;
    private Produtos produtos;
    private Produtos invalidProdutos;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("User", "user_cad@user.com", "1234@", "true");
        postUserRequest(SPEC, validUser);
        postLoginRequest(SPEC, validUser);
    }

    @Test
    public void inserirProduto(){
        produtos = new Produtos("N1", "20.0", "Teste", "50");
        Response getPostProdutosResponse = postProdutosRequest(SPEC, validUser, produtos);
        getPostProdutosResponse.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo("Cadastro realizado com sucesso")).
                body("message", instanceOf(String.class)).
                body("_id", notNullValue());
    }

    @Test
    public void postProdutosStatus403(){
        invalidUser = new User("User", "user_alfabeto@user.com", "1234@", "false");
        invalidProdutos = new Produtos("N2", "20.0", "Teste", "50");
        postUserRequest(SPEC, invalidUser);
        postLoginRequest(SPEC, invalidUser);
        Response getPostProdutosResponse = postProdutosRequest(SPEC, invalidUser, invalidProdutos);
        getPostProdutosResponse.
                then().
                    assertThat().
                    statusCode(403).
                    body("message", equalTo("Rota exclusiva para administradores"));
        deleteUserRequest(SPEC, invalidUser);
    }

    @AfterClass
    public void afterClass(){
        deletarProduto(SPEC, validUser, produtos);
        deleteUserRequest(SPEC, validUser);
    }

}
