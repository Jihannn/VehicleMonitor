package com.jihan.monitor.phone.model;

public class Vehicle {
    private int id;
    private String plate_number;
    private String brand;
    private String model;
    private int production_year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getProduction_year() {
        return production_year;
    }

    public void setProduction_year(int production_year) {
        this.production_year = production_year;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", plate_number='" + plate_number + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", production_year=" + production_year +
                '}';
    }
}
