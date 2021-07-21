import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest 
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
		
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		ResponseSpecification responsespec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification res = given().spec(req).body(addplace);
		
		Response response = res.when().post("maps/api/place/add/json").
			then().spec(responsespec).extract().response();
		
		
		System.out.println("response from post method is : " + response.asString());

		/*RequestSpecification req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
				 
				 
				ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
				RequestSpecification res=given().spec(req)
				.body(addplace);

				Response response =res.when().post("/maps/api/place/add/json").
				then().spec(resspec).extract().response();

				String responseString=response.asString();
				System.out.println(responseString);*/
	}

}
