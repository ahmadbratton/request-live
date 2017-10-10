package com.example.demo;

import com.example.demo.model.Show;
import com.example.demo.repository.ShowRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	ShowRepository showRepo;

	@Test
	public void createShow() {
		Show show = new Show("Eddies Attic", "22 Willow Street");
		showRepo.save(show);

		Iterable<Show> getAllShows = showRepo.findAll();
		ArrayList<Show> allShows = new ArrayList<>();
		for(Show currentShow : getAllShows) {
			allShows.add(currentShow);
		}
		Show show1 = findInList(allShows, "Eddies Attic", "22 Willow Street");
		Assert.assertNotNull(show1);
	}

	public static Show findInList(List<Show> shows, String locationName, String locationAddress) {
		boolean found = false;
		for (Show show : shows) {
			if (show.getLocationName().equals(locationName) && show.getLocationAddress().equals(locationAddress)) {
				return show;
			}
		}
		return null;
	}
}
