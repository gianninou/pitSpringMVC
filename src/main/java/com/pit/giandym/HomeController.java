package com.pit.giandym;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller

public class HomeController implements HandlerExceptionResolver{

	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/script", method = RequestMethod.GET)
	public String selectScript(HttpServletRequest request,Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		request.getSession().setAttribute("userFile", new UserFileModel());
		request.getSession().setAttribute("listeFichier", null);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		List<String> liste= new ArrayList<String>();
		liste.add("Site1");
		liste.add("Site2");
		liste.add("Site3");
		liste.add("Site4");

		List<String> script= new ArrayList<String>();
		script.add("Lineintersect");
		script.add("Script2");
		script.add("Script3");
		script.add("Script4");

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("listeSite",liste);
		model.addAttribute("listeScript",script);

		return "script/choixScript";
	}


	@RequestMapping(value = "/script/choixScript", method = RequestMethod.POST)
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
		
		request.getSession().setAttribute("userFile", new UserFileModel(request.getParameter("script"),request.getParameter("site")));
		
		

		List<String> entete= new ArrayList<String>();
		entete.add("Entete1");
		entete.add("Entete2");
		entete.add("Entete3");
		entete.add("Entete4");
		request.setAttribute("listeEntete", entete);

		return "script/envoieFichier";
	}


	@RequestMapping(value = "/script/envoieFichier", method = RequestMethod.POST)
    public String envoieFichier(HttpServletRequest request, @ModelAttribute(value="FORM") UserFileModel script,BindingResult result){
		if(!result.hasErrors()){
            FileOutputStream outputStream = null;
            UserFileModel ufm = (UserFileModel)request.getSession().getAttribute("userFile");
            ufm.setFile(script.getFile());
            ufm.setName(script.getFile().getOriginalFilename());
            
            ufm.setName(ufm.getName().substring(0, ufm.getName().lastIndexOf('.')));
     

            
            File f = new File (ufm.UserDataDir +"/"+ ufm.getUnique());
            f.mkdir();
            System.out.println("creation du dossier : "+ufm.UserDataDir + ufm.getUnique());
            String filePath = ufm.UserDataDir + ufm.getUnique() +"/"+ ufm.getFile().getOriginalFilename();
            //String filePath = System.getProperty("java.io.tmpdir") + "/" + script.getFile().getOriginalFilename();
            System.out.println("path : "+filePath);

            try {
                outputStream = new FileOutputStream(new File(filePath));
                outputStream.write(ufm.getFile().getFileItem().get());
                outputStream.close();
                System.out.println("Enregistrement du fichier");
                Map<String,ArrayList<String>> map = ufm.execute();
                if(map==null){
                	System.out.println("script/erreurScript");
                	return "script/erreurScript";
                }
                request.getSession().setAttribute("listeFichier", map);
                
            } catch (Exception e) {
                System.out.println("Error while saving file");
                e.printStackTrace();
                return "FileUploadForm";
            }
            
            return "script/execution";
        }else{
        	System.out.println("Erreur HomeControleur at envoieFichier");
            return "FileUploadForm";
        }
    }
	
	
	@RequestMapping(value = "/script/execution", method = RequestMethod.POST)
	public String resultScript(HttpServletRequest request,HttpServletResponse reponse){
		request.setAttribute("listeFichier", request.getSession().getAttribute("listeFichier"));
		
		return null;
	}
	
	@RequestMapping(value = "/script/getFile", method = RequestMethod.GET)
	public String getFile(HttpServletRequest request,HttpServletResponse reponse){
		System.out.println(request.getParameter("id"));
		@SuppressWarnings("unchecked")
		ArrayList<String> path = ((Map<String,ArrayList<String>>)request.getSession().getAttribute("listeFichier")).get(request.getParameter("id"));
		for(int i=0;i<path.size();i++){
			System.out.println("test : "+path.get(i));
		}
		System.out.println("path : "+path.get(0));
		
		String name = path.get(1).substring(0, path.get(1).lastIndexOf('.'));
		System.out.println("name : "+name);
		
		String extention = path.get(1).substring(path.get(1).lastIndexOf('.')+1,path.get(1).length()); 
		System.out.println("Extention : "+extention);
		// Voir la pour plus d extention
		// http://www.coderanch.com/t/422345/Servlets/java/response-setContentType-working-case-Excel
		if(extention.equals("csv")){
			reponse.setContentType("APPLICATION/OCTET-STREAM");  
            reponse.setHeader("Content-Disposition", "attachment; filename="+name+".csv"); 
		}else if(extention.equals("pdf")){
			reponse.setContentType("application/pdf");  
            reponse.setHeader("Content-Disposition", "attachment; filename="+name+".pdf");  
		}
		
		try {
			InputStream is = new FileInputStream(path.get(0));
			IOUtils.copy(is, reponse.getOutputStream());
			reponse.getOutputStream().flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	


	@Override
    public ModelAndView resolveException(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception exception) {
        Map<Object, Object> model = new HashMap<Object, Object>();
        if (exception instanceof MaxUploadSizeExceededException){
            model.put("errors", "File size should be less then "+
            ((MaxUploadSizeExceededException)exception).getMaxUploadSize()+" byte.");
        } else{
            model.put("errors", "Unexpected error: " + exception.getMessage());
        }
        model.put("FORM", new UserFileModel());
        return new ModelAndView("/FileUploadForm", ((Map) model));
    }

}
