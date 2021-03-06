package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	static RequestSpecification req;
	static Properties prop;
	public RequestSpecification requestSpecification() throws IOException {
		if(req==null) {
		PrintStream log = new PrintStream(new FileOutputStream("loging.txt"));
		 req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	
	public static String getGlobalValue(String key) throws IOException {
		 prop =  new Properties();
		FileInputStream fls = new FileInputStream("src\\test\\java\\resources\\global.properties");
		prop.load(fls);
		return prop.getProperty(key);
	}
	
	public static void addGlobalValues(String key , String value) throws IOException {
		FileOutputStream out = new FileOutputStream("src\\test\\java\\resources\\global.properties");
		prop.setProperty(key, value);
		prop.store(out, null);
		out.close();
	}
	
	protected String getJsonPath(Response response,String Key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(Key).toString();
	}
	
}
