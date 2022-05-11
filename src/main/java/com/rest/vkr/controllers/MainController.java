package com.rest.vkr.controllers;

import com.data.EnterpriseModelsManager;
import com.data.classes.EnterpriseModel;

import com.data.classes.*;
import com.google.gson.Gson;
import com.rest.vkr.exeptions.NotFoundException;
import com.rest.vkr.models.DeleteModel;
import com.rest.vkr.models.NewModel;
import com.rest.vkr.models.PostBody;
import com.rest.vkr.models.UpdateModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("api")
public class MainController {
    private String TOKEN = "sXUlQYd6RwkjVhmDTQ7WkgbLi5IwJtIX6JJuNxRfEjUk9uQiv4";
    private Gson g = new Gson();
    private EnterpriseModelsManager enterpriseModelsManager = new EnterpriseModelsManager();

    @GetMapping("/models")
    public String getModels() {
        ArrayList<EnterpriseModel> enterpriseModels = this.enterpriseModelsManager.getAllModels();

        return g.toJson(enterpriseModels);
    }

    @GetMapping("/models/{id}")
    public String getModel(@PathVariable String id) {
        EnterpriseModel enterpriseModel = this.enterpriseModelsManager.getModelById(Integer.parseInt(id));

        if(enterpriseModel == null) {
            throw new NotFoundException();
        }

        return g.toJson(enterpriseModel);
    }

    @GetMapping("/workshops/{id}")
    public String getWorkshop(@PathVariable String id) {
        Workshop workshop = this.enterpriseModelsManager.getWorkshopById(Integer.parseInt(id));

        if(workshop == null) {
            throw new NotFoundException();
        }

        return g.toJson(workshop);
    }

    @GetMapping("/routes/{id}")
    public String getRout(@PathVariable String id) {
       Route route = this.enterpriseModelsManager.getRouteById(Integer.parseInt(id));

        if(route == null) {
            throw new NotFoundException();
        }

        return g.toJson(route);
    }

    @GetMapping("/components/{id}")
    public String getComponent(@PathVariable String id) {
        Component component = this.enterpriseModelsManager.getComponentById(Integer.parseInt(id));

        if(component == null) {
            throw new NotFoundException();
        }

        return g.toJson(component);
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable String id) {
        Product product = this.enterpriseModelsManager.getProductById(Integer.parseInt(id));

        if(product == null) {
            throw new NotFoundException();
        }

        return g.toJson(product);
    }

    @PostMapping("/createModel")
    public String createModel(@RequestBody PostBody<NewModel> postBody) {
        postBody.checkToken(TOKEN);
        NewModel newModel = postBody.getContent();

        EnterpriseModel enterpriseModel = null;

        switch (newModel.getType()) {
            case "workshop":
                enterpriseModel = this.enterpriseModelsManager.addWorkshop(newModel.getTitle());
                break;
            case "route":
                enterpriseModel = this.enterpriseModelsManager.addRoute(newModel.getTitle());
                break;
            case "component":
                enterpriseModel = this.enterpriseModelsManager.addComponent(newModel.getTitle());
                break;
            case "product":
                enterpriseModel = this.enterpriseModelsManager.addProduct(newModel.getTitle());
                break;
            default:
                throw new NotFoundException();
        }

        if (enterpriseModel == null) throw new NotFoundException();

        return g.toJson(enterpriseModel);
    }

    @PutMapping("/updateWorkshop")
    public String updateWorkshop(@RequestBody PostBody<Workshop> postBody) {
        postBody.checkToken(TOKEN);

        Workshop workshop = postBody.getContent();

        this.enterpriseModelsManager.updateWorkshop(workshop);

        Workshop newWorkshop = this.enterpriseModelsManager.getWorkshopById(workshop.getId());

        if (newWorkshop == null) throw new NotFoundException();

        return g.toJson(newWorkshop);
    }

    @PutMapping("/updateRoute")
    public String updateRoute(@RequestBody PostBody<Route> postBody) {
        postBody.checkToken(TOKEN);

        Route route = postBody.getContent();
        this.enterpriseModelsManager.updateRoute(route);
        Route newRoute = this.enterpriseModelsManager.getRouteById(route.getId());

        if (newRoute == null) throw new NotFoundException();

        return g.toJson(newRoute);
    }

    @PutMapping("/updateComponent")
    public String updateComponent(@RequestBody PostBody<Component> postBody) {
        postBody.checkToken(TOKEN);

        Component component = postBody.getContent();
        this.enterpriseModelsManager.updateComponent(component);

        Component newComponent = this.enterpriseModelsManager.getComponentById(component.getId());

        if (newComponent == null) throw new NotFoundException();

        return g.toJson(newComponent);
    }

    @PutMapping("/updateProduct")
    public String updateProduct(@RequestBody PostBody<Product> postBody) {
        postBody.checkToken(TOKEN);

        Product product = postBody.getContent();
        this.enterpriseModelsManager.updateProduct(product);

        Product newProduct = this.enterpriseModelsManager.getProductById(product.getId());

        if (newProduct == null) throw new NotFoundException();

        return g.toJson(newProduct);
    }

    @PutMapping("/updateTitle")
    public String updateTitle(@RequestBody PostBody<UpdateModel> postBody) {
        postBody.checkToken(TOKEN);
        UpdateModel updateModel = postBody.getContent();

        this.enterpriseModelsManager.updateTitle(updateModel.getId(), updateModel.getTitle());

        ArrayList<EnterpriseModel> enterpriseModels = this.enterpriseModelsManager.getAllModels();

        return g.toJson(enterpriseModels);
    }

    @DeleteMapping("/deleteModel")
    public String deleteModel(@RequestBody PostBody<DeleteModel> postBody) {
        postBody.checkToken(TOKEN);
        DeleteModel deleteModel = postBody.getContent();

        this.enterpriseModelsManager.deleteModel(deleteModel.getId(), deleteModel.getType());

        ArrayList<EnterpriseModel> enterpriseModels = this.enterpriseModelsManager.getAllModels();

        return g.toJson(enterpriseModels);
    }
}
