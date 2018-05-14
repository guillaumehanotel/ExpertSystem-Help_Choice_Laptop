package com.ynov.b2.systemeexpert.utils;

import java.util.ArrayList;

import com.ynov.b2.systemeexpert.beans.IFait;

public class QueryBuilder {
	
	
	private ArrayList<IFait> faits;
	
	public QueryBuilder(ArrayList<IFait> faits) {
		this.faits = new ArrayList<IFait>();
		for(IFait fait : faits) {
			if(fait.getNiveau() != 0)
				this.faits.add(fait);
		}
		
	}
	
	
	public String getRequest() {
		
		QueryResources queryResources = QueryResources.getInstance();
		StringBuilder request = new StringBuilder();
		
		request.append("SELECT c.id, weight, model, ssd, ram, price, storage, cpu_model, size, resolution, gpu_model, rank\n" +
					   "FROM computers c, gpu g, gpu_rank r\n" + 
					   "WHERE c.id = g.computer_id\n" +
					   "AND r.id = g.gpu_rank_id\n");
		
		
		// on parcourt tous les faits inférés
		for(IFait fait : this.faits) {
			// si le nom du fait correspond à une clé du hashmap
			if(queryResources.queryFactMap.containsKey(fait.getNom())) {
				// on récupère le bout de requete correspondant au fait associé
				request.append(queryResources.queryFactMap.get(fait.getNom())+"\n");
			}
		}
		
		
		
		return request.toString();
	}
	
	
	
	
	

}
