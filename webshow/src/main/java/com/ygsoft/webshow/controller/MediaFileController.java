package com.ygsoft.webshow.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ygsoft.SysConf;

@RestController
public class MediaFileController {
	private static Logger log = Logger.getLogger(MediaFileController.class);
		
	public MediaFileController() {
		;
	}
	
	@RequestMapping(value = "/files/{file_name}/dn", 
			produces = {"image/jpeg", "image/gif", "image/png"}, method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("file_name") String fileName) {
		log.info("Resource File Requested :" + fileName);
		
		fileName = SysConf.IMG_DIR + fileName ;
		File imgFile = new File(fileName);
		
	    return new FileSystemResource(imgFile); 
	}
	
}
