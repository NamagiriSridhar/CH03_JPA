package tacos.controllers;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.domain.Order;
import tacos.repository.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController
{
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo)
	{
		this.orderRepo = orderRepo;
	}
	@ModelAttribute(name="order")
	public Order order()
	{
		return new Order();
	}
	@GetMapping("/current")
	public String showOrderForm ()
	{
		//model.addAttribute("order", new Order());
		return "orderForm";
	}
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus)
	{
		if(errors.hasErrors())
		{
			return "orderForm";
		}
		orderRepo.save(order);
		sessionStatus.setComplete();
		log.info("Order submitted: " + order);
		return "redirect:/";
	}	
}
