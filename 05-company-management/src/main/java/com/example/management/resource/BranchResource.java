package com.example.management.resource;

import com.example.management.entity.Branch;
import com.example.management.response.HttpResponse;
import com.example.management.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class BranchResource {

    private BranchService branchService;
    @Autowired
    public BranchResource(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/branch")
    public ResponseEntity<HttpResponse> getAll(){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("branches", branchService.getAll()))
                        .message("Branch retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/branch/{id}")
    public ResponseEntity<HttpResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("branch", branchService.getBranchById(id)))
                        .message("Branch retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/branch/name/{name}")
    public ResponseEntity<HttpResponse> getByName(@PathVariable String name){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("branch", branchService.getBranchByName(name)))
                        .message("Branch retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/branch")
    public ResponseEntity<HttpResponse> saveBranch(@RequestBody Branch branch){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("branch", branchService.createBranch(branch)))
                        .message("Branch created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @DeleteMapping("/branch/{id}")
    public ResponseEntity<HttpResponse> deleteBranch(@PathVariable Long id){
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now())
                        .data(of("Branch deleted", branchService.deleteBranch(id)))
                        .message("deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
