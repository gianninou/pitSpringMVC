package com.pit.giandym;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserFileModel {

	public final String ScriptDir = "/home/giann/tmp/script";
	public final String UserDataDir = "/home/giann/tmp/userData/";
	public final String UserDataDirDel = "/home/giann/tmp/userDataDel/";
	


	private String name;
	private CommonsMultipartFile file;

	private String script;
	private String site;
	private String unique;
	private Boolean conserver;


	

	public UserFileModel(){ 
		conserver=false;
	}

	public UserFileModel(String script,String site){
		this.script=script;
		this.site=site;
		conserver=false;

		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		unique = dateFormat.format(actuelle)+"_"+this.site+"_"+this.script+"_"+((int)(Math.random()*1000));

	}



	public Map<String,ArrayList<String>> execute(){
		Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		
		String dir = (conserver?UserDataDir:UserDataDirDel)+unique;
		String outputDir = dir+"/output";
		
		File f = new File(outputDir);
		f.mkdir();

		if(unique==null){
			Date actuelle = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			unique = dateFormat.format(actuelle)+"_"+this.site+"_"+this.script+"_"+((int)(Math.random()*1000));
		}

		try {
			System.out.println("Execution : ...");
			String cmd = "\tRscript " + ScriptDir +"/"+ ScriptModel.scriptSelected(Integer.parseInt(script))[0] +" "+ (dir) +" "+ name +" "+ outputDir +" "+ name;
			System.out.println(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			if(p.waitFor()!=0){
				System.out.println("Erreur lors de l'execution de script");
				return null;
			}
			System.out.println(" ..... fin");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		int filecount=0;
		File file = new File(outputDir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					ArrayList<String> l = new ArrayList<String>();
					l.add(files[i].getAbsolutePath());
					l.add(files[i].getName());
					map.put(""+i,l);
					System.out.println("Fichier créé : " + files[i].getAbsolutePath());
					filecount++;
				}                
			}
			System.out.println("Nombre de fichier créé : "+filecount);
		}
		return map;
	}



	public String getPath(){
		
		return (conserver?UserDataDir:UserDataDirDel)+unique+"/";
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
		this.name = file.getOriginalFilename();
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	
	public Boolean getConserver() {
		return conserver;
	}

	public void setConserver(Boolean conserver) {
		this.conserver = conserver;
	}
	
	

}
