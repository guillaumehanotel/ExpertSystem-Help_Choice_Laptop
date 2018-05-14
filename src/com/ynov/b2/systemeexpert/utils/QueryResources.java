package com.ynov.b2.systemeexpert.utils;

import java.util.HashMap;

public class QueryResources {
	
	public HashMap<String, String> queryFactMap;
	private static QueryResources instance;
	
	private static final int GPU_LOW_BOUNDARY = 50;
	private static final int GPU_HIGH_BOUNDARY = 150;
	
	private static final int SCREEN_LOW_BOUNDARY = 14;
	private static final int SCREEN_HIGH_BOUNDARY = 16;
	
	private static final int RAM_LOW_BOUNDARY = 4;
	private static final int RAM_MID_BOUNDARY = 8;
	private static final int RAM_HIGH_BOUNDARY = 16;
	private static final int RAM_MAX_BOUNDARY = 32;
	
	private static final int HDD_LOW_BOUNDARY = 500;
	private static final int HDD_HIGH_BOUNDARY = 1200;
	
	
	private QueryResources() {
		queryFactMap = new HashMap<>();
		/*
		queryFactMap.put("GPU1", "AND r.rank <= "+QueryResources.GPU_LOW_BOUNDARY);
		queryFactMap.put("GPU2", "AND r.rank > "+QueryResources.GPU_LOW_BOUNDARY+ " AND r.rank <= "+QueryResources.GPU_HIGH_BOUNDARY);
		queryFactMap.put("GPU3", "AND r.rank > "+QueryResources.GPU_HIGH_BOUNDARY);
		*/
		queryFactMap.put("GrandEcran", "AND size >= "+QueryResources.SCREEN_HIGH_BOUNDARY);
		queryFactMap.put("MoyenEcran", "AND size >= "+QueryResources.SCREEN_LOW_BOUNDARY+ " AND size < "+QueryResources.SCREEN_HIGH_BOUNDARY);
		queryFactMap.put("PetitEcran", "AND size < "+QueryResources.SCREEN_LOW_BOUNDARY);
		
		queryFactMap.put("AvecSSD", "AND ssd = 1");
		queryFactMap.put("SansSSD", "AND ssd = 0");
		queryFactMap.put("DefaultSSD", "");
		
		queryFactMap.put("MinRAM", "AND ram >= "+QueryResources.RAM_LOW_BOUNDARY+" AND ram < "+QueryResources.RAM_MID_BOUNDARY);
		queryFactMap.put("MoyRAM", "AND ram >= "+QueryResources.RAM_MID_BOUNDARY+" AND ram < "+QueryResources.RAM_HIGH_BOUNDARY);
		queryFactMap.put("BigRAM", "AND ram >= "+QueryResources.RAM_HIGH_BOUNDARY+" AND ram < "+QueryResources.RAM_MAX_BOUNDARY);
		queryFactMap.put("ExtraRAM", "AND ram >= "+QueryResources.RAM_MAX_BOUNDARY);
		
		queryFactMap.put("MinHDD", "AND storage <= "+QueryResources.HDD_LOW_BOUNDARY);
		queryFactMap.put("MoyHDD", "AND storage > "+QueryResources.HDD_LOW_BOUNDARY+" AND storage <= "+QueryResources.HDD_HIGH_BOUNDARY);
		queryFactMap.put("MaxHDD", "AND storage > "+QueryResources.HDD_HIGH_BOUNDARY);
		
	}
	
	
	public static QueryResources getInstance() {
		if(instance == null) {
			instance = new QueryResources();
		}
		return instance;
	}
	
	

}
