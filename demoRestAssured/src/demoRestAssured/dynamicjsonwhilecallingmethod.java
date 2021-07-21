package demoRestAssured;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class dynamicjsonwhilecallingmethod 
{
	@Test()
	public void addBook() // here we are initialising array elements 
	{
		RestAssured.baseURI= "http://216.10.245.166"; 
		String extractresponseaddbook = 
		
		given().log().all().header("Content-Type","application/json").
			body(payload.addBookpostjson("3rrer","3434")). // here you can directly send body data means json data
		when().post("Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println("add book response : " + extractresponseaddbook);
		
		JsonPath js = reusableMethods.rawToJson(extractresponseaddbook);
		String idaddbook = js.get("ID");
		System.out.println("print book id : " + idaddbook);
	}
}
