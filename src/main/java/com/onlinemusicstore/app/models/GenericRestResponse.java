package com.onlinemusicstore.app.models;

import java.util.ArrayList;
import java.util.Arrays;

public class GenericRestResponse {

	private Object data;
	private Object[] errors;
	private String message;
	private long resultsCount;
	private Object filteredCategories;

	public GenericRestResponse() {
		data = new Object();
		errors = new Object[0];
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object[] getError() {
		return errors;
	}

	public void setError(Object[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(Object filteredCategories) {
		this.filteredCategories = filteredCategories;
	}

	public long getResultsCount() {
		return resultsCount;
	}

	public void setResultsCount(long resultsCount) {
		this.resultsCount = resultsCount;
	}

	public Object[] appendValue(Object[] obj, Object newObj) {

		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		temp.add(newObj);
		return temp.toArray();

	}

}