package com.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.data.classes.*;
import com.data.core.DBWorker;

public class EnterpriseModelsManager {
    private final String GET_LAST_MODEL = "SELECT * FROM models WHERE last_insert_id() = id";
    private final String GET_ALL_MODELS = "SELECT * FROM models";
    private final String GET_MODEL_BY_ID = "SELECT * FROM models WHERE id=";
    private final String GET_WORKSHOP_BY_ID = "SELECT * FROM workshops where id=";
    private final String GET_ROUTE_BY_ID = "SELECT * FROM routes WHERE id=";
    private final String GET_WORKSHOP_NODES = "SELECT * FROM workshops_route WHERE route_id=";
    private final String GET_WORKSHOP_LINES = "SELECT * FROM lines_route WHERE route_id=";
    private final String GET_COMPONENT_BY_ID = "SELECT * FROM components WHERE id=";
    private final String GET_PRODUCT_BY_ID = "SELECT * FROM products WHERE id=";
    private final String GET_USED_PRODUCTS = "SELECT * FROM product_to_product WHERE to_product_id=";
    private final String GET_USED_COMPONENTS = "SELECT * FROM component_to_product WHERE to_product_id=";
    private final String ADD_WORKSHOP =
            "INSERT INTO workshops SET name=?; INSERT INTO models SET workshop=last_insert_id(), type=\"workshop\", title=?;";
    private final String ADD_ROUTE =
            "INSERT INTO routes SET id=null; INSERT INTO models SET route=last_insert_id(), type=\"route\", title=?;";
    private final String ADD_COMPONENT =
            "INSERT INTO components SET name=?; INSERT INTO models SET component=last_insert_id(), type=\"component\", title=?;";
    private final String ADD_PRODUCT =
            "INSERT INTO products SET name=?; INSERT INTO models SET product=last_insert_id(), type=\"product\", title=?;";
    private final String ADD_WORKSHOP_NODE = "INSERT INTO workshops_route SET workshop_id=?, route_id=?, position_x=?, position_y=?";
    private final String ADD_WORKSHOP_LINE = "INSERT INTO lines_route SET route_id=?, start=?, end=?";
    private final String ADD_USED_PRODUCT = "INSERT INTO product_to_product SET product_id=?, to_product_id=?";
    private final String ADD_USED_COMPONENT = "INSERT INTO component_to_product SET component_id=?, to_product_id=?";
    private final String UPDATE_WORKSHOP = "UPDATE workshops SET name=?, description=? WHERE id=?";
    private final String UPDATE_COMPONENT = "UPDATE components SET name=?, drawing=?, route=?, description=? WHERE id=?";
    private final String UPDATE_PRODUCT = "UPDATE products SET name=?, drawing=?, route=?, description=? WHERE id=?";
    private final String UPDATE_TITLE = "UPDATE models SET title=? WHERE id=?";
    private final String DELETE_WORKSHOP_NODES = "DELETE FROM workshops_route WHERE route_id=";
    private final String DELETE_WORKSHOP_LINES = "DELETE FROM lines_route WHERE route_id=";
    private final String DELETE_USED_PRODUCTS = "DELETE FROM product_to_product WHERE to_product_id=";
    private final String DELETE_USED_COMPONENTS = "DELETE FROM component_to_product WHERE to_product_id=";
    private final String DELETE_WORKSHOP_BY_ID = "DELETE FROM workshops WHERE id=";
    private final String DELETE_ROUTE_BY_ID = "DELETE FROM routes WHERE id=";
    private final String DELETE_COMPONENT_BY_ID = "DELETE FROM components WHERE id=";
    private final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE id=";


    private DBWorker worker;


    public EnterpriseModelsManager() {
        worker = new DBWorker();
        worker.connect();
    }

    public ArrayList<EnterpriseModel> getAllModels() {
        ArrayList<EnterpriseModel> modelsList = new ArrayList<EnterpriseModel>();

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(GET_ALL_MODELS);

            while (res.next()) {
                EnterpriseModel enterpriseModel = fillModel(res);
                modelsList.add(enterpriseModel);
            }
        } catch (SQLException e) {
            System.out.println("Can`t create statement in getAllModels() func");
            e.getStackTrace();
        }

