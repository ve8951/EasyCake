package com.android.easycake.models;

public class MODELShoppingCartProduct {
	MODELProduct modelProducts;
	String quantity;
	
	
	
	public MODELShoppingCartProduct(MODELProduct modelProducts, String quantity) {
		super();
		this.modelProducts = modelProducts;
		this.quantity = quantity;
	}
	
	public MODELProduct getModelProducts() {
		return modelProducts;
	}
	public void setModelProducts(MODELProduct modelProducts) {
		this.modelProducts = modelProducts;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	
	

}
