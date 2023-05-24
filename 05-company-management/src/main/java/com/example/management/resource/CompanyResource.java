package com.example.management.resource;

import com.example.management.entity.Company;
import com.example.management.response.HttpResponse;
import com.example.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class CompanyResource {
    private CompanyService companyService;

    @Autowired
    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/company")
    public ResponseEntity<HttpResponse> getAllCompanies(){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("companies", companyService.getAll()))
                        .message("Company retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<HttpResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("company", companyService.getCompanyById(id)))
                        .message("Company retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/company/name/{name}")
    public ResponseEntity<HttpResponse> getCompanyByName(@PathVariable String name){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("company", companyService.getCompanyByName(name)))
                        .message("Company retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/company")
    public ResponseEntity<HttpResponse> addCompany(@RequestBody Company company){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("company", companyService.createCompany(company)))
                        .message("Company created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<HttpResponse> deleteCompany(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("Company deleted", companyService.deleteCompany(id)))
                        .message("deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


}
