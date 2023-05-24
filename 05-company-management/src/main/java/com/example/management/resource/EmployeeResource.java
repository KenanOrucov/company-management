package com.example.management.resource;

import com.example.management.entity.Branch;
import com.example.management.response.HttpResponse;
import com.example.management.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeResource {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/employee")
    public ResponseEntity<HttpResponse> getAllCompanies(){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("employees", employeeService.getAll()))
                        .message("Employee retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<HttpResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("employee", employeeService.getEmployeeById(id)))
                        .message("Employee retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/employee/name/{name}")
    public ResponseEntity<HttpResponse> getCompanyByName(@PathVariable String name){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("employee", employeeService.getEmployeeByName(name)))
                        .message("Employee retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @PostMapping("/employee")
    public ResponseEntity<HttpResponse> addCompany(@RequestParam(name = "id", required = false) Long id,
                                                   @RequestParam(name = "firstName", required = false) String firstName,
                                                   @RequestParam(name = "lastName", required = false) String lastName,
                                                   @RequestParam(name = "speciality", required = false) String speciality,
                                                   @RequestParam(name = "salary", required = false) Integer salary,
                                                   @RequestParam(name = "email", required = false) String email,
                                                   @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
                                                   @RequestParam(name = "branches", required = false) List<Branch> branches,
                                                   @RequestParam(name = "imageUrl", required = false) MultipartFile multipartFile) throws IOException {
        log.info(branches.toString());
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("employee", employeeService.createEmployee(id,firstName, lastName, speciality, salary, email, phoneNumber, branches, multipartFile)))
                        .message("Employee created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<HttpResponse> deleteCompany(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("Employee deleted", employeeService.deleteEmployee(id)))
                        .message("Employee deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
    @GetMapping(path = "/employee/image/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getEmployeeImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Pictures/DesktopBackgrounds/" + fileName));
    }

    @PostMapping(path = "/employee/image/update")
    public ResponseEntity<HttpResponse> updateEmployeeImage(@RequestParam(name = "email") String email,
                                      @RequestParam(name = "imageUrl") MultipartFile imageUrl) throws IOException {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("employee", employeeService.updateImage(email, imageUrl)))
                        .message("Employee updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}

