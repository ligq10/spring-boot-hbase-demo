package com.spring.boot.starter.hbase.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.boot.starter.hbase.dto.PeopleDto;
import com.spring.boot.starter.hbase.service.PeopleService;


@Controller
public class PeopleController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PeopleService peopleService;
	
	@RequestMapping(value = "/test", method = { RequestMethod.POST })
	public ResponseEntity<?> cp150Locations(
			HttpServletRequest request) {
		try {
			//peopleService.createTable("user", "name");
			//peopleService.saveRecords("blog");
			//peopleService.findOne("blog","");
			peopleService.findAll("");
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
