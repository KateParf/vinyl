package com.example.vinyl;


import com.example.vinyl.service.SearchService;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.Group;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class SearchTests {

	@Autowired
    private SearchService searchService;
	

	@Test
	public void testSearchRecord() {

		List<RecordBrief> recs = this.searchService.searchByBarcode("600753511060");
		assertNotNull(recs); 
	}

}
