package twitterapiexample;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TwitterEndToEndWorkflow {
	String consumerKey = "2GMuRK1Y4HsVQ4v5Dzzz4YN2S";
	String consumerSecret = "bzlNl5yw7agfZI50kXh8mY4H6LLkdxt9hzMgj6Jt1riThZQ9iu";
	String accessToken = "2853753255-QAVUeknkoh9L233yCRaHzvwfWM1XupxA1qN3zUN";
	String accessSecret = "j0tdUZ47fsatAyT03PmbvJKfSg99yAF2mv7nnRiWuZTLN";
	String tweetId = "";

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Test
	public void postTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("status", "My First Tweet")
			.when()
				.post("/update.json")
			.then()
				.statusCode(200)
				.extract()
				.response();
		
		tweetId = response.path("id_str");
		System.out.println("The response.path: " + tweetId);
	}
	
	@Test(dependsOnMethods={"postTweet"})
	public void readTweet() {
		Response response =
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.queryParam("id", tweetId)
			.when()
				.get("/show.json")
			.then()
				.extract()
				.response();
		
		String text = response.path("text");
		System.out.println("The tweet text is: " + text);
	}

	@Test(dependsOnMethods={"readTweet"})
	public void deleteTweet() {
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
				.pathParam("id", tweetId)
			.when()
				.post("/destroy/{id}.json")
			.then()
				.statusCode(200);
	}

	
}