        return modelsList;
    }

    public EnterpriseModel getModelById(int id) {
        EnterpriseModel enterpriseModel = null;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(GET_MODEL_BY_ID + Integer.toString(id));
            res.next();
            enterpriseModel = fillModel(res);
        } catch (SQLException e) {
            System.out.println("Can`t create prepare statement in getModelById() func");
            e.getStackTrace();
        }

        return enterpriseModel;
    }

    public Workshop getWorkshopById(int id) {
        Workshop workshop = null;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(GET_WORKSHOP_BY_ID + Integer.toString(id));
            res.next();
            workshop = fillWorkshop(res);
        } catch (SQLException e) {
            System.out.println("Can`t create prepare statement in getWorkshopById() func");
            e.getStackTrace();
        }

        return workshop;
    }

    public Route getRouteById(int id) {
        Route route = null;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet resLines = statement.executeQuery(GET_WORKSHOP_LINES + Integer.toString(id));
            ArrayList<WorkshopLine> workshopLinesList = new ArrayList<WorkshopLine>();
            while(resLines.next()) {
                WorkshopLine workshopLine = fillWorkshopLine(resLines);
                workshopLinesList.add(workshopLine);
            }
            resLines.close();

            ResultSet resWorkshops = statement.executeQuery(GET_WORKSHOP_NODES + Integer.toString(id));
            ArrayList<WorkshopNode> workshopNodesList = new ArrayList<WorkshopNode>();
            while(resWorkshops.next()) {
                WorkshopNode workshopNode = fillWorkshopNode(resWorkshops);
                workshopNodesList.add(workshopNode);
            }
            resWorkshops.close();

            ResultSet resRout = statement.executeQuery(GET_ROUTE_BY_ID + Integer.toString(id));

            resRout.next();
            route = fillRoute(resRout,
                    (WorkshopNode[]) workshopNodesList.toArray(new WorkshopNode[0]),
                    (WorkshopLine[]) workshopLinesList.toArray(new WorkshopLine[0])
            );
            resRout.close();

        } catch (SQLException e) {
            System.out.println("Can`t create prepare statement in getRouteById() func");
            e.getStackTrace();
        }

        return route;
    }

    public Component getComponentById(int id) {
        Component component = null;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet res = statement.executeQuery(GET_COMPONENT_BY_ID + Integer.toString(id));
            res.next();
            component = fillComponent(res);
        } catch (SQLException e) {
            System.out.println("Can`t create prepare statement in getComponentById() func");
            e.getStackTrace();
        }

        return component;
    }

    public Product getProductById(int id) {
        Product product = null;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            ResultSet resUsedProducts = statement.executeQuery(GET_USED_PRODUCTS + Integer.toString(id));
            ArrayList<Integer> usedProductsList = new ArrayList<Integer>();
            while(resUsedProducts.next()) {
                usedProductsList.add(resUsedProducts.getInt(1));
            }
            resUsedProducts.close();

            ResultSet resUsedComponents = statement.executeQuery(GET_USED_COMPONENTS + Integer.toString(id));
            ArrayList<Integer> usedComponentsList = new ArrayList<Integer>();
            while(resUsedComponents.next()) {
                usedComponentsList.add(resUsedComponents.getInt(1));
            }
            resUsedComponents.close();

            ResultSet resProduct = statement.executeQuery(GET_PRODUCT_BY_ID + Integer.toString(id));
            resProduct.next();
            product = fillProduct(
                    resProduct,
                    usedProductsList.stream().mapToInt(x -> x).toArray(),
                    usedComponentsList.stream().mapToInt(x -> x).toArray()
                    );
            resProduct.close();
        } catch(SQLException e) {
            System.out.println("Can`t create prepare statement in getProductById() func");
            e.getStackTrace();
        }

        return product;
    }

    public EnterpriseModel addWorkshop(String title) {
        return createModel(2, ADD_WORKSHOP, title);
    }

    public EnterpriseModel addRoute(String title) {
        return createModel(1, ADD_ROUTE, title);
    }

    public EnterpriseModel addComponent(String title) {
        return createModel(2, ADD_COMPONENT, title);
    }

    public EnterpriseModel addProduct(String title) {
        return createModel(2, ADD_PRODUCT, title);
    }

    public boolean updateWorkshop(Workshop workshop) {
        boolean isExecute = false;

        try(PreparedStatement preparedStatement = this.worker.getConnection().prepareStatement(UPDATE_WORKSHOP)) {
            preparedStatement.setString(1, workshop.getName());
            preparedStatement.setString(2, workshop.getDescription());
            preparedStatement.setInt(3, workshop.getId());
            preparedStatement.execute();

            isExecute = true;
        } catch(SQLException e) {
            System.out.println("Can`t update workshop");
            e.getStackTrace();
        }

        return isExecute;
    }

    public boolean updateRoute(Route route) {
        boolean isExecute = false;

        try(PreparedStatement psWorkshopNode = this.worker.getConnection().prepareStatement(ADD_WORKSHOP_NODE);
        PreparedStatement psWorkshopLine = this.worker.getConnection().prepareStatement(ADD_WORKSHOP_LINE);
        Statement statement = this.worker.getConnection().createStatement()) {
            WorkshopLine[] workshopLines = route.getWorkshopLines();
            WorkshopNode[] workshopNodes = route.getWorkshopNodes();

            statement.execute(DELETE_WORKSHOP_LINES + Integer.toString(route.getId()));
            statement.execute(DELETE_WORKSHOP_NODES + Integer.toString(route.getId()));

            for(WorkshopNode workshopNode : workshopNodes) {
                psWorkshopNode.setInt(1, workshopNode.getWorkshopId());
                psWorkshopNode.setInt(2, route.getId());
                psWorkshopNode.setInt(3, workshopNode.getPositionX());
                psWorkshopNode.setInt(4, workshopNode.getPositionY());
                psWorkshopNode.addBatch();
            }
            psWorkshopNode.executeBatch();

            for(WorkshopLine workshopLine : workshopLines) {
                psWorkshopLine.setInt(1, route.getId());
                psWorkshopLine.setInt(2, workshopLine.getStart());
                psWorkshopLine.setInt(3, workshopLine.getEnd());
                psWorkshopLine.addBatch();
            }
            psWorkshopLine.executeBatch();

            isExecute = true;
        } catch (SQLException e) {
            System.out.println("Can`t update route");
            e.getStackTrace();
        }

        return isExecute;
    }

    public boolean updateComponent(Component component) {
        boolean isExecute = false;

        try(PreparedStatement preparedStatement = this.worker.getConnection().prepareStatement(UPDATE_COMPONENT)) {
            preparedStatement.setString(1, component.getName());
            preparedStatement.setString(2, component.getDrawing());
            preparedStatement.setInt(3, component.getRouteId());
            preparedStatement.setString(4, component.getDescription());
            preparedStatement.setInt(5, component.getId());
            preparedStatement.execute();

            isExecute = true;
        } catch(SQLException e) {
            System.out.println("Can`t update component");
            e.getStackTrace();
        }

        return isExecute;
    }

    public boolean updateProduct(Product product) {
        boolean isExecute = false;

        try(PreparedStatement psProduct = this.worker.getConnection().prepareStatement(UPDATE_PRODUCT);
        PreparedStatement psUsedProduct = this.worker.getConnection().prepareStatement(ADD_USED_PRODUCT);
        PreparedStatement psUsedComponent = this.worker.getConnection().prepareStatement(ADD_USED_COMPONENT);
        Statement statement = this.worker.getConnection().createStatement()) {
            statement.execute(DELETE_USED_PRODUCTS + Integer.toString(product.getId()));
            statement.execute(DELETE_USED_COMPONENTS + Integer.toString(product.getId()));

            psProduct.setString(1, product.getName());
            psProduct.setString(2, product.getDrawing());
            psProduct.setInt(3, product.getRouteId());
            psProduct.setString(4, product.getDescription());
            psProduct.setInt(5, product.getId());
            psProduct.execute();

            int[] usedProductIds = product.getUsedProductIds();
            for(int usedProductId : usedProductIds) {
                psUsedProduct.setInt(1, usedProductId);
                psUsedProduct.setInt(2, product.getId());
                psUsedProduct.addBatch();
            }
            psUsedProduct.executeBatch();

            int[] usedComponentIds = product.getUsedComponentIds();
            for(int usedComponentId : usedComponentIds) {
                psUsedComponent.setInt(1, usedComponentId);
                psUsedComponent.setInt(2, product.getId());
                psUsedComponent.addBatch();
            }
            psUsedComponent.executeBatch();

            isExecute = true;
        } catch(SQLException e) {
            System.out.println("Can`t update product");
            e.getStackTrace();
        }

        return isExecute;
    }

    public boolean updateTitle(int id, String title) {
        boolean isExecute = false;

        try(PreparedStatement preparedStatement = this.worker.getConnection().prepareStatement(UPDATE_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

            isExecute = true;
        } catch(SQLException e) {
            System.out.println("Can`t update title");
            e.getStackTrace();
        }

        return isExecute;
    }

    public boolean deleteModel(int id, String type) {
        boolean isExecute = false;

        try(Statement statement = this.worker.getConnection().createStatement()) {
            switch(type) {
                case "workshop":
                    statement.execute(DELETE_WORKSHOP_BY_ID + Integer.toString(id));
                    isExecute = true;
                    break;
                case "route":
                    statement.execute(DELETE_ROUTE_BY_ID + Integer.toString(id));
                    isExecute = true;
                    break;
                case "component":
                    statement.execute(DELETE_COMPONENT_BY_ID + Integer.toString(id));
                    isExecute = true;
                    break;
                case "product":
                    statement.execute(DELETE_PRODUCT_BY_ID + Integer.toString(id));
                    isExecute = true;
                    break;
                default:
                    System.out.println("Type is`n recognize");
            }
        } catch(SQLException e) {
            System.out.println("Can`t delete model");
            e.getStackTrace();
        }

        return isExecute;
    }


    private EnterpriseModel createModel(int countOfParameters, String query, String title) {
        EnterpriseModel enterpriseModel = null;

        try (PreparedStatement preparedStatement = this.worker.getConnection().prepareStatement(query);
        Statement statement = this.worker.getConnection().createStatement()) {
            for(int i = 1; i <= countOfParameters; i++) {
                preparedStatement.setString(i, title);
            }
            preparedStatement.execute();

            ResultSet resultSet = statement.executeQuery(GET_LAST_MODEL);
            resultSet.next();

            enterpriseModel = fillModel(resultSet);
        } catch (SQLException e) {
            System.out.println("Can`t create prepare statement in createModel() func");
            e.getStackTrace();
        }

        return enterpriseModel;
    }

    private EnterpriseModel fillModel(ResultSet res) {
        EnterpriseModel enterpriseModel = null;

        try {
            int id = res.getInt(1);
            String type = res.getString(2);
            String title = res.getString(3);
            int bodyId = 0;

            switch(type) {
                case "product":
                    bodyId = res.getInt(4);
                    break;
                case "component":
                    bodyId = res.getInt(5);
                    break;
                case "route":
                    bodyId = res.getInt(6);
                    break;
                case "workshop":
                    bodyId = res.getInt(7);
                    break;
            }

            enterpriseModel = new EnterpriseModel(id, type, title, bodyId);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return enterpriseModel;
    }

    private Workshop fillWorkshop(ResultSet res) {
        Workshop workshop = null;

        try {
            int id = res.getInt(1);
            String name = res.getString(2);
            String description = res.getString(3);

            workshop = new Workshop(id, name, description);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return workshop;
    }

    private WorkshopLine fillWorkshopLine(ResultSet res) {
        WorkshopLine workshopLines = null;

        try {
            int start = res.getInt(2);
            int end = res.getInt(3);

            workshopLines = new WorkshopLine(start, end);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return workshopLines;
    }

    private WorkshopNode fillWorkshopNode(ResultSet res) {
        WorkshopNode workshopNode = null;

        try {
            int workshopId = res.getInt(1);
            int positionX = res.getInt(3);
            int positionY = res.getInt(4);

            workshopNode = new WorkshopNode(workshopId, positionX, positionY);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return workshopNode;
    }

    private Route fillRoute(ResultSet res, WorkshopNode[] workshopNodes, WorkshopLine[] workshopLines) {
        Route route = null;

        try {
            int id = res.getInt(1);

            route = new Route(id, workshopNodes, workshopLines);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return route;
    }

    private Component fillComponent(ResultSet res) {
        Component component = null;

        try {
            int id = res.getInt(1);
            String name = res.getString(2);
            String drawing = res.getString(3);
            int routeId = res.getInt(4);
            String description = res.getString(5);

            component = new Component(id, name, drawing, routeId, description);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return component;
    }

    private Product fillProduct(ResultSet res, int[] usedProducts, int[] usedComponents) {
        Product product = null;

        try {
            int id = res.getInt(1);
            String name = res.getString(2);
            String drawing = res.getString(3);
            int routeId = res.getInt(4);
            String description = res.getString(5);

            product = new Product(id, name, drawing, routeId, usedProducts, usedComponents, description);
        } catch (SQLException e) {
            System.out.println("Can`t get cell");
            e.getStackTrace();
        }

        return product;
    }
}
