package com.ty.springboot_hotel_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.springboot_hotel_project.dao.HotelDao;
import com.ty.springboot_hotel_project.dao.RoomDao;
import com.ty.springboot_hotel_project.dto.Hotel;
import com.ty.springboot_hotel_project.dto.Room;
import com.ty.springboot_hotel_project.exception.CustomerIdNotFoundException;
import com.ty.springboot_hotel_project.exception.RoomAvailabilityNotFoundException;
import com.ty.springboot_hotel_project.exception.RoomBodyNotFoundException;
import com.ty.springboot_hotel_project.exception.RoomIdNotFoundException;
import com.ty.springboot_hotel_project.exception.RoomNoNotFoundException;
import com.ty.springboot_hotel_project.exception.RoomTypeNotFoundException;
import com.ty.springboot_hotel_project.util.ResponseStructure;

@Service
public class RoomService {

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private HotelDao hotelDao;

	ResponseStructure<Room> structure = new ResponseStructure<>();

	public ResponseEntity<ResponseStructure<Room>> saveRoom(Room room, int hid) {
		Hotel hotel = hotelDao.getHotelById(hid);
		room.setHotel(hotel);
		Room room2 = roomDao.saveRoom(room);
		if (room2 != null) {
			structure.setMessage("Room Saved Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(room2);
		}
		return new ResponseEntity<ResponseStructure<Room>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Room>> updateRoom(String rid, Room room) {
		Room room2 = roomDao.getRoomById(rid);
		if (room2 != null) {
			room.setId(rid);
			room.setHotel(room2.getHotel());
			structure.setMessage("Room Updated Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(roomDao.updatRoom(room));
			return new ResponseEntity<ResponseStructure<Room>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomIdNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<Room>> deleteRoom(String rid) {
		Room room2 = roomDao.getRoomById(rid);
		if (room2 != null) {
			structure.setMessage("Room deleted Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(roomDao.deleteRoom(room2));
			return new ResponseEntity<ResponseStructure<Room>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomIdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<Room>> getRoomById(String rid) {
		Room room2 = roomDao.getRoomById(rid);
		if (room2 != null) {
			structure.setMessage("Room fetched Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(room2);
			return new ResponseEntity<ResponseStructure<Room>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomIdNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<List<Room>>> getRoomByType(String room_type) {
		List<Room> room2 = roomDao.getRoomByType(room_type);
		if (room2 != null) {
			ResponseStructure<List<Room>> structure = new ResponseStructure<>();
			structure.setMessage("Room fetched Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(room2);
			return new ResponseEntity<ResponseStructure<List<Room>>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomTypeNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<Room>> getRoomByNo(String room_no) {
		Room room2 = roomDao.getRoomByNo(room_no);
		if (room2 != null) {
			structure.setMessage("room fetched Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(room2);
			return new ResponseEntity<ResponseStructure<Room>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomNoNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<List<Room>>> getRoomByAvailability(String availability) {
		List<Room> room2 = roomDao.getRoomByAvailability(availability);
		if (room2 != null) {
			ResponseStructure<List<Room>> structure = new ResponseStructure<>();

			structure.setMessage("Room fetched Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(room2);
			return new ResponseEntity<ResponseStructure<List<Room>>>(structure, HttpStatus.CREATED);
		} else {
			throw new RoomAvailabilityNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<List<Room>>> getRooms() {
		ResponseStructure<List<Room>> responseStructure = new ResponseStructure<>();
		if (roomDao.getAllRooms() != null) {
			responseStructure.setMessage("Rooms feted Successufully....");
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setData(roomDao.getAllRooms());
			return new ResponseEntity<ResponseStructure<List<Room>>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new RoomBodyNotFoundException();

		}
	}

}
