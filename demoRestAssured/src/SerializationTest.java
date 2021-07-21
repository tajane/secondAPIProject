import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;

public class SerializationTest 
{

	public static void main(String[] args) 
	{
		
		AddPlace addplace = new AddPlace();
		addplace.setAccuracy(50);
		addplace.setAddress("29, side layout, cohen 09");
		addplace.setLanguage("French-IN");
		addplace.setPhone_number("(+91) 983 893 3937");
		addplace.setWebsite("http://google.com");
		addplace.setName("Frontline house");
		
		List<String> listsetype = new ArrayList<String>();
		listsetype.add("shoe park");
		listsetype.add("shop");
		
		addplace.setTypes(listsetype);
		
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		
		addplace.setLocation(location);
		
		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com"; 
		Response response = 
			given().log().all().queryParam("key", "qaclick123")
					.body(addplace).
			when().post("maps/api/place/add/json").
			then().log().all().assertThat().statusCode(200).extract().response();
		
		String getresponse = response.asString();
		System.out.println("response from post method is : " + getresponse);

	}

}
