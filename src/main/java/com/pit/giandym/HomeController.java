package com.pit.giandym;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String selectScript(Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		List<String> liste= new ArrayList<String>();
		liste.add("Site1");
		liste.add("Site2");
		liste.add("Site3");
		liste.add("Site4");
		
		List<String> script= new ArrayList<String>();
		script.add("Script1");
		script.add("Script2");
		script.add("Script3");
		script.add("Script4");
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("listeSite",liste);
		model.addAttribute("listeScript",script);
		
		return "choixScript";
	}
	
	
	@RequestMapping(value = "envoieFichier", method = RequestMethod.POST)
	public String envoieFichier(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		        MultipartFile multipartFile = multipartRequest.getFile("file");
		        
		        System.out.println(multipartFile.getBytes());
		        return null;
		
	}
	
	
	
	/*@RequestMapping(value = "envoieFichier", method = RequestMethod.POST)
	public String envoieFichier(HttpServletRequest request) throws IOException {
		
		System.out.println("site : "+request.getParameter("file"));
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while((line = br.readLine()) != null) {
		    System.out.println(line);
		}
		return "envoieFichier";
		
	}*/
	
}
