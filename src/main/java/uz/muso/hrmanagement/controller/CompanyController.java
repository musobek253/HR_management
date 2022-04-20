package uz.muso.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.muso.hrmanagement.pyload.ApiResponse;
import uz.muso.hrmanagement.pyload.CompanyDto;
import uz.muso.hrmanagement.service.CompanyService;

@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
