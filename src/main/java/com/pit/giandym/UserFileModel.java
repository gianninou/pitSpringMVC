package com.pit.giandym;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserFileModel {

	//chemin d'acces au dossier contenant les scripts
	public final String ScriptDir = "/home/giann/tmp/script";
	//chemin d'acces aux fichiers des utilisateurs voulants garder les données pour des statistiques
	public final String UserDataDir = "/home/giann/tmp/userData/";
	//chemin d'acces aux fichiers des utilisateurs voulants que leurs données soit supprimées.
	public final String UserDataDirDel = "/home/giann/tmp/userDataDel/";
	


	//nom du fichier envoyé
	private String name;
	//fichier envoyé
	private CommonsMultipartFile file;

	//nom du script pour executer le fichier envoyé
	private String script;
	//nom du site auquel appartient l'utilisateur
	private String site;
	//nom du type a utiliser pour le script (ConStat)
	private String type;
	//nom de la méthode a utiliser pour les script (ConStat)
	private String methode;
	//nom de sortie des fichiers générés
	private String outputName;
	//nom du dossier ou sera enregistrer toutes le données
	private String unique;
	//flag permettant de savoir si les données seront dans UserDataDir ou UserDataDirDel
	private Boolean conserver;


	

	public UserFileModel(){ 
		this.script=null;
		this.site=null;
		this.type=null;
		this.methode=null;
		this.setOutputName(null);
		conserver=false;
	}

	public UserFileModel(String script,String site){
		this.script=script;
		this.site=site;
		this.type=null;
		this.methode=null;
		this.setOutputName(null);
		conserver=false;

		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		unique = dateFormat.format(actuelle)+"_"+this.site+"_"+this.script+(this.methode!=null?"_"+this.methode:"")+(this.type!=null?"_"+this.type:"")+"_"+((int)(Math.random()*1000));
	}
	
	public UserFileModel(String script,String site,String methode,String type){
		this.script=script;
		this.site=site;
		this.type=type;
		this.methode=methode;
		this.setOutputName(null);
		conserver=false;

		Date actuelle = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
		unique = dateFormat.format(actuelle)+"_"+this.site+"_"+this.script+(this.methode!=null?"_"+this.methode:"")+(this.type!=null?"_"+this.type:"")+"_"+((int)(Math.random()*1000));
	}



	
	public Map<String,ArrayList<String>> execute(){
		Map<String,ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		//repertoire de base contenant tout les fichiers pour cette session
		String dir = (conserver?UserDataDir:UserDataDirDel)+unique;
		String outputDir = dir+"/output";
		
		
		File file = new File(outputDir);
		file.mkdir();

		if(unique==null){
			Date actuelle = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
			unique = dateFormat.format(actuelle)+"_"+this.site+"_"+this.script+(this.methode!=null?"_"+this.methode:"")+(this.type!=null?"_"+this.type:"")+"_"+((int)(Math.random()*1000));
		}

		try {
			System.out.println("Execution : ..."+methode+":"+type);
			//commande qui sera executé, il est possible de l'executer en cas de problème pour voir le resultat du script R et de voir les erreurs
			String cmd = "\tRscript " + ScriptDir +"/"+ ScriptModel.scriptSelected(Integer.parseInt(script))[0] +" "+ (dir) +" "+ name +" "+ outputDir +" "+ (getOutputName()!=null?getOutputName():name)+" "+(methode!=null?methode:"")+" "+(type!=null?type:"");
			System.out.println(cmd);
			//on execute la commande
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

		//on liste les fichiers créé pour les envoyer a la vue
		int filecount=0;
		//File file = new File(outputDir);
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

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}
	
	

}
