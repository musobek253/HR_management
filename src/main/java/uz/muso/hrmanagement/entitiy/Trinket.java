package uz.muso.hrmanagement.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.muso.hrmanagement.entitiy.Company;
import uz.muso.hrmanagement.entitiy.User;
import uz.muso.hrmanagement.entitiy.baseEntity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trinket extends AbstractEntity {
    @ManyToOne
    private Company company;

    @OneToOne
    private User owner;//Trinket egasi

    private String number = UUID.randomUUID().toString();//Trinket raqami

    private boolean enabled = true;
}
