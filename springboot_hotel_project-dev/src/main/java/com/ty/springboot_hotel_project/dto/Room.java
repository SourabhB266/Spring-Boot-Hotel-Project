package com.ty.springboot_hotel_project.dto;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Room {
	@Id
	@GenericGenerator(name = "room_id",strategy = "com.ty.springboot_hotel_project.util.RoomIdGenerator")
	@GeneratedValue(generator = "room_id")
	private String room_id;
	@NotBlank(message = "room no should not blank")
	@NotNull(message = "room no should not be null")
	private String room_no;
	@NotBlank(message = "roomtype should not blank")
	@NotNull(message = "roomtype should not be null")
	private String room_type;
	@NotBlank(message = "roomprice should not blank")
	@NotNull(message = "roomprice should not be null")
	private String room_price;
	@NotBlank(message = "no of beds should not blank")
	@NotNull(message = "no of beds should not be null")
	private String no_of_beds;
	@NotBlank(message = "max no of people should not blank")
	@NotNull(message = "max no of people should not be null")
	private String max_no_of_people;
	@NotBlank(message = "availibility should not blank")
	@NotNull(message = "availablility should not be null")
	@Pattern(regexp = "Y/N")
	private String availability;
	
	@Enumerated(EnumType.STRING)
	private Floor floor;

	@ManyToOne
	private Hotel hotel;
	
	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getId() {
		return room_id;
	}

	public void setId(String id) {
		this.room_id = id;
	}

	public String getRoom_no() {
		return room_no;
	}

	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}

	public String getRoom_type() {
		return room_type;
	}

	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}

	public String getRoom_price() {
		return room_price;
	}

	public void setRoom_price(String room_price) {
		this.room_price = room_price;
	}

	public String getNo_of_beds() {
		return no_of_beds;
	}

	public void setNo_of_beds(String no_of_beds) {
		this.no_of_beds = no_of_beds;
	}

	public String getMax_no_of_people() {
		return max_no_of_people;
	}

	public void setMax_no_of_people(String max_no_of_people) {
		this.max_no_of_people = max_no_of_people;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
}
