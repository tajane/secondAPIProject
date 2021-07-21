package demoRestAssured;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;


public class basics1 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		RestAssured.baseURI= "https://rahulshettyacademy.com"; 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(payload.addPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)");	
	}
}
