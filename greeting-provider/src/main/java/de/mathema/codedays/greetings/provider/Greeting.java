package de.mathema.codedays.greetings.provider;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("greeting")
public class Greeting {

	private String type;
	private String phrase;

	public Greeting() {
	}

	public Greeting(String type, String phrase) {
		this.type = type;
		this.phrase = phrase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	@Override
	public String toString() {
		return "Greeting{" +
				"type='" + type + '\'' +
				", phrase='" + phrase + '\'' +
				'}';
	}
}
