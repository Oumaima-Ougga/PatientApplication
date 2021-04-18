package com.example.demo.entities;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import lombok.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Patient {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min =2,max=15)
	private String name;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNaissance;
	
	@DecimalMin("3")
	private int score;
	private boolean malade;
	
}
