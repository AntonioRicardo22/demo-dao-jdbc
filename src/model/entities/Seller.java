package model.entities;


import java.io.Serializable;
import java.time.LocalDate;


import java.util.Objects;

public class Seller implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String email;
	private LocalDate birthLocalDate;
	private Double baseSalary;
	private Department department;
	
	public Seller() {
	
	}
	
	public Seller( String name, String email, LocalDate birthLocalDate, Double baseSalary,Department department) {
		this.name = name;
		this.email = email;
		this.birthLocalDate = birthLocalDate;
		this.baseSalary = baseSalary;
		this.department = department;
	}

	public Seller(Integer id, String name, String email, LocalDate birthLocalDate, Double baseSalary,Department department) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthLocalDate = birthLocalDate;
		this.baseSalary = baseSalary;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthLocalDate() {
		return birthLocalDate;
	}

	public void setBirthLocalDate(LocalDate birthLocalDate) {
		this.birthLocalDate = birthLocalDate;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Seller: " + id + ", name: " + name + ", email: " + email + ", birthLocalDate: " + birthLocalDate + ", baseSalary: "
				+ baseSalary + ",  " + department ;
	}
}
