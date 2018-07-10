package controllers;

import db.DBHelper;
import models.Department;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class ManagersController {

    public ManagersController() {

        this.setupEndpoints();

    }

    private void setupEndpoints(){
        get("/managers", (req,res) -> { //display all managers
            HashMap<String, Object> model = new HashMap<>();
            List<Manager> managers = DBHelper.getAll(Manager.class);
            model.put("template", "templates/managers/index.vtl");
            model.put("managers", managers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/managers/new", (req,res) -> { // opens new manager form
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class); //for dropdownlist
            model.put("departments", departments);
            model.put("template", "templates/managers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/managers", (req, res) -> { // displays updated manager list
            int departmentId = Integer.parseInt(req.queryParams("department"));
            Department department = DBHelper.find(departmentId, Department.class);
            String firstName = req.queryParams("firstName");
            String lastname = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            int budget = Integer.parseInt(req.queryParams("budget"));

            Manager manager = new Manager(firstName, lastname, salary, department, budget);
            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        get("/managers/:id", (req, res) -> { //displays one manager
            HashMap<String, Object> model = new HashMap<>();
            String stringId = req.params(":id");
            Integer id = Integer.parseInt(stringId);
            Manager manager = DBHelper.find(id, Manager.class);
            model.put("manager", manager);
            model.put("template", "templates/managers/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


        get("/managers/:id/edit", (req,res) -> { //opens edit form
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class); //for dropdownlist
            String stringId = req.params(":id");
            Integer id = Integer.parseInt(stringId);
            Manager manager = DBHelper.find(id, Manager.class);
            model.put("manager", manager);
            model.put("departments", departments);
            model.put("template", "templates/managers/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/managers/:id/edit", (req, res) -> { //posts edited manager & displayes manager list

            String firstName = req.queryParams("firstName");
            String lastName = req.queryParams("lastName");
            int salary = Integer.parseInt(req.queryParams("salary"));
            Department department = DBHelper.find(Integer.parseInt(req.queryParams("department")), Department.class);
            double budget = Double.parseDouble(req.queryParams("budget"));

            Manager manager = DBHelper.find(Integer.parseInt(req.params(":id")), Manager.class);

            manager.setFirstName(firstName);
            manager.setLastName(lastName);
            manager.setSalary(salary);
            manager.setDepartment(department);
            manager.setBudget(budget);

            DBHelper.save(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

        post("/managers/:id/delete", (req, res) -> { //deletes manager &  displays manager list ; only post route
            Manager manager = DBHelper.find(Integer.parseInt(req.params(":id")), Manager.class);
            DBHelper.delete(manager);
            res.redirect("/managers");
            return null;
        }, new VelocityTemplateEngine());

    }
}

