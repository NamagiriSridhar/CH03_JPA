package tacos.tests;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import tacos.controllers.DesignTacoController;
import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.OrderRepository;
import tacos.repository.TacoRepository;



@RunWith(SpringRunner.class)
@WebMvcTest(DesignTacoController.class)
public class DesignTacoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private List<Ingredient> ingredients;

  private Taco design;

  @MockBean
  private IngredientRepository ingredientRepository;

  @MockBean
  private TacoRepository designRepository;

  @MockBean
  private OrderRepository orderRepository;

  @Before
  public void setup() {
    ingredients = Arrays.asList(
      new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
      new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
      new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
      new Ingredient("CARN", "Carnitas", Type.PROTEIN),
      new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
      new Ingredient("CHED", "Cheddar", Type.CHEESE),
      new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
      new Ingredient("SLSA", "Salsa", Type.SAUCE),
      new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
    );

    when(ingredientRepository.findAll())
        .thenReturn(ingredients);

    when(ingredientRepository.findById("FLTO")).thenReturn(Optional.of(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP)));
    when(ingredientRepository.findById("GRBF")).thenReturn(Optional.of(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN)));
    when(ingredientRepository.findById("CHED")).thenReturn(Optional.of(new Ingredient("CHED", "Cheddar", Type.CHEESE)));

    design = new Taco();
    design.setName("Test Taco");

    design.setIngredients(Arrays.asList(
        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
        new Ingredient("CHED", "Cheddar", Type.CHEESE)
  ));

  }

  @Test
  public void testShowDesignForm() throws Exception {
    mockMvc.perform(get("/design"))
        .andExpect(status().isOk())
        .andExpect(view().name("designForm"))
        .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
        .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
        .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
        .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
        .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
  }

  @Test
  public void processDesign() throws Exception {
    when(designRepository.save(design))
        .thenReturn(design);
     System.out.println(design);
     //Taco(id=null, createdAt=null, name=Test Taco, ingredients=[Ingredient(id=FLTO, name=Flour Tortilla, type=WRAP),
     //Ingredient(id=GRBF, name=Ground Beef, type=PROTEIN), Ingredient(id=CHED, name=Cheddar, type=CHEESE)])

    mockMvc.perform(post("/design")
    		
        .content("name=Test+Taco&ingredients=FLTO,GRBF,CHED")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
      .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/orders/current"));
    
  }

}
 //mj.nuonhp98ny0ph;uyuobh juil bhjknm kl j