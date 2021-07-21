package demoRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.payload;
import io.restassured.RestAssured;

public class staticjsondatausingfile 
{

// calling to external json file in test
	@Test
	public void staticJsonData() throws IOException
	{
		RestAssured.baseURI= "https://rahulshettyacademy.com"; 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("D:\\adddata.json")))).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)");
	}
}
