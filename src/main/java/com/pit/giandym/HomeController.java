package com.pit.giandym;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController {

	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

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


	@RequestMapping(value = "choixScript", method = RequestMethod.POST)
	public String selectScriptSite(HttpServletRequest request,HttpServletResponse reponse){

		System.out.println("script : "+request.getParameter("script")+", Site : "+request.getParameter("site"));

		if(request.getParameter("script")!=null){
			request.getSession().setAttribute("script", request.getParameter("script"));
		}else{
			return null;
		}
		
		if(request.getParameter("site")!=null){
			request.getSession().setAttribute("site", request.getParameter("site"));
		}else{
			return null;
		}
		
		List<String> entete= new ArrayList<String>();
		entete.add("Script1");
		entete.add("Script2");
		entete.add("Script3");
		entete.add("Script4");
		request.setAttribute("listeEntete", entete);
		
		return "envoieFichier";
	}





	@RequestMapping(value = "envoieFichier", method = RequestMethod.POST)
	public String envoieFichier(HttpServletRequest request,Object command,@RequestParam("file") MultipartFile file) throws IOException {

		//MultipartHttpServletRequest mres = new DefaultMultipartHttpServletRequest(request);
		//MultipartFile multipartFile = mres.getFile("file");
		 
        
        System.out.println(file.getBytes());
		
		
		/* Recuperation du fichier a faire */
		/*System.out.println("site : "+request.getParameter("file"));
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		while((line = br.readLine()) != null) {
			System.out.println("apres : "+line);
		}
		/* Fin de recuperation */

		return "execution";

	}

}
