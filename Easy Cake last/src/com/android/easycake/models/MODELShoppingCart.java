package com.android.easycake.models;

import java.util.List;

public class MODELShoppingCart {
	
	
	String userId;
	List<MODELCartProduct> modelCartProductsList;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public List<MODELCartProduct> getModelCartProductsList() {
		return modelCartProductsList;
	}
	public void setModelCartProductsList(
			List<MODELCartProduct> modelCartProductsList) {
		this.modelCartProductsList = modelCartProductsList;
	}
	

}
