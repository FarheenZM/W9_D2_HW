package controllers;

import db.DBHelper;
import models.Department;
import models.Employee;
import models.Manager;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static spark.Spark.get;
import static spark.Spark.post;

public class DepartmentsController {

    public DepartmentsController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        get("/departments", (req, res) -> { //display all managers
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class);
            model.put("template", "templates/managers/index.vtl");
            model.put("departments", departments);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/departments/new", (req, res) -> { // opens new manager form
            HashMap<String, Object> model = new HashMap<>();
            List<Department> departments = DBHelper.getAll(Department.class); //for dropdownlist
            model.put("departments", departments);
            model.put("template", "templates/departments/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

//        post("/departments", (req, res) -> { // displays updated manager list
//            int departmentId = Integer.parseInt(req.queryParams("department"));
//            Department department = DBHelper.find(departmentId, Department.class);
//            String title = req.queryParams("title");
//            Set<Employee> allEmployees = req.queryParams("employees");
//
//            Department department = new Department(title, allEmployees);
//            DBHelper.save(department);
//            res.redirect("/departments");
//            return null;
//        }, new VelocityTemplateEngine());

    }

    }
