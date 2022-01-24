import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static requests.LoginEndpoints.postLoginRequest;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;

public class PostLoginTest extends TestBase{
    private User validUser;

    @BeforeClass
    public void generateTestData(){
        validUser = new User("Xicorita", "email_1sss@gmail.com", "1234@", "false");
        postUserRequest(SPEC, validUser);
    }

    @Test
    public void loginTestWithValidUser(){
        Response loginUserResponse = postLoginRequest(SPEC, validUser);
        loginUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", is("Login realizado com sucesso")).
                body("authorization", notNullValue())
                ;
        loginUserResponse.then().log().all();
    }

    @AfterClass
    public void afterClass(){
        deleteUserRequest(SPEC, validUser);
    }
}
