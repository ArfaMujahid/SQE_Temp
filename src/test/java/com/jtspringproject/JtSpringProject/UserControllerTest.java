package com.jtspringproject.JtSpringProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.cartService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.userService;

import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private cartService cartService;

    @MockBean
    private productService productService;

    @Autowired
    private UserController userController;

    @MockBean
    private userService userService;


    /**
 {@link UserController#registerUser()}
     */
    @Test
    void testRegisterUser() {

        assertEquals("register", (new UserController()).registerUser());
    }

    /**
    {@link UserController#buy()}
     */
    @Test
    void testBuy() {

        assertEquals("buy", (new UserController()).buy());
    }

    /**
      {@link UserController#getproduct()}
     */
    @Test
    void testGetproduct() throws Exception {
        when(productService.getProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/products");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg"))
                .andExpect(MockMvcResultMatchers.view().name("uproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("uproduct"));
    }

    /**
     {@link UserController#getproduct()}
     */
    @Test
    void testGetproduct2() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("?");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("?");
        product.setName("?");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getProducts()).thenReturn(productList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/products");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("uproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("uproduct"));
    }

    /**
      {@link UserController#newUseRegister(User)}
     */
    @Test
    void testNewUseRegister() throws Exception {
        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkUserExists(Mockito.<String>any())).thenReturn(true);
        when(userService.addUser(Mockito.<User>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/newuserregister");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg", "user"))
                .andExpect(MockMvcResultMatchers.view().name("register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("register"));
    }

    /**
      {@link UserController#newUseRegister(User)}
     */
    @Test
    void testNewUseRegister2() throws Exception {
        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkUserExists(Mockito.<String>any())).thenReturn(false);
        when(userService.addUser(Mockito.<User>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/newuserregister");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     {@link UserController#returnIndex()}
     */
    @Test
    void testReturnIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     {@link UserController#returnIndex()}
     */
    @Test
    void testReturnIndex2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     * {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin() throws Exception {
        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg"))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin2() throws Exception {
        when(productService.getProducts()).thenReturn(new ArrayList<>());

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("foo");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
        ResultActions resultActions2 = resultActions.andExpect(MockMvcResultMatchers.cookie().value("username", "foo"));
        ResultActions resultActions3 = resultActions2.andExpect(MockMvcResultMatchers.cookie().secure("username", false));
        ResultActions resultActions4 = resultActions3.andExpect(MockMvcResultMatchers.cookie().httpOnly("username", false));
        resultActions4.andExpect(MockMvcResultMatchers.cookie().maxAge("username", -1))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin3() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("?");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("?");
        product.setName("?");
        product.setPrice(2);
        product.setQuantity(2);
        product.setWeight(3);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getProducts()).thenReturn(productList);

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("foo");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
        ResultActions resultActions2 = resultActions.andExpect(MockMvcResultMatchers.cookie().value("username", "foo"));
        ResultActions resultActions3 = resultActions2.andExpect(MockMvcResultMatchers.cookie().secure("username", false));
        ResultActions resultActions4 = resultActions3.andExpect(MockMvcResultMatchers.cookie().httpOnly("username", false));
        resultActions4.andExpect(MockMvcResultMatchers.cookie().maxAge("username", -1))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    @Test
    void testViewCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }

    @Test
    void testViewCart2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }
}
