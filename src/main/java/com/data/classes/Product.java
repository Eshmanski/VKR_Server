package com.data.classes;

public class Product {
    private int id;
    private String name;
    private String drawing;
    private int routeId;
    private UsedProduct[] usedProducts;
    private UsedComponent[] usedComponents;
    private String description;

    public Product() {
        this(0, "-", "-", 0, new UsedProduct[]{}, new UsedComponent[]{}, "-");
    }

    public Product(int id, String name, String drawing, int routeId, UsedProduct[] usedProducts, UsedComponent[] usedComponents, String description) {
        this.id = id;
        this.name = name;
        this.drawing = drawing;
        this.routeId = routeId;
        this.usedProducts = usedProducts;
        this.usedComponents = usedComponents;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDrawing() {
        return drawing;
    }

    public int getRouteId() {
        return routeId;
    }

    public UsedProduct[] getUsedProducts() {
        return usedProducts;
    }

    public UsedComponent[] getUsedComponents() {
        return usedComponents;
    }

    public String getDescription() {
        return description;
    }

    public void printProduct() {
        System.out.printf("|id: %-5d|name: %-15s|drawing: %-15s|routeId: %-5d|description: %-100s|%n",
                this.id, this.name, this.drawing, this.routeId, this.description);

        System.out.println("===Used Products===");
        if(usedProducts.length != 0) {
            for(UsedProduct usedProduct : this.usedProducts) ;
        } else System.out.println("Empty");

        System.out.println("===Used Components===");
        if(usedComponents.length != 0) {
            for(UsedComponent usedComponent : this.usedComponents) ;
        } else System.out.println("Empty");
    }

    @Override
    public String toString() {
        StringBuilder strBuild = new StringBuilder();
        strBuild.append("id: ").append(Integer.toString(this.id))
                .append("\tname: ").append(this.name)
                .append("\tdrawing: ").append(this.drawing)
                .append("\trouteId: ").append(Integer.toString(this.routeId))
                .append("\tdescription: ").append(this.description).append("\n")
                .append("===Used Products==\n");

        if(usedProducts.length != 0) {
            for(UsedProduct usedProduct : this.usedProducts)
                System.out.println(usedProduct);
        } else strBuild.append("Empty").append("\n");

        strBuild.append("===Used Components==\n");
        if(usedComponents.length != 0) {
            for(UsedComponent usedComponent : this.usedComponents)
                System.out.println(usedComponent);
        } else strBuild.append("Empty").append("\n");

        return strBuild.toString();
    }
}
