package uz.muso.hrmanagement.pyload;

import lombok.Data;
import uz.muso.hrmanagement.entitiy.User;

import java.util.UUID;

@Data
public class CompanyDto {
    private String name;
    private UUID directorId;
}
