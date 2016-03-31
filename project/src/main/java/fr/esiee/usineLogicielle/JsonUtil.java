package fr.esiee.usineLogicielle;

import spark.ResponseTransformer;

import com.google.gson.Gson;

public class JsonUtil {
	 
	  public static String toJson(Object object) {
		  return new Gson().toJson(object);
	  }
	  
	  public static Task fromJson(String s)
	  {
		  return new Gson().fromJson(s, Task.class);
	  }
	 
	  public static ResponseTransformer json() {
		  return JsonUtil::toJson;
	  }
	}
