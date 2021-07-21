package demoRestAssured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class dynamicjsonwithdataprovider 
{

	@Test(dataProvider="bookdata")
	public void addBook(String isb,String aisle) // here we are initialising array elements 
	{
		RestAssured.baseURI= "http://216.10.245.166"; 
		String extractresponseaddbook = 
		
		given().log().all().header("Content-Type","application/json").
			body(payload.addBookpostjson(isb,aisle)).
		when().post("Library/Addbook.php").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println("add book response : " + extractresponseaddbook);
		
		JsonPath js = reusableMethods.rawToJson(extractresponseaddbook);
		String idaddbook = js.get("ID");
		System.out.println("print book id : " + idaddbook);
	}
	
	@DataProvider(name="bookdata")
	public Object[][] getAddBookData()
	{
		return new Object[][] {{"tajane","1993"},{"fdrfd","1994"},{"fdr","1995"}};
	}
}
