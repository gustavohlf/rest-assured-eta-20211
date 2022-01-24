import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;

public class GetUsersTest extends TestBase{
    private User validateUser1;
    private User validateUser2;
    private int getQuantity = 0;



    @BeforeClass
    public void generateTestData(){
        validateUser1 = new User("Francisco", "francisco2022@gmail.com", "teste", "false");
        validateUser2 = new User("Xico", "xicoLixo@gmail.com", "teste", "false");
        postUserRequest(SPEC, validateUser1);
        postUserRequest(SPEC, validateUser2);

    }

    @DataProvider(name = "userQueryData")
    public Object[][] createQueryData(){
        return new Object[][]{
                {"nome", validateUser1.nome},
                {"email", validateUser2.email}
        };
    }

    @Test(dataProvider = "userQueryData")
    public void listAllUsers(String query, String queryValue){
        SPEC.queryParam(query, queryValue);
        Response getUsersResponse =  getUserRequest(SPEC);
        getUsersResponse.
                then().log().all();

        FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) SPEC;
        filterableRequestSpecification.removeQueryParam(query);
    }

    @Test
    public void getAllUsersAndValidate(){
        Response getUsersResponse =  getUserRequest(SPEC);
        getQuantity = getIntegerResponseValue(getUsersResponse, "quantidade");
        getUsersResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(17)).
                body("quantidade", instanceOf(Integer.class)).
                body("usuarios", instanceOf(List.class));
    }

    @Test
    public void deleteAllUsers(){
        Response getUsersResponse =  getUserRequest(SPEC);
        getQuantity = getIntegerResponseValue(getUsersResponse, "quantidade");
        List<String> userList = getReponseList(getUsersResponse, "usuarios");
    }


    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validateUser1);
        deleteUserRequest(SPEC, validateUser2);
    }
}
