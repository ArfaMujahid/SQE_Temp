package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.io.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jtspringproject.JtSpringProject.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.services.userService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.cartService;



@Controller
public class UserController{

	@Autowired
	private userService userService;

	@Autowired
	private productService productService;

	@Autowired
	private cartService cartService;
	int userlogcheck = 0;
	String usernameforclass = "";
	int useridd = 0;
	@RequestMapping(value = {"/","/logout"})
	public String returnIndex() {
		userlogcheck =0;
		usernameforclass = "";
		useridd = -1;
		cartService = new cartService();
		return "userLogin";
	}
	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}

	@GetMapping("/buy")
	public String buy()
	{
		return "buy";
	}


	//	@RequestMapping(value = {"/","/logout"})
//	public String returnIndex() throws SQLException {
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava", "root", "tayyab3037");
//		String deleteProductCartQuery = "DELETE FROM Product_cart";
//
//		try (PreparedStatement deleteStmt = con.prepareStatement(deleteProductCartQuery)) {
//			// Execute the delete statement
//			int rowsAffected = deleteStmt.executeUpdate();
//		}
//		return "userLogin";
//	}
	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public ModelAndView userlogin( @RequestParam("username") String username, @RequestParam("password") String pass,Model model,HttpServletResponse res) {

		System.out.println(pass);
		User u = this.userService.checkLogin(username, pass);
		//System.out.println(u.getUsername());
		if(u != null && u.getUsername().equals(username)) {
			useridd = u.getId();
			usernameforclass = u.getUsername();
			res.addCookie(new Cookie("username", u.getUsername()));
			res.addCookie(new Cookie("cutomerid", String.valueOf(u.getId())));
			ModelAndView mView  = new ModelAndView("index");
			userlogcheck=1;
			mView.addObject("user", u);
			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;

		}else {
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("msg", "Please enter correct email and password");
			return mView;
		}

	}


	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if(products.isEmpty()) {
			mView.addObject("msg","No products are available");
		}else {
			mView.addObject("products",products);
		}

		return mView;
	}@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public ModelAndView newUseRegister(@ModelAttribute User user) {

		boolean exists = this.userService.checkUserExists(user.getUsername());

		if(!exists) {
			System.out.println(user.getEmail());
			user.setRole("ROLE_NORMAL");
			this.userService.addUser(user);

			System.out.println("New user created: " + user.getUsername());
			ModelAndView mView = new ModelAndView("userLogin");
			return mView;
		} else {
			System.out.println("New user not created - username taken: " + user.getUsername());
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("msg", user.getUsername() + " is taken. Please choose a different username.");
			return mView;
		}
	}


	@PostMapping("/deleteCartItem")
	public String deleteCartItem(@RequestParam("id") int cartItemId) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","Arfasara1624928");
			String deleteQuery = "DELETE FROM product_cart WHERE product_id = ?";
			PreparedStatement stmt = con.prepareStatement(deleteQuery);
			stmt.setInt(1, cartItemId);
			int rowsAffected = stmt.executeUpdate();
			System.out.println("Item deleted from cart. Cart Item ID: " + cartItemId);

			// Redirect back to the cartproduct page
			return "redirect:/cartproduct";
		} catch (Exception e) {
			// Handle exceptions (e.g., log the error)
			System.out.println("Error deleting item from cart: " + e.getMessage());
			return "redirect:/cartproduct"; // Redirect back to the cartproduct page in case of an error
		}
	}


	@GetMapping("/cart")
	public String viewCart(Model model) {
		// Fetch products by IDs from the cartProductIds list


		// Add the cartProducts to the model


		// Return the cartproduct.jsp page
		return "cartproduct";
	}


	////////////////
	@PostMapping("/cart/delete")
	public String deleteProductFromCart(@RequestParam("id") int productId) {
		User user = this.userService.getCurrentUser();
		Cart cart = cartService.getCart();

		Product productToRemove = null;
		for (Product product : cart.getProducts()) {
			if (product.getId() == productId) {
				productToRemove = product;
				break;
			}
		}

		if (productToRemove != null) {
			cart.removeProduct(productToRemove);
			cartService.updateCart(cart);
		}

		return "redirect:/cartproduct";
	}


	@PostMapping("/products/addtocart")
	public String addToCart(@RequestParam("id") int productId) {
		User user = this.userService.getCurrentUser();
		Cart cart = cartService.getCart();
		Product product = this.productService.getProduct(productId);
		boolean check = false;

		if(cart == null){
			cart = cartService.createCart();
			check = true;
		}
		if (cart.getCustomer() == null) {
			cart.setCustomer(user);
		}

		cart.addProduct(product);

		if (check == true) {
			cartService.addCart(cart);
			check = false;
		} else {
			cartService.updateCart(cart);
		}

		return "redirect:/cartproduct";
	}


	@GetMapping("/cartproduct")
	public ModelAndView  getCartDetail()
	{
		ModelAndView mv= new ModelAndView();
		List<Cart>carts = cartService.getCarts();
		return mv;
	}
	/////////////////////



	@GetMapping("updateProfile")
	public String redirectToUpdateProfile() {
		return "updateProfile";
	}

	@GetMapping("/profileDisplay")
	public String profileDisplay(Model model) {
		String displayusername, displaypassword, displayemail, displayaddress;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava", "root", "Arfasara1624928");
			PreparedStatement stmt = con.prepareStatement("select * from CUSTOMER where username = ?");
			stmt.setString(1, usernameforclass);
			ResultSet rst = stmt.executeQuery();
			if (rst.next()) {
				int userid = rst.getInt("id");
				displayusername = rst.getString("username");
				displayemail = rst.getString("email");
				displaypassword = rst.getString("password");
				displayaddress = rst.getString("address");
				model.addAttribute("userid", userid);
				model.addAttribute("username", displayusername);
				model.addAttribute("email", displayemail);
				model.addAttribute("password", displaypassword);
				model.addAttribute("address", displayaddress);
			}
			return "displayProfile";
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		System.out.println("Hello");
		return "displayProfile";
	}


	@RequestMapping(value = "updateuser",method=RequestMethod.POST)
	public String updateUserProfile(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","Arfasara1624928");
			PreparedStatement pst = con.prepareStatement("update CUSTOMER set username= ?,email = ?,password= ?, address= ? where id = ?;");
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, address);
			pst.setInt(5, useridd);
			int i = pst.executeUpdate();
			usernameforclass = username;
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/profileDisplay";
	}



}
