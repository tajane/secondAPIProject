package demoRestAssured;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class jiratest 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		RestAssured.baseURI= "http://localhost:8080"; 
// login scenario
		SessionFilter session = new SessionFilter();
		String extractresponseaddbook = 
		
		given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json").
				body("{\r\n" + 
					"    \"username\": \"nitintajane3\",\r\n" + 
					"    \"password\": \"Nitin@1111\"\r\n" + 
					"}").log().all().filter(session).
		when().post("rest/auth/1/session").
		then().log().all().extract().response().asString();
		
		System.out.println("add book response : " + extractresponseaddbook);
	
// add comment

		String expectedcomment = "after passing comment id test 6";
		String getcommentresponse = given().pathParam("key","10101").log().all().header("Content-Type","application/json").
		body("{\r\n" + 
				"    \"body\": \""+expectedcomment+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session).when().post("rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
// here we are extracting response to get id of last added comments	
		
				JsonPath jp = new JsonPath(getcommentresponse);
				String commentId = jp.getString("id");
				int covertcommentid = Integer.valueOf(commentId);
				System.out.println("comment id is : " + commentId);
				
		
		
// add attachment
		
		/*given().pathParam("key","10101").header("X-Atlassian-Token", "no-check").header("Content-Type","multipart/form-data").filter(session).
		multiPart("file", new File("jira.text")).when().
		post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);*/
	
// use path parameter and query parameter in single script 
// script for get issue details		
		
		String newdata =
		given().filter(session).pathParam("key","10101").
		queryParam("fields", "comment")
		.log().all().when().
		get("rest/api/2/issue/{key}").then().assertThat().extract().response().asString();
		System.out.println("get issue details : " + newdata);
		
		JsonPath js = new JsonPath(newdata);
		int getsizeofcommentarray = js.getInt("fields.comment.comments.size()");
		System.out.println("print number of array :" + getsizeofcommentarray);
		
		String actualcomment = "";
		for(int i=0;i<getsizeofcommentarray;i++)
		{
			int fromloopcommentid = js.getInt("fields.comment.comments["+i+"].id");
			if(covertcommentid==fromloopcommentid)
			{
				//String getrespectivecomment = js.get("fields.comment.comments["+i+"]").toString();
				
				// in above line if you not mention toString() then code will not be run it give error for hashmap 
				// means that data not present in string it is in hashmap so we are converting data into string
				// above all comments contains in array so its give error
				// below line we are geting body and body alrady present in string format
				
				actualcomment = js.get("fields.comment.comments["+i+"].body");
				System.out.println("get exact comment which is added last time : " + actualcomment);
				break;
			}
			
		}
		
		Assert.assertEquals(actualcomment, expectedcomment);
		
	}

}
