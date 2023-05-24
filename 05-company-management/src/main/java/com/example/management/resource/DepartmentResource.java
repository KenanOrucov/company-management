package com.example.management.resource;

import com.example.management.entity.Department;
import com.example.management.response.HttpResponse;
import com.example.management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    public ResponseEntity<HttpResponse> getAllCompanies(){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("departments", departmentService.getAll()))
                        .message("Department retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<HttpResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("department", departmentService.getDepartmentById(id)))
                        .message("Department retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/department/name/{name}")
    public ResponseEntity<HttpResponse> getCompanyByName(@PathVariable String name){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("department", departmentService.getDepartmentByName(name)))
                        .message("Department retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/department")
    public ResponseEntity<HttpResponse> addCompany(@RequestBody Department department){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("department", departmentService.createDepartment(department)))
                        .message("Department created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<HttpResponse> deleteCompany(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("Department department", departmentService.deleteDepartment(id)))
                        .message("deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );    }
}
