package com.ty.springboot_hotel_project.service;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.springboot_hotel_project.dao.BookingDao;
import com.ty.springboot_hotel_project.dao.CustomerDao;
import com.ty.springboot_hotel_project.dao.RoomDao;
import com.ty.springboot_hotel_project.dto.Booking;
import com.ty.springboot_hotel_project.dto.Customer;
import com.ty.springboot_hotel_project.dto.Room;
import com.ty.springboot_hotel_project.exception.BookingBodyNotFoundException;
import com.ty.springboot_hotel_project.exception.BookingIdNotFoundException;
import com.ty.springboot_hotel_project.repository.BookingsRepository;
import com.ty.springboot_hotel_project.util.ResponseStructure;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Autowired
	private RoomDao roomDao;
	@Autowired
	private BookingsRepository Repository;
	@Autowired
	private CustomerDao customerDao;

	ResponseStructure<Booking> structure = new ResponseStructure<>();

	public ResponseEntity<ResponseStructure<Booking>> saveBooking(Booking booking, int cid, String rid) {
		Room room = roomDao.getRoomById(rid);
		room.setAvailability("N");
		Room room2=roomDao.updatRoom(room);
		Customer customer = customerDao.getCustomerById(cid);
		booking.setCustomer(customer);
		booking.setRooms(room2);
		Booking booking2 = bookingDao.saveBooking(booking);
		if (booking2 != null) {
			structure.setMessage("booking Saved Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(booking2);
		}
		return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Booking>> updateBooking(int bid, Booking booking) {

		if (bookingDao.getBookingById(bid) != null) {
			Booking booking2 = bookingDao.getBookingById(bid);
			booking.setCustomer(booking2.getCustomer());
			booking.setRooms(booking2.getRooms());
			booking.setId(bid);
			structure.setMessage("booking Updated Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(bookingDao.updatBooking(booking2));
			return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);
		} else {
			throw new BookingIdNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(int bid) {
		Booking booking2 = bookingDao.getBookingById(bid);
		if (booking2 != null) {
			structure.setMessage("booking deleted Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(bookingDao.deleteBooking(booking2));
			return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);
		} else {
			throw new BookingIdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<Booking>> getBookingById(int bid) {
		Booking booking2 = bookingDao.getBookingById(bid);
		if (booking2 != null) {
			structure.setMessage("booking fetched Successufully....");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData(booking2);
			return new ResponseEntity<ResponseStructure<Booking>>(structure, HttpStatus.CREATED);
		} else {
			throw new BookingIdNotFoundException();
		}
	}

	public ResponseEntity<ResponseStructure<List<Booking>>> getBookings() {
		ResponseStructure<List<Booking>> responseStructure = new ResponseStructure<>();
		if (bookingDao.getAllBookings() != null) {
			responseStructure.setMessage("bookings feted Successufully....");
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setData(bookingDao.getAllBookings());
			return new ResponseEntity<ResponseStructure<List<Booking>>>(responseStructure, HttpStatus.CREATED);
		} else {
			throw new BookingBodyNotFoundException();

		}
	}

	public ResponseEntity<ResponseStructure<List<Room>>> getRoomsByCheckOutAndCheckIn(String in, String out)
			throws ParseException {
		List<Room> sendList = new ArrayList<>();
		List<Room> list = roomDao.getRoomByAvailability("Y");
		for (Room r : list) {
			sendList.add(r);
		}
		List<Booking> list2 = bookingDao.getAllBookings();
		for (Booking b : list2) {
			String from = b.getCheck_in_date();
			String to = b.getCheck_out_date();

			Date d_from = new SimpleDateFormat("yyyy-MM-dd").parse(from);
			Date d_to = new SimpleDateFormat("yyyy-MM-dd").parse(to);

			long t1 = d_from.getTime();
			long t2 = d_to.getTime();

			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");

			if (t1 < t2) {

				int count = 0;
				for (long i = t1; i <= t2; i += 86400000) {
					if ((f.format(i)).equals(in) || (f.format(i)).equals(out))
						count++;
				}
				if (count == 0) {
					sendList.add(b.getRooms());
				}
			}
		}

		ResponseStructure<List<Room>> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Rooms feted Successufully....");
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setData(sendList);
		return new ResponseEntity<ResponseStructure<List<Room>>>(responseStructure, HttpStatus.OK);

	}

}
