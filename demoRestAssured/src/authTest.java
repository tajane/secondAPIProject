import static io.restassured.RestAssured.given;

import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourcesRespose;

public class authTest 
{

	public static void main(String[] args)
	{
	
		
// get access token	
		// use of url encoding method use for we code as it is but rest assured convert % sign into number
		// so restrict that we  need to false the encoding 
		// because rest assure automatically do encoding
		String response_get_access_token =
				given().log().all().urlEncodingEnabled(false).queryParam("code", "4%2F0AY0e-g7dBwLdcAMCfbIYDI2HSmbP9os-TCberFxwEGhtfG5LXWx-3SExy_1iyLZCt9XZWg").
				queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
				queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W").
				queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
				queryParam("grant_type","Authorizationcode").
				when().log().all().
				post("https://www.googleapis.com/oauth2/v4/token").asString();
				JsonPath jsonpath = new JsonPath(response_get_access_token);
				String accesstoken = jsonpath.getString("access_token");
				System.out.println("print access token " + accesstoken);
		
		
		
		
	// here we do de-serialization	
		
				GetCourcesRespose response =
				given().log().all().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON).
				when().
				get("https://rahulshettyacademy.com/getCourse.php").as(GetCourcesRespose.class);
				System.out.println("get response to get code : " + response.getLinkedIn());
				System.out.println("get response to get code : " + response.getCourses().getApi().get(1).getCourseTitle());
				// above script we get course title from first array of api and from course array
				
				List<Api> getapidetails = response.getCourses().getApi();
				// in api having one array and in that content elements so need to get into list format
				// below code for get perticular  information from get api   element 
				for(int i=0;i<getapidetails.size();i++)
				{
					if(getapidetails.get(i).getCourseTitle().equalsIgnoreCase("match any course title"))
					{
						getapidetails.get(i).getPrice();
						break;
						// then here we get prise when title get match
						
					}
					
					
				}
				
				
		
// this deafultparser we explict convert into json format because in our header having content type as text/html 
				// so it convert into json
	}

}
