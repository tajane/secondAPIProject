package demoRestAssured;

import java.util.List;

import org.springframework.util.SystemPropertyUtils;
import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		JsonPath js2 = new JsonPath(payload.coursePrize());

// get mumber of courses 		
		int coursecount = js2.getInt("courses.size()");
		System.out.println("courses count is  : " + coursecount);
	
// get purchase prize
		int purchaseprize = js2.getInt("dashboard.purchaseAmount");
		System.out.println("purchase prize is  : " + purchaseprize);
	
//  print title  of first course
		String titleoffirstcourse = js2.get("courses[0].title");
		System.out.println("title  of first course is  : " + titleoffirstcourse);
	
	//  print all courses title and there respective prices
			List<String> course_title = js2.get("courses.title");
			List<String> course_prise = js2.get("courses.price");
			System.out.println("all courses title  is  : " + course_title );	
			System.out.println("all courses prises is  : " + course_prise );
// better solution 
			
			for(int i=0;i<coursecount;i++)
			{
				String course_title_prise = js2.get("courses["+i+"].title");
				System.out.println("course title : " + course_title_prise);
				System.out.println("course prise : " + js2.get("courses["+i+"].price").toString());
			}
	
// print the number of copies sold by RPA course 
			
			for(int i=0;i<coursecount;i++)
			{
				String course_title_prise = js2.get("courses["+i+"].title");
				if(course_title_prise.equalsIgnoreCase("RPA"))
				{
					int get_copies_name = js2.get("courses["+i+"].copies");
					System.out.println("number of copies sold by RPA course : " + get_copies_name);
					break;
				}
			}
			
// verify if sum  of  all course matches with purchase amount	
			
			int sum=0;
			
			for(int i=0;i<coursecount;i++)
			{
				int coureprize = js2.get("courses["+i+"].price");
				int numberofcopies = js2.get("courses["+i+"].copies");
				int multiplyprise_copies = coureprize * numberofcopies;
			    System.out.println(multiplyprise_copies);
				sum  = multiplyprise_copies + sum;	
			}
			
			System.out.println("sum of all courses is : " + sum);
			
			int get_purchase_amount = js2.get("dashboard.purchaseAmount");
			
			Assert.assertEquals(sum, get_purchase_amount);
			
			/*if(sum==get_purchase_amount) 
			{
			   System.out.println(" purchase amount same");	
			}else
			{
				System.out.println("purchase amount not same");
			}*/
	}
}
