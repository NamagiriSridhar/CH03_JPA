package tacos.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Taco 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date createdAt;
	@NotNull
	@Size(min=5, message="You must have a long taco name")
	private String name;
	
	@ManyToMany(targetEntity=Ingredient.class)
	@Size(min=1, message="You must choose at least 1 ingredient")
	private List <Ingredient> ingredients=new ArrayList<>();
//	private List <String> ingredients;
	
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
	
}