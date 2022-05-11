package com.data.classes;

public class Product {
    private int id;
    private String name;
    private String drawing;
    private int routeId;
    private int[] usedProductIds;
    private int[] usedComponentIds;
    private String description;

    public Product() {
        this(0, "-", "-", 0, new int[]{}, new int[]{}, "-");
    }

    public Product(int id, String name, String drawing, int routeId, int[] usedProductIds, int[] usedComponentIds, String description) {
        this.id = id;
        this.name = name;
        this.drawing = drawing;
        this.routeId = routeId;
        this.usedProductIds = usedProductIds;
        this.usedComponentIds = usedComponentIds;
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

    public int[] getUsedProductIds() {
        return usedProductIds;
    }

    public int[] getUsedComponentIds() {
        return usedComponentIds;
    }

    public String getDescription() {
        return description;
    }

    public void printProduct() {
        System.out.printf("|id: %-5d|name: %-15s|drawing: %-15s|routeId: %-5d|description: %-100s|%n",
                this.id, this.name, this.drawing, this.routeId, this.description);

        System.out.println("===Used Products===");
        if(usedProductIds.length != 0) {
            for(int usedProductId : this.usedProductIds) System.out.printf("|productID: %-5d|\n", usedProductId);
        } else System.out.println("Empty");

        System.out.println("===Used Components===");
        if(usedComponentIds.length != 0) {
            for(int usedComponentId : this.usedComponentIds) System.out.printf("|componentID: %-5d|\n", usedComponentId);
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

        if(usedProductIds.length != 0) {
            for(int usedProductId : this.usedProductIds)
                strBuild.append("productID: ").append(Integer.toString(usedProductId)).append("\n");
        } else strBuild.append("Empty").append("\n");

        strBuild.append("===Used Components==\n");
        if(usedComponentIds.length != 0) {
            for(int usedComponentId : this.usedComponentIds)
                strBuild.append("componentID: ").append(Integer.toString(usedComponentId)).append("\n");
        } else strBuild.append("Empty").append("\n");

        return strBuild.toString();
    }
}
