package demoRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.payload;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class basic2ExtractResponse 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//add place > and update place with new address > get place to validate if new address present in response 

// add place	
		
			RestAssured.baseURI= "https://rahulshettyacademy.com"; 
			String getresponse = 
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
						.body(payload.addPlace()).
				when().post("maps/api/place/add/json").
				then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
						.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
					System.out.println("get response :" + getresponse);
					JsonPath js =  new JsonPath(getresponse); 
					String placeid = js.getString("place_id");
					System.out.println("place id  is : " + placeid);
		
		
// update place script
					
		String newAddress  =  "nitin tajane";
	
				given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
						.body("{\r\n" + 
								"\"place_id\":\""+placeid+"\",\r\n" + 
								"\"address\":\""+newAddress+"\",\r\n" + 
								"\"key\":\"qaclick123\"\r\n" + 
								"}\r\n" + 
								"").
				when().put("maps/api/place/update/json").
				then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
// get place
		
		String getResponseFromGet = 
		
				given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeid).
				when().get("maps/api/place/get/json").
				then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
				String actualaddress = reusableMethods.rawToJson(getResponseFromGet).getString("address");
				Assert.assertEquals(actualaddress, newAddress);
				// we use  testng assert function
				System.out.println("actual address is : " + actualaddress);
	}

}
