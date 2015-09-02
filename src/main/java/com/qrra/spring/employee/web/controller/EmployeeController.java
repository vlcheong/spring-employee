package com.qrra.spring.employee.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qrra.spring.employee.domain.Company;
import com.qrra.spring.employee.domain.Employee;
import com.qrra.spring.employee.model.InputResult;
import com.qrra.spring.employee.model.Page;
import com.qrra.spring.employee.service.CompanyService;
import com.qrra.spring.employee.service.EmployeeService;
import com.qrra.spring.employee.web.form.EmployeeSearchForm;
import com.qrra.spring.employee.web.support.ActionCode;
import com.qrra.spring.employee.web.support.ResponseInfo;
import com.qrra.spring.employee.web.support.StatusCode;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    private final CompanyService companyService;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(
            final CompanyService companyService,
            final EmployeeService employeeService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @ModelAttribute(value = "companies")
    public List<Company> companies() {
        return companyService.findAll();
    }

    @RequestMapping("/searchForm")
    public String searchForm() {
        return "employeeSearch";
    }

    @RequestMapping("/search")
    @ResponseBody
    public Page<Map<Integer, Object>> search(EmployeeSearchForm form,
            @RequestParam(value = "p", required = false, defaultValue = "1") int p) {
        return employeeService.findByPage(form, p);
    }

    @RequestMapping("/form")
    public String form(
            @RequestParam(value = "id", required = false, defaultValue = "0") long id,
            ModelMap model) {
        if (id > 0L) {
            model.addAttribute(ActionCode.ACTION, ActionCode.EDIT.getName());
            model.addAttribute("employee", employeeService.find(id));
        } else {
            model.addAttribute(ActionCode.ACTION, ActionCode.NEW.getName());
            model.addAttribute("employee", new Employee());
        }
        return "employeeForm";
    }

    @RequestMapping("/view")
    public String view(@RequestParam(value = "id") long id, ModelMap model) {
        if (id > 0L) {
            model.addAttribute(ActionCode.ACTION, ActionCode.VIEW.getName());
            model.addAttribute("employee", employeeService.find(id));
            return "employeeForm";
        }
        model.addAttribute(ActionCode.ERRORS, Arrays.asList("Employee " + id + " not found"));
        return "error";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseInfo<Employee> save(Employee employee) {
        InputResult<Employee> result = employeeService.save(employee);
        if (result.hasErrors()) {
            return ResponseInfo
                    .<Employee>builder()
                    .statusCode(StatusCode.ERROR)
                    .messages(result.getAllMessages())
                    .build();
        }
        return ResponseInfo
                .<Employee>builder()
                .statusCode(StatusCode.OK)
                .entity(employee)
                .message("Employee successfully saved")
                .build();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseInfo<Void> remove(@RequestParam(value = "id") long id) {
        employeeService.remove(id);
        return ResponseInfo
                .<Void>builder()
                .statusCode(StatusCode.OK)
                .message("Employee successfully deleted")
                .build();
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseInfo<Void> batchDelete(@RequestParam(value = "id") long[] ids) {
        employeeService.batchRemove(ids);
        return ResponseInfo
                .<Void>builder()
                .statusCode(StatusCode.OK)
                .message("Employee(s) successfully deleted")
                .build();
    }
}