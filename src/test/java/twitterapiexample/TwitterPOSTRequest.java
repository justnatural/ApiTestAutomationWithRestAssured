package twitterapiexample;

import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class TwitterPOSTRequest {
	
	String consumerKey = "2GMuRK1Y4HsVQ4v5Dzzz4YN2S";
	String consumerSecret = "bzlNl5yw7agfZI50kXh8mY4H6LLkdxt9hzMgj6Jt1riThZQ9iu";
	String accessToken = "2853753255-QAVUeknkoh9L233yCRaHzvwfWM1XupxA1qN3zUN";
	String accessSecret = "j0tdUZ47fsatAyT03PmbvJKfSg99yAF2mv7nnRiWuZTLN";
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}
	
	@Test
	public void statusCodeVerification() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessSecret)
			.queryParam("status", "Posting A Tweet")
		.when()
			.post("/update.json")
		.then()
			.statusCode(200);
	}
}