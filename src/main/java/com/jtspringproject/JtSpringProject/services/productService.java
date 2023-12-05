package com.jtspringproject.JtSpringProject.services;

import java.util.List;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtspringproject.JtSpringProject.dao.productDao;
import com.jtspringproject.JtSpringProject.models.Product;

@Service
public class productService {
	@Autowired
	private productDao productDao;
	
	public List<Product> getProducts() {
		List<Product> productList = this.productDao.getProducts();
		productList.sort(Comparator.comparing(Product::getPrice));
		return productList;
	}
	
	public Product addProduct(Product product) {
		return this.productDao.addProduct(product);
	}
	
	public Product getProduct(int id) {
		return this.productDao.getProduct(id);
	}

	public Product updateProduct(int id,Product product){
		product.setId(id);
		return this.productDao.updateProduct(product);
	}
	public boolean deleteProduct(int id) {
		return this.productDao.deletProduct(id);
	}

	
}
