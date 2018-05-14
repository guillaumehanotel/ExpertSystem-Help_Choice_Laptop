package com.ynov.b2.systemeexpert.beans;

public class Computer {
	
	private int id;
	private double weight;
	private String model;
	private boolean ssd;
	private double ram;
	private double price;
	private double storage;
	private String cpu_model;
	private double size;
	private String resolution;
	private String gpu_model;
	private int rank;
	
	public Computer() {}
	
	public Computer(int id, double weight, String model, boolean ssd, double ram, double price, double storage, String cpu_model,
			double size, String resolution, String gpu_model, int rank) {
		this.id = id;
		this.weight = weight;
		this.model = model;
		this.ssd = ssd;
		this.ram = ram;
		this.price = price;
		this.storage = storage;
		this.cpu_model = cpu_model;
		this.size = size;
		this.resolution = resolution;
		this.gpu_model = gpu_model;
		this.rank = rank;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean isSsd() {
		return ssd;
	}

	public void setSsd(boolean ssd) {
		this.ssd = ssd;
	}

	public double getRam() {
		return ram;
	}

	public void setRam(double ram) {
		this.ram = ram;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getStorage() {
		return storage;
	}

	public void setStorage(double storage) {
		this.storage = storage;
	}

	public String getCpu_model() {
		return cpu_model;
	}

	public void setCpu_model(String cpu_model) {
		this.cpu_model = cpu_model;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getGpu_model() {
		return gpu_model;
	}

	public void setGpu_model(String gpu_model) {
		this.gpu_model = gpu_model;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", weight=" + weight + ", model=" + model + ", ssd=" + ssd + ", ram=" + ram
				+ ", price=" + price + ", storage=" + storage + ", cpu_model=" + cpu_model + ", size=" + size
				+ ", resolution=" + resolution + ", gpu_model=" + gpu_model + ", rank=" + rank + "]";
	}


	
	
	
}
