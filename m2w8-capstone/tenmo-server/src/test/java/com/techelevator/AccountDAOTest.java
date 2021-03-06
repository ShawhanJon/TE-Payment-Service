package com.techelevator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.dao.AccountDAO;

public class AccountDAOTest extends DAOIntegrationTest{
	

	private AccountDAO dao;
	private JdbcTemplate jdbcTemplate;
	
	
	@BeforeAll
	public void setup() {
		
			dao = new AccountDAO(getDataSource());
			jdbcTemplate = new JdbcTemplate(getDataSource());
		}
	
	@Test
	public void test_get_account() {
		
	}

//	@Test
//	public void test_get_all_venues() {
//		
//		String sql = "SELECT COUNT(*) AS numberOfRows FROM venue";
//		SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);
//		rows.next();
//		int originalRowCount = rows.getInt("numberOfRows");
//
//		Venue venue = getNewVenue();
//		
//		dao.save(venue);
//
//		//public List<Venue> getAllVenues()
//		
//		List<Venue> venuesReturned = dao.getAllVenues();
//		
//		Assert.assertNotNull(venuesReturned);
//		Assert.assertEquals(originalRowCount + 1, venuesReturned.size());
//	}
//	
//
//
//	
//
//	
//	@Test
//	public void tests_find_venue_by_id() throws SQLException {
//		Venue theVenue = getNewVenue();
//
//		dao.save(theVenue);
//		Venue savedVenue = dao.selectVenueById(theVenue.getId());
//		
//		//public List<Venue> getAllVenues()
//
//		assertNotEquals(null, theVenue.getId());
//		assertVenuesAreEqual(theVenue, savedVenue);
//	}
//
//
//	//public Venue selectVenueById(int id)
//	
//	private Venue getNewVenue() {
//		Venue venue = new Venue();
//		venue.setId(1);
//		venue.setName("TestName");
//		venue.setCity_id(1);
//		venue.setDescription("Test description");
//		
//		return venue;
//	}
//	
//	
//	/*
//	 * private Venue getNewVenue(int id, String name, int city_id, String
//	 * description) { Venue theVenue = new Venue(); theVenue.setId(id);
//	 * theVenue.setName(name); theVenue.setCity_id(city_id);
//	 * theVenue.setDescription(description); return theVenue; }
//	 */
//	
//	private void assertVenuesAreEqual(Venue expected, Venue actual) {
//		assertEquals(expected.getId(), actual.getId());
//		assertEquals(expected.getName(), actual.getName());
//		assertEquals(expected.getCity_id(), actual.getCity_id());
//		assertEquals(expected.getDescription(), actual.getDescription());
//		
//	}
//
//}