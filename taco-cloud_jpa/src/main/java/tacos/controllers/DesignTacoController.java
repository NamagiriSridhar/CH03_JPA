package tacos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import tacos.domain.Ingredient;
import tacos.domain.Ingredient.Type;
import tacos.domain.Order;
import tacos.domain.Taco;
import tacos.repository.IngredientRepository;
import tacos.repository.TacoRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController 
{  
	private final IngredientRepository ingredientRepo;
	private TacoRepository tacoRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo,TacoRepository tacoRepo)
	{
		this.ingredientRepo = ingredientRepo;
		this.tacoRepo = tacoRepo;
	}
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
	    ingredientRepo.findAll().forEach(i -> ingredients.add(i));

	    Type[] types = Ingredient.Type.values();
	    for (Type type : types) {
	      model.addAttribute(type.toString().toLowerCase(),
	          filterByType(ingredients, type));
	    }
	
	}

	
	@ModelAttribute(name="order")
	public Order order()
	{
		return new Order();
	}
	
	@ModelAttribute(name="taco")
	public Taco taco()
	{
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm(Model model)
	{
		 
		return "designForm";
	}
	
	
	 private List<Ingredient> filterByType(
		      List<Ingredient> ingredients, Type type) 
	 {
		    return ingredients
		              .stream()
		              .filter(x -> x.getType().equals(type))
		              .collect(Collectors.toList());
	 }
	 
	/* private Taco changeBackToIngredient(Taco taco){
		 List<String> ingred = taco.getIngredients();
		 List<Ingredient> ingredients = new ArrayList<>();
			ingredientRepo.findAll().forEach(i -> ingredients.add(i));
			List<Ingredient> newIngredients = new ArrayList<>();
		 for(int i = 0; i < ingred.size(); i++) {
			 for(int y = 0; y < ingredients.size(); y ++) {
				 if(ingred.get(i) == ingredients.get(y).getName()) {
					 newIngredients.add(ingredients.get(y));
				 }
			 }
			 
		 }
		 		 
		 taco.setIngredientss(newIngredients);
		 return taco;
	 }*/
	 
	 @PostMapping
	 public String processDesign(@Valid Taco taco, Errors errors, Order order) 
	 {
		 if(errors.hasErrors()) {
			 return "designForm";
		 }
		log.info("Processing design: " + taco);//@Slf4j for log red squiggly.
	    tacoRepo.save(taco);
	   // System.out.println("Before add "+order.getTacos());
	    order.addTaco(taco);
	  //  System.out.println("After add "+order.getTacos());	   
	   return "redirect:/orders/current";
    }
}
