package tacos.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import tacos.repository.IngredientRepository;
import tacos.repository.OrderRepository;
import tacos.repository.TacoRepository;

@RunWith(SpringRunner.class)
@WebMvcTest   // <1>
public class testHomePage {

  @Autowired
  private MockMvc mockMvc;   // <2>
  
  @MockBean
  private IngredientRepository ingredientRepository;//wont run successfully without these mock beans

  @MockBean
  private TacoRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Test
  public void testHomePg() throws Exception {
    mockMvc.perform(get("/"))    // <3>
    
      .andExpect(status().isOk())  // <4>
      
      .andExpect(view().name("home"))  // <5>
      
      .andExpect(content().string(           // <6>
          containsString("Welcome to...")));  
  }

}