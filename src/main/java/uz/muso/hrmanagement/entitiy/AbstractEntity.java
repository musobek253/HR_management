package uz.muso.hrmanagement.entitiy;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;


    @CreationTimestamp
    @Column(updatable = false)// yanatilgan vaqtni o'zgartirishga ruxsat bermaydi
    private Timestamp createdAt; //qachon qo'shilsa o'sh vaqtni oladi


    @UpdateTimestamp
    private Timestamp updatedAt;//qachon o'zgartirilsa o'sh vaqtni oladi

    @CreatedBy
    @Column(updatable = false) //kim creat qilgani o'zgartirib bo'lmaydi?
    private UUID createdBy;


    @LastModifiedBy
    private UUID updatedBy; //kim tomondan oxirgi o'zgartirish kiritildi

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbstractEntity that = (AbstractEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